package ru.zhambul.logsearch.type;

import ru.zhambul.logsearch.type.service.SearchParams;
import ru.zhambul.logsearch.type.service.SearchResult;

import java.util.List;

/**
 * Created by zhambyl on 19/01/2017.
 */
public class Server implements Searchable {

    private final List<? extends SearchFile> logFiles;

    public Server(List<? extends SearchFile> logFiles) {
        this.logFiles = logFiles;
    }

    @Override
    public SearchResult search(SearchParams params) {
        return searchAll(logFiles, params);
    }
}
