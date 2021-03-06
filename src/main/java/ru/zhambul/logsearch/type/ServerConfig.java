package ru.zhambul.logsearch.type;

/**
 * Created by zhambyl on 20/01/2017.
 */
public class ServerConfig {

    private final String name;
    private final String clusterName;

    public ServerConfig(String name, String clusterName) {
        this.name = name;
        this.clusterName = "".equals(clusterName) ? null : clusterName;
    }

    public String getName() {
        return name;
    }

    public String getClusterName() {
        return clusterName;
    }
}
