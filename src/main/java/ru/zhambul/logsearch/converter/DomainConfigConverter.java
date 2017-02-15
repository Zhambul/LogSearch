package ru.zhambul.logsearch.converter;

import ru.zhambul.logsearch.type.DomainConfig;
import ru.zhambul.logsearch.type.ServerConfig;

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
 * Created by zhambyl on 26/01/2017.
 */
public class DomainConfigConverter implements Converter<Path, DomainConfig> {

    private final static XMLInputFactory inputFactory = XMLInputFactory.newInstance();

    @Override
    public DomainConfig convert(Path filePath) {
        try (InputStream in = new FileInputStream(filePath.toString())) {
            XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);

            return parseDomainConfig(streamReader);

        } catch (IOException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    private DomainConfig parseDomainConfig(XMLStreamReader streamReader) throws XMLStreamException {
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

    private ServerConfig parseServerConfig(XMLStreamReader streamReader) throws XMLStreamException {
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