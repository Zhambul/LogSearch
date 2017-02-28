package ru.zhambul.logsearch.type;

import ru.zhambul.logsearch.core.ResourceReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhambyl on 20/01/2017.
 */
public class DomainConfig {

    private final String name;
    private final List<ServerConfig> servers;

    private final static XMLInputFactory inputFactory = XMLInputFactory.newInstance();
    private final static ResourceReader resourceReader = new ResourceReader();

    private static DomainConfig instance;

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

    public static DomainConfig getInstance() {
        if (instance == null) {
            instance = parse(resourceReader.domainConfigPath());
        }
        return instance;
    }

    public static DomainConfig parse(Path filePath) {
        try (InputStream in = new FileInputStream(filePath.toString())) {
            XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);
            return parseDomainConfig(streamReader);
        } catch (IOException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    private static DomainConfig parseDomainConfig(XMLStreamReader streamReader) throws XMLStreamException {
        String domainName = null;
        List<ServerConfig> servers = new ArrayList<>();
        while (streamReader.hasNext()) {
            if (streamReader.isStartElement()) {
                switch (streamReader.getLocalName()) {
                    case "name":
                        if (domainName == null) {
                            domainName = streamReader.getElementText();
                        }
                        break;

                    case "server":
                        servers.add(parseServerConfig(streamReader));
                        break;
                }
            }
            streamReader.next();
        }
        return new DomainConfig(domainName, servers);
    }

    private static ServerConfig parseServerConfig(XMLStreamReader streamReader) throws XMLStreamException {
        String serverName = null;
        String clusterName = null;
        while (streamReader.hasNext()) {
            if (streamReader.isStartElement()) {
                switch (streamReader.getLocalName()) {
                    case "name":
                        serverName = streamReader.getElementText();
                        break;
                    case "cluster":
                        clusterName = streamReader.getElementText();
                        break;
                }
            }
            if (streamReader.isEndElement() && streamReader.getLocalName().equals("server")) {
                break;
            }
            streamReader.next();
        }
        return new ServerConfig(serverName, clusterName);
    }
}
