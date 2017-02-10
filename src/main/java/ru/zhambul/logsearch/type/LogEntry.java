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

    @Override
    public String toString() {
        return "LogEntry{" +
                "payload='" + payload + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogEntry logEntry = (LogEntry) o;

        if (payload != null ? !payload.equals(logEntry.payload) : logEntry.payload != null) return false;
        return date != null ? date.equals(logEntry.date) : logEntry.date == null;
    }

    @Override
    public int hashCode() {
        int result = payload != null ? payload.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
