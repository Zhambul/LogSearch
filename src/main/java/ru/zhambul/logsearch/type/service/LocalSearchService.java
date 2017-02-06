package ru.zhambul.logsearch.type.service;

import ru.zhambul.logsearch.type.*;
import ru.zhambul.logsearch.type.converter.DomainConfigConverter;
import ru.zhambul.logsearch.type.converter.InStrToLogEntriesConverter;
import ru.zhambul.logsearch.xml.DomainConfig;
import ru.zhambul.logsearch.xml.ServerConfig;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhambyl on 19/01/2017.
 */
@Stateless
public class LocalSearchService implements SearchService {

    private DomainConfig domainConfig;

    @EJB
    private DomainConfigConverter domainConfigConverter;

    @EJB
    private InStrToLogEntriesConverter logEntryConverter;

    //todo get user.dir
    private final static String PATH = "/Users/zhambyl/app_server/fmw_12/wls12212/" +
            "user_projects/domains/base_domain/";
    private final static String SERVERS_PATH = PATH + "servers/";
    private final static String CONFIG_PATH = PATH + "config/config.xml";

    @PostConstruct
    public void init() {
        System.out.println("post init");
        domainConfig = domainConfigConverter.convert(CONFIG_PATH);
    }

    @Override
    public SearchResult search(SearchQuery query) {
        Searchable searchable = createSearchable(query.getTargetType(), query.getTargetName());
        return searchable.search(query.getParams());
    }

    public void setDomainConfigConverter(DomainConfigConverter domainConfigConverter) {
        this.domainConfigConverter = domainConfigConverter;
    }

    public void setLogEntryConverter(InStrToLogEntriesConverter logEntryConverter) {
        this.logEntryConverter = logEntryConverter;
    }


    private Searchable createSearchable(SearchTargetType type, String name) {
        switch (type) {
            case DOMAIN:
                return createDomain(name);
            case CLUSTER:
                return createCluster(name);
            case SERVER:
                validateServer(name);
                return createServer(name);
            default:
                throw new IllegalStateException("unknown type");
        }
    }

    private Domain createDomain(String name) {
        if (!domainConfig.getName().equals(name)) {
            throw new IllegalArgumentException("no such domain (" + name + ")");
        }

        List<Server> servers = domainConfig.getServers()
                .stream()
                .map(this::createServer)
                .collect(toList());

        return new Domain(servers, new ArrayList<>());
    }

    private Cluster createCluster(String name) {
        List<Server> servers = domainConfig.getServers()
                .stream()
                .filter(each -> name.equals(each.getCluster()))
                .map(this::createServer)
                .collect(toList());

        if (servers.size() == 0) {
            throw new IllegalArgumentException("no such cluster (" + name + ")");
        }

        return new Cluster(servers);
    }

    private Server createServer(ServerConfig serverConfig) {
        return createServer(serverConfig.getName());
    }

    private Server createServer(String name) {
        Path serverPath = Paths.get(serverLogsPath(name));
        try {
            List<SearchFile> logFiles = Files
                    .list(serverPath)
                    .map(each -> each.getFileName().toString())
                    .filter(each -> each.contains(".log"))
                    .filter(each -> !each.equals("access.log"))
                    .map(each -> new AWKSearchFile(serverPath.resolve(each).toString(), logEntryConverter))
                    .collect(toList());

            return new Server(logFiles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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


    private String serverLogsPath(String serverName) {
        return SERVERS_PATH + serverName + "/logs";
    }
}

