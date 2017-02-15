package ru.zhambul.logsearch.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by zhambyl on 10/02/2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DownloadRESTResponse {

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public DownloadRESTResponse setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }
}
