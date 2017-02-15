package ru.zhambul.logsearch.core;

import ru.zhambul.logsearch.type.SearchParams;
import ru.zhambul.logsearch.type.SearchResult;

/**
 * Created by zhambyl on 19/01/2017.
 */
public interface Searchable {

    String LOG_DELIMITER = "####";

    SearchResult search(SearchParams searchParams);
}
