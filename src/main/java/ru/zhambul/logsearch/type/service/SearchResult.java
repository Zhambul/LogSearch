package ru.zhambul.logsearch.type.service;

import ru.zhambul.logsearch.type.LogEntry;

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
        //todo unique, sorted by date
        newLogs.addAll(toAppend.getLogs());
        return new SearchResult(newLogs);
    }

    public List<LogEntry> getLogs() {
        return logs;
    }

    public static SearchResult empty() {
        return new SearchResult(new ArrayList<>());
    }
}
