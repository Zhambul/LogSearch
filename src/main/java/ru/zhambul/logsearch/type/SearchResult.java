package ru.zhambul.logsearch.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhambyl on 19/01/2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResult {

    private List<LogEntry> logs;

    public SearchResult() {
    }

    public SearchResult(List<LogEntry> logs) {
        this.logs = logs;
    }

    public SearchResult merge(SearchResult toAppend) {
        List<LogEntry> newLogs = new ArrayList<>(logs);
        //todo sorted by date
        newLogs.addAll(toAppend.getLogs());
        return new SearchResult(newLogs);
    }

    public List<LogEntry> getLogs() {
        return logs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchResult that = (SearchResult) o;

        return logs != null ? logs.equals(that.logs) : that.logs == null;
    }

    @Override
    public int hashCode() {
        return logs != null ? logs.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "logs=" + logs +
                '}';
    }
}
