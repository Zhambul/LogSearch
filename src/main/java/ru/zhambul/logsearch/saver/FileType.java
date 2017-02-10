package ru.zhambul.logsearch.saver;

import org.apache.fop.apps.MimeConstants;

/**
 * Created by zhambyl on 02/02/2017.
 */
public enum FileType {
    LOG, XML, HTML, PDF, RTF, DOC;

    String extension() {
        return name().toLowerCase();
    }

    String xslFileName() {
        switch (this) {
            case RTF:
            case PDF:
                return "fop.xsl";
            default:
                return extension() + ".xsl";
        }
    }

    String mime() {
        switch (this) {
            case RTF:
                return MimeConstants.MIME_RTF;
            case PDF:
                return MimeConstants.MIME_PDF;
            default:
                throw new IllegalArgumentException("no mime type for " + this);
        }
    }
}
