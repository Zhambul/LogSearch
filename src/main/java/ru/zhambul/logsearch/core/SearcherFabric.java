package ru.zhambul.logsearch.core;

import ru.zhambul.logsearch.type.DomainConfig;
import ru.zhambul.logsearch.type.SearchTargetTypeEnum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by zhambyl on 07/02/2017.
 */
public class SearcherFabric {

    private final DomainConfig domainConfig = DomainConfig.getInstance();
    private final ResourceReader resourceReader = new ResourceReader();

    public Searcher create(SearchTargetTypeEnum type, String name) {
        switch (type) {
            case DOMAIN:
                return createDomainSearcher();
            case CLUSTER:
                return createClusterSearcher(name);
            case SERVER:
                return createServerSearcher(name);
            default:
                throw new IllegalArgumentException("unknown type " + type);
        }
    }

    private Searcher createDomainSearcher() {
        List<String> logFiles = domainConfig.getServers()
                .stream()
                .map(serverConfig -> serverLogFiles(serverConfig.getName()))
                .flatMap(List::stream)
                .collect(toList());

        return new Searcher(logFiles);
    }

    private Searcher createClusterSearcher(String clusterName) {
        List<String> logFiles = domainConfig.getServers()
                .stream()
                .filter(each -> clusterName.equals(each.getClusterName()))
                .map(serverConfig -> serverLogFiles(serverConfig.getName()))
                .flatMap(List::stream)
                .collect(toList());

        return new Searcher(logFiles);
    }

    private Searcher createServerSearcher(String serverName) {
        return new Searcher(serverLogFiles(serverName));
    }

    private List<String> serverLogFiles(String serverName) {
        Path path = resourceReader.serverLogsPath(serverName);
        try {
            return Files
                    .list(path)
                    .map(each -> each.getFileName().toString())
                    .filter(each -> each.contains(".log"))
                    .filter(each -> !each.contains("access.log"))
                    .map(each -> path.resolve(each).toString())
                    .collect(toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
