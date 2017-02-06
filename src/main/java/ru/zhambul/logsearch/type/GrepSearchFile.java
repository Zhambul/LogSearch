package ru.zhambul.logsearch.type;

import ru.zhambul.logsearch.type.service.SearchParams;
import ru.zhambul.logsearch.type.service.SearchResult;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by zhambyl on 23/01/2017.
 */
public class GrepSearchFile implements SearchFile {

    @Override
    public SearchResult search(SearchParams searchParams) {
        throw new NotImplementedException();
    }
}
