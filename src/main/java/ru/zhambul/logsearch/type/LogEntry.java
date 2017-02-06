package ru.zhambul.logsearch.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Date;

/**
 * Created by zhambyl on 19/01/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LogEntry {

    private String payload;
    private Date date;

    public LogEntry() {
    }

    public LogEntry(String payload, Date date) {
        this.payload = payload;
        this.date = date;
    }

    public String getPayload() {
        return payload;
    }

    public Date getDate() {
        return date;
    }
}
