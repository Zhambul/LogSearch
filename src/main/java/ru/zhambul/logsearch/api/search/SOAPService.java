package ru.zhambul.logsearch.api.search;

import ru.zhambul.logsearch.service.*;
import ru.zhambul.logsearch.type.SearchResult;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by zhambyl on 23/01/2017.
 */
@WebService(name = "LogSearch")
@Stateless
public class SOAPService {

    @EJB
    private SearchService searchService;

    @WebMethod(operationName = "Search")
    public SearchResult search() {
        SearchParams params = new SearchParams("weblogic");
        SearchQuery query = new SearchQuery(SearchTargetType.SERVER, "standalone_server", params);

        return searchService.search(query);
    }

}
