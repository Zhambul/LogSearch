package ru.zhambul.logsearch.search;

import ru.zhambul.logsearch.converter.DomainConfigConverter;
import ru.zhambul.logsearch.converter.InStrToLogEntriesConverter;
import ru.zhambul.logsearch.service.PathResolver;
import ru.zhambul.logsearch.service.SearchTargetType;
import ru.zhambul.logsearch.xml.DomainConfig;
import ru.zhambul.logsearch.xml.ServerConfig;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhambyl on 07/02/2017.
 */
@Stateless
public class LocalSearchableFabric implements SearchableFabric {

    private DomainConfig domainConfig;

    @EJB
    private DomainConfigConverter domainConfigConverter;

    @EJB
    private InStrToLogEntriesConverter logEntryConverter;

    @EJB
    private PathResolver pathResolver;

    @PostConstruct
    public void init() {
        domainConfig = domainConfigConverter.convert(pathResolver.configPath());
    }

    @Override
    public Searchable create(SearchTargetType type, String name) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(name);

        switch (type) {
            case DOMAIN:
                return createDomain(name);
            case CLUSTER:
                return createCluster(name);
            case SERVER:
                validateServer(name);
                return createServer(name);
            default:
                throw new IllegalArgumentException("unknown type " + type);
        }
    }

    private Searchable createDomain(String name) {
        if (!domainConfig.getName().equals(name)) {
            throw new IllegalArgumentException("no such domain (" + name + ")");
        }

        List<Searchable> servers = domainConfig.getServers()
                .stream()
                .map(this::createServer)
                .collect(toList());

        return new SearchablePool(servers);
    }

    private Searchable createCluster(String name) {
        List<Searchable> servers = domainConfig.getServers()
                .stream()
                .filter(each -> name.equals(each.getCluster()))
                .map(this::createServer)
                .collect(toList());

        if (servers.size() == 0) {
            throw new IllegalArgumentException("no such cluster (" + name + ")");
        }

        return new SearchablePool(servers);
    }

    private Searchable createServer(ServerConfig serverConfig) {
        return createServer(serverConfig.getName());
    }

    private Searchable createServer(String name) {
        Path serverPath = Paths.get(pathResolver.serverLogsPath(name));
        List<Searchable> logFiles = listLogFiles(serverPath);
        return new SearchablePool(logFiles);
    }

    private List<Searchable> listLogFiles(Path path) {
        try {
            return Files
                    .list(path)
                    .map(each -> each.getFileName().toString())
                    .filter(each -> each.contains(".log"))
                    .filter(each -> !each.contains("access.log"))
                    .map(each -> createSearchFile(path.resolve(each).toString()))
                    .collect(toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Searchable createSearchFile(String path) {
        return new AWKSearchFile(path, logEntryConverter);
    }

    private void validateServer(String name) {
        ServerConfig serverConf = domainConfig.getServers()
                .stream()
                .filter(each -> each.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (serverConf == null) {
            throw new IllegalArgumentException("no such server (" + name + ")");
        }
    }


    /*
        Setters for tests
    */
    public void setDomainConfigConverter(DomainConfigConverter domainConfigConverter) {
        this.domainConfigConverter = domainConfigConverter;
    }

    public void setLogEntryConverter(InStrToLogEntriesConverter logEntryConverter) {
        this.logEntryConverter = logEntryConverter;
    }

    public void setPathResolver(PathResolver pathResolver) {
        this.pathResolver = pathResolver;
    }
}
