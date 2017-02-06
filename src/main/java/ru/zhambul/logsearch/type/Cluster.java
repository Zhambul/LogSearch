package ru.zhambul.logsearch.type;

import ru.zhambul.logsearch.type.service.SearchResult;
import ru.zhambul.logsearch.type.service.SearchParams;

import java.util.List;

/**
 * Created by zhambyl on 19/01/2017.
 */
public class Cluster implements Searchable {

    private final List<Server> servers;

    public Cluster(List<Server> servers) {
        this.servers = servers;
    }

    @Override
    public SearchResult search(SearchParams searchParams) {
        return searchAll(servers, searchParams);
    }
}
