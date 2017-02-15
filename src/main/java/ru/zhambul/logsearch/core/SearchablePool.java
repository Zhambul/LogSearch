package ru.zhambul.logsearch.core;

import ru.zhambul.logsearch.type.SearchParams;
import ru.zhambul.logsearch.type.SearchResult;

import java.util.Collection;
import java.util.List;

/**
 * Created by zhambyl on 10/02/2017.
 */
public class SearchablePool implements Searchable {

    private final List<? extends Searchable> searchables;

    public SearchablePool(List<? extends Searchable> searchables) {
        this.searchables = searchables;
    }

    @Override
    public SearchResult search(SearchParams params) {
        return searchAll(searchables, params);
    }

    protected SearchResult searchAll(Collection<? extends Searchable> all, SearchParams params) {
        return all
                .parallelStream()
                .map(searchable -> searchable.search(params))
                .reduce(SearchResult::merge)
                .orElse(null);
    }
}
