package ru.zhambul.logsearch.core;

import ru.zhambul.logsearch.type.SearchQuery;
import ru.zhambul.logsearch.type.SearchResult;

import java.util.Objects;

/**
 * Created by zhambyl on 19/01/2017.
 */
public class SearchService {

    private SearchableFabric fabric = new SearchableFabric();

    public SearchResult search(SearchQuery query) {
        Objects.requireNonNull(query);
        Searchable searchable = fabric.create(query.getTargetType(), query.getTargetName());
        return searchable.search(query.getParams());
    }
}

