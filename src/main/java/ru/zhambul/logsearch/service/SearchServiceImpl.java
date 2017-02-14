package ru.zhambul.logsearch.service;

import ru.zhambul.logsearch.search.SearchableFabric;
import ru.zhambul.logsearch.search.Searchable;
import ru.zhambul.logsearch.type.SearchResult;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Objects;

/**
 * Created by zhambyl on 19/01/2017.
 */
@Stateless
public class SearchServiceImpl implements SearchService {

    @EJB
    private SearchableFabric fabric;

    public SearchServiceImpl() {
    }

    /*
    * Constructor fot tests
    * */
    public SearchServiceImpl(SearchableFabric fabric) {
        this.fabric = Objects.requireNonNull(fabric);
    }

    @Override
    public SearchResult search(SearchQuery query) {
        Objects.requireNonNull(query);
        Searchable searchable = fabric.create(query.getTargetType(), query.getTargetName());
        return searchable.search(query.getParams());
    }
}

