package ru.zhambul.logsearch.core;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by zhambyl on 13/02/2017.
 */
public class ResourceReader {

    private final Properties properties;
    private final String domainPath;

    public ResourceReader() {
        try (InputStream inputStream =
                     ResourceReader.class.getClassLoader().getResourceAsStream("default.properties")) {
            this.properties = new Properties();
            this.properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        domainPath = read("domainPath");
    }

    private String read(String name) {
        return properties.getProperty(name);
    }

    public Path domainPath() {
        return Paths.get(domainPath);
    }

    public Path serverLogsPath(String serverName) {
        return Paths.get(domainPath + "servers/" + serverName + "/logs");
    }

    public Path domainConfigPath() {
        return Paths.get(domainPath + "config/config.xml");
    }

    public Path savePath() {
        return Paths.get(read("savePath"));
    }

    public Path xslPath() {
        return Paths.get(read("xslPath"));
    }
}
