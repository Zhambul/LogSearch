package ru.zhambul.logsearch.search;

import ru.zhambul.logsearch.service.SearchTargetType;

import javax.ejb.Local;

/**
 * Created by zhambyl on 07/02/2017.
 */
@Local
public interface SearchableFabric {

    Searchable create(SearchTargetType type, String name);
}
