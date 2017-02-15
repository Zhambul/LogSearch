package ru.zhambul.logsearch.type;

import org.apache.fop.apps.MimeConstants;

/**
 * Created by zhambyl on 02/02/2017.
 */
public enum FileTypeEnum {
    LOG, XML, HTML, PDF, RTF, DOC;

    public String extension() {
        return name().toLowerCase();
    }

    public String xslFileName() {
        switch (this) {
            case RTF:
            case PDF:
                return "fop.xsl";
            default:
                return extension() + ".xsl";
        }
    }

    public String mime() {
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
