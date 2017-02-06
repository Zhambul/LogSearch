package ru.zhambul.logsearch.type;

import ru.zhambul.logsearch.type.service.SearchResult;
import ru.zhambul.logsearch.type.service.SearchParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhambyl on 19/01/2017.
 */
public class Domain implements Searchable {

    private final List<Server> servers;
    private final List<? extends Searchable> domainLogs;

    public Domain(List<Server> standaloneServers, List<? extends SearchFile> domainLogs) {
        this.servers = standaloneServers;
        this.domainLogs = domainLogs;
    }

    @Override
    public SearchResult search(SearchParams params) {
        List<Searchable> allSearchable = new ArrayList<>(servers);
        allSearchable.addAll(domainLogs);

        return searchAll(allSearchable, params);
    }
}
