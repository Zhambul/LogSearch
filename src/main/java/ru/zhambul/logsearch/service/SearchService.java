package ru.zhambul.logsearch.service;

import ru.zhambul.logsearch.type.SearchResult;

import javax.ejb.Local;

/**
 * Created by zhambyl on 19/01/2017.
 */
@Local
public interface SearchService {

    SearchResult search(SearchQuery query);
}
