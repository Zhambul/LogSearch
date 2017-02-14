package ru.zhambul.logsearch.saver;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import ru.zhambul.logsearch.service.ResourceReader;
import ru.zhambul.logsearch.type.LogEntry;
import ru.zhambul.logsearch.type.SearchResult;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Objects;
import java.util.Random;

/**
 * Created by zhambyl on 02/02/2017.
 */
@Stateless
public class SearchResultSaverImpl implements SearchResultSaver {

    private String savePath;
    private String xslPath;

    private final static XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
    private final static FopFactory fopFactory = FopFactory.newInstance(new File("").toURI());

    @EJB
    private ResourceReader resourceReader;

    public SearchResultSaverImpl() {
    }

    /*
    * Constructor fot tests
    * */
    public SearchResultSaverImpl(ResourceReader resourceReader) {
        this.resourceReader = Objects.requireNonNull(resourceReader);
    }

    @PostConstruct
    public void init() {
        String savePath = resourceReader.read("savePath");
        String xslPath = resourceReader.read("xslPath");

        requireDirExist(savePath);
        requireDirExist(xslPath);

        this.savePath = savePath;
        this.xslPath = xslPath;
    }

    @Override
    public String save(SearchResult searchResult, FileType fileType) {
        Objects.requireNonNull(searchResult);
        Objects.requireNonNull(fileType);

        String fileName = generateFileName(fileType);
        save(searchResult, fileType, fileName);
        return fileName;
    }

    @Override
    public File getByName(String name) {
        Objects.requireNonNull(name);
        return new File(savePath, name);
    }

    private void requireDirExist(String path) {
        requireDirExist(new File(path));
    }

    private void requireDirExist(File file) {
        if (!file.exists() || !file.isDirectory()) {
            throw new IllegalArgumentException("directory " + file.getPath() + " is not found");
        }
    }

    private String generateFileName(FileType fileType) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString() + "." + fileType.extension();
    }

    private void save(SearchResult searchResult, FileType fileType, String fileName) {
        try {
            File resultFile = new File(savePath, fileName);
            switch (fileType) {
                case XML:
                    saveAsXML(searchResult, resultFile);
                    break;
                case LOG:
                    saveAsLog(searchResult, resultFile);
                    break;
                default:
                    File xslFile = new File(xslPath, fileType.xslFileName());
                    if (!xslFile.exists()) {
                        throw new IllegalStateException("cannot find xsl file for " + fileType);
                    }

                    File xmlFile = File.createTempFile("xml_temp", null);
                    saveAsXML(searchResult, xmlFile);

                    switch (fileType) {
                        case HTML:
                        case DOC:
                            saveWithXSLT(resultFile, xmlFile, xslFile);
                            break;
                        case PDF:
                        case RTF:
                            saveWithFop(resultFile, xmlFile, xslFile, fileType.mime());
                            break;
                        default:
                            throw new IllegalArgumentException("unknown file type " + fileType);
                    }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void saveWithXSLT(File resultFile, File tempXmlFile, File xslFile)
            throws TransformerException {

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(xslFile));
        Source in = new StreamSource(tempXmlFile);
        Result out = new StreamResult(resultFile);
        transformer.transform(in, out);
    }

    private void saveWithFop(File resultFile, File tempXmlFile, File xslFile, String mime)
            throws IOException, FOPException, TransformerException {

        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(resultFile))) {
            Fop fop = fopFactory.newFop(mime, foUserAgent, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslFile));
            Source src = new StreamSource(tempXmlFile);
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        }
    }

    private void saveAsLog(SearchResult searchResult, File resultFile) throws IOException {
        try (Writer writer = new BufferedWriter(new FileWriter(resultFile))) {
            for (LogEntry logEntry : searchResult.getLogs()) {
                writer.write(logEntry.getPayload() + "####\n");
            }
        }
    }

    private void saveAsXML(SearchResult searchResult, File resultFile)
            throws XMLStreamException, IOException {

        XMLStreamWriter writer = xmlOutputFactory
                .createXMLStreamWriter(
                        new FileOutputStream(resultFile)
                );
        try {
            writer.writeStartDocument();
            writer.writeStartElement("Logs");

            for (LogEntry log : searchResult.getLogs()) {
                writer.writeStartElement("Log");
                writer.writeAttribute("Payload", log.getPayload());
                writer.writeAttribute("Date", log.getDate().toString());
                writer.writeEndElement();
            }

            writer.writeEndElement();
        } finally {
            writer.close();
        }
    }
}