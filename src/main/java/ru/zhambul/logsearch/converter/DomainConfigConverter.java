package ru.zhambul.logsearch.converter;

import ru.zhambul.logsearch.xml.DomainConfig;
import ru.zhambul.logsearch.xml.ServerConfig;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhambyl on 26/01/2017.
 */
@Stateless
@LocalBean
public class DomainConfigConverter implements Converter<String, DomainConfig> {

    private final static XMLInputFactory inputFactory = XMLInputFactory.newInstance();

    @Override
    public DomainConfig convert(String filePath) {
        try (InputStream in = new FileInputStream(filePath)) {
            XMLStreamReader streamReader = inputFactory.createXMLStreamReader(in);

            return parseDomainConfig(streamReader);

        } catch (IOException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    private DomainConfig parseDomainConfig(XMLStreamReader streamReader) throws XMLStreamException {
        DomainConfig config = new DomainConfig();
        while (streamReader.hasNext()) {
            if (streamReader.isStartElement()) {
                switch (streamReader.getLocalName()) {
                    case "name":
                        if (config.getName() == null) {
                            config.setName(streamReader.getElementText());
                        }
                        break;

                    case "server":
                        config.addServer(parseServerConfig(streamReader));
                        break;
                }
            }
            streamReader.next();
        }
        return config;
    }

    private ServerConfig parseServerConfig(XMLStreamReader streamReader) throws XMLStreamException {
        ServerConfig serverConfig = new ServerConfig();
        while (streamReader.hasNext()) {
            if (streamReader.isStartElement()) {
                switch (streamReader.getLocalName()) {
                    case "name":
                        serverConfig.setName(streamReader.getElementText());
                        break;
                    case "cluster":
                        serverConfig.setCluster(streamReader.getElementText());
                        break;
                }
            }
            if (streamReader.isEndElement() && streamReader.getLocalName().equals("server")) {
                break;
            }
            streamReader.next();
        }
        return serverConfig;
    }
}
