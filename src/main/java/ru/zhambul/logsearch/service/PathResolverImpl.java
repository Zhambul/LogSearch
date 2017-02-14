package ru.zhambul.logsearch.service;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Created by zhambyl on 10/02/2017.
 */
@Stateless
public class PathResolverImpl implements PathResolver {

    private String domainPath;

    @EJB
    private ResourceReader resourceReader;

    public PathResolverImpl() {
    }

    /*
    * Constructor fot tests
    * */
    public PathResolverImpl(ResourceReader resourceReader) {
        this.resourceReader = Objects.requireNonNull(resourceReader);
    }

    @PostConstruct
    public void init() {
        domainPath = resourceReader.read("domainPath");
    }

    @Override
    public Path domainPath() {
        return Paths.get(domainPath);
    }

    @Override
    public Path serverLogsPath(String serverName) {
        return Paths.get(domainPath + "servers/" + serverName + "/logs");
    }

    @Override
    public Path domainConfigPath() {
        return Paths.get(domainPath + "config/config.xml");
    }

}
