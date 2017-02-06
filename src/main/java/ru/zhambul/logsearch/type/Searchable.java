package ru.zhambul.logsearch.type;

import ru.zhambul.logsearch.type.service.SearchParams;
import ru.zhambul.logsearch.type.service.SearchResult;

import java.util.Collection;

/**
 * Created by zhambyl on 19/01/2017.
 */
public interface Searchable {

    SearchResult search(SearchParams searchParams);

    default SearchResult searchAll(Collection<? extends Searchable> all, SearchParams params) {
        return all
                .parallelStream()
                .map(searchable -> searchable.search(params))
                .reduce(SearchResult::merge)
                .orElse(null);
    }
}
