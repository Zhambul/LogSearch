package ru.zhambul.logsearch.api.search;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by zhambyl on 10/02/2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RESTFile {

    private String name;

    public String getName() {
        return name;
    }

    public RESTFile setName(String name) {
        this.name = name;
        return this;
    }
}
