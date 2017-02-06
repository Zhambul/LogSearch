package ru.zhambul.logsearch.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhambyl on 20/01/2017.
 */
public class DomainConfig {

    private String name;
    private List<ServerConfig> servers = new ArrayList<>();

    public String getName() {
        return name;
    }

    public List<ServerConfig> getServers() {
        return servers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addServer(ServerConfig server) {
        servers.add(server);
    }

}
