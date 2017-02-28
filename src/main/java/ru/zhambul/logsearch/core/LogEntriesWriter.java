package ru.zhambul.logsearch.core;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import ru.zhambul.logsearch.type.FileTypeEnum;
import ru.zhambul.logsearch.type.LogEntry;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

/**
 * Created by zhambyl on 02/02/2017.
 */
public class LogEntriesWriter {

    private final Path savePath;
    private final Path xslPath;

    private final static XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
    private final static FopFactory fopFactory = FopFactory.newInstance(new File("").toURI());

    public LogEntriesWriter() {
        ResourceReader resourceReader = new ResourceReader();
        savePath = resourceReader.savePath();
        xslPath = resourceReader.xslPath();
    }

    //todo refactor
    public String write(List<LogEntry> logEntries, FileTypeEnum fileType) {
        String fileName = generateFileName(fileType);

        try {
            File resultFile = savePath.resolve(fileName).toFile();
            switch (fileType) {
                case XML:
                    writeAsXML(logEntries, resultFile);
                    break;
                case LOG:
                    writeAsLog(logEntries, resultFile);
                    break;
                default:
                    File xslFile = xslPath.resolve(fileType.xslFileName()).toFile();
                    if (!xslFile.exists()) {
                        throw new IllegalStateException("cannot find xsl file for " + fileType);
                    }

                    File xmlFile = File.createTempFile("xml_temp", null);
                    writeAsXML(logEntries, xmlFile);

                    switch (fileType) {
                        case HTML:
                        case DOC:
                            writeWithXSLT(resultFile, xmlFile, xslFile);
                            break;
                        case PDF:
                        case RTF:
                            writeWithFop(resultFile, xmlFile, xslFile, fileType.mime());
                            break;
                        default:
                            throw new IllegalArgumentException("unknown file type " + fileType);
                    }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return fileName;
    }

    private void writeAsLog(List<LogEntry> logEntries, File resultFile) throws IOException {
        try (Writer writer = new BufferedWriter(new java.io.FileWriter(resultFile))) {
            for (LogEntry logEntry : logEntries) {
                writer.write(logEntry.getPayload() + Searcher.LOG_DELIMITER + "\n");
            }
        }
    }

    private void writeAsXML(List<LogEntry> logEntries, File resultFile)
            throws XMLStreamException, IOException {

        XMLStreamWriter writer = xmlOutputFactory
                .createXMLStreamWriter(
                        new FileOutputStream(resultFile)
                );
        try {
            writer.writeStartDocument();
            writer.writeStartElement("Logs");

            for (LogEntry log : logEntries) {
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

    private void writeWithXSLT(File resultFile, File tempXmlFile, File xslFile)
            throws TransformerException {

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(xslFile));
        Source in = new StreamSource(tempXmlFile);
        Result out = new StreamResult(resultFile);
        transformer.transform(in, out);
    }

    private void writeWithFop(File resultFile, File tempXmlFile, File xslFile, String mime)
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

    private String generateFileName(FileTypeEnum fileType) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            builder.append(random.nextInt(10));
        }
        return builder.toString() + "." + fileType.extension();
    }
}