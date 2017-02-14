package ru.zhambul.logsearch.service;

import javax.ejb.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zhambyl on 13/02/2017.
 */
@Singleton
public class ResourceReaderImpl implements ResourceReader {

    private final Properties properties;

    public ResourceReaderImpl() {
        try (InputStream inputStream =
                     PathResolverImpl.class.getClassLoader().getResourceAsStream("default.properties")) {
            this.properties = new Properties();
            this.properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String read(String name) {
        return properties.getProperty(name);
    }
}
