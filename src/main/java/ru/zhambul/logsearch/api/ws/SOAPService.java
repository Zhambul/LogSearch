package ru.zhambul.logsearch.api.ws;

import ru.zhambul.logsearch.type.SearchParams;
import ru.zhambul.logsearch.type.SearchQuery;
import ru.zhambul.logsearch.core.SearchService;
import ru.zhambul.logsearch.type.SearchTargetTypeEnum;
import ru.zhambul.logsearch.type.SearchResult;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by zhambyl on 23/01/2017.
 */
@WebService(name = "LogSearch")
@Stateless
public class SOAPService {

    private SearchService searchService = new SearchService();

    @WebMethod(operationName = "Search")
    public SearchResult search() {
        SearchParams params = new SearchParams("weblogic");
        SearchQuery query = new SearchQuery(SearchTargetTypeEnum.SERVER, "standalone_server", params);

        return searchService.search(query);
    }

}
