package ru.zhambul.logsearch.xml;

/**
 * Created by zhambyl on 20/01/2017.
 */
public class ServerConfig {

    private String name;
    private String cluster;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        if (cluster.equals("")) {
            return;
        }
        this.cluster = cluster;
    }
}
