package ru.zhambul.logsearch.type;

import ru.zhambul.logsearch.converter.DomainConfigConverter;
import ru.zhambul.logsearch.core.ResourceReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * Created by zhambyl on 20/01/2017.
 */
public class DomainConfig {

    private final String name;
    private final List<ServerConfig> servers;

    private static DomainConfigConverter converter = new DomainConfigConverter();
    private static ResourceReader resourceReader = new ResourceReader();
    private static DomainConfig instance;

    public DomainConfig(String name, List<ServerConfig> servers) {
        this.name = Objects.requireNonNull(name);
        this.servers = Objects.requireNonNull(servers);
    }

    public String getName() {
        return name;
    }

    public List<ServerConfig> getServers() {
        return new ArrayList<>(servers);
    }

    /**
     * Group servers by cluster
     */
    public List<ClusterConfig> getClusters() {
        return servers.stream()
                .filter(it -> it.getClusterName() != null)
                .collect(
                        groupingBy(ServerConfig::getClusterName,
                                Collectors.mapping(Function.identity(), toList())
                        )
                )
                .entrySet()
                .stream()
                .map(it -> new ClusterConfig(it.getKey(), it.getValue()))
                .collect(toList());
    }

    public static DomainConfig parse() {
        if (instance == null) {
            synchronized (DomainConfig.class) {
                if (instance == null) {
                    instance = converter.convert(resourceReader.domainConfigPath());
                }
            }
        }
        return instance;
    }
}
