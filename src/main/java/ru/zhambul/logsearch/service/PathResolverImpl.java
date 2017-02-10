package ru.zhambul.logsearch.service;

import javax.ejb.Stateless;

/**
 * Created by zhambyl on 10/02/2017.
 */
@Stateless
public class PathResolverImpl implements PathResolver {

    private final static String DEFAULT_PATH = "/Users/zhambyl/app_server/fmw_12/wls12212/" +
            "user_projects/domains/base_domain/";

    private final String path;

    public PathResolverImpl() {
        //todo read from properties
        path = DEFAULT_PATH;
    }

    @Override
    public String domainPath() {
        return path;
    }

    @Override
    public String serverLogsPath(String serverName) {
        return path + "servers/" + serverName + "/logs";
    }

    @Override
    public String configPath() {
        return path + "config/config.xml";
    }
}
