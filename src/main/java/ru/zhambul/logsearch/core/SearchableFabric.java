package ru.zhambul.logsearch.core;

import ru.zhambul.logsearch.converter.InStrToLogEntriesConverter;
import ru.zhambul.logsearch.type.DomainConfig;
import ru.zhambul.logsearch.type.SearchTargetTypeEnum;
import ru.zhambul.logsearch.type.ServerConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhambyl on 07/02/2017.
 */
public class SearchableFabric {

    private final DomainConfig domainConfig = DomainConfig.parse();
    private final InStrToLogEntriesConverter logEntryConverter = new InStrToLogEntriesConverter();
    private final ResourceReader resourceReader = new ResourceReader();

    public Searchable create(SearchTargetTypeEnum type, String name) {
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
                .filter(each -> name.equals(each.getClusterName()))
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
        List<Searchable> logFiles = listLogFiles(resourceReader.serverLogsPath(name));
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
        return new SearchFile(path, logEntryConverter);
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
}
