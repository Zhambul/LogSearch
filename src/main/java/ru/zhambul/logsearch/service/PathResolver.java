package ru.zhambul.logsearch.service;

import javax.ejb.Local;

/**
 * Created by zhambyl on 10/02/2017.
 */
@Local
public interface PathResolver {

    String domainPath();

    String serverLogsPath(String serverName);

    String configPath();
}
