package ru.zhambul.logsearch.type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhambyl on 20/01/2017.
 */
public class DomainConfig {

    private final String name;
    private final List<ServerConfig> servers;

    public DomainConfig(String name, List<ServerConfig> servers) {
        this.name = name;
        this.servers = servers;
    }

    public String getName() {
        return name;
    }

    public List<ServerConfig> getServers() {
        return new ArrayList<>(servers);
    }
}
