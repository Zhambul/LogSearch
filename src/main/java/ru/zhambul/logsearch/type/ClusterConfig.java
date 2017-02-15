package ru.zhambul.logsearch.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by zhambyl on 15/02/2017.
 */
public class ClusterConfig {

    private final String name;
    private final List<ServerConfig> servers;

    public ClusterConfig(String name, List<ServerConfig> servers) {
        this.name = Objects.requireNonNull(name);
        this.servers = Objects.requireNonNull(servers);
    }

    public String getName() {
        return name;
    }

    public List<ServerConfig> getServers() {
        return new ArrayList<>(servers);
    }
}
