package ru.zhambul.logsearch.service;

import javax.ejb.Local;
import java.nio.file.Path;

/**
 * Created by zhambyl on 10/02/2017.
 */
@Local
public interface PathResolver {

    Path domainPath();

    Path serverLogsPath(String serverName);

    Path domainConfigPath();
}
