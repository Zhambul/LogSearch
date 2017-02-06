package ru.zhambul.logsearch.soap;

import ru.zhambul.logsearch.type.service.SearchParams;
import ru.zhambul.logsearch.type.service.SearchQuery;
import ru.zhambul.logsearch.type.service.SearchResult;
import ru.zhambul.logsearch.type.service.SearchTargetType;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Created by zhambyl on 23/01/2017.
 */
@WebService(name = "LogSearch")
@Stateless
public class SOAPService {

    @WebMethod(operationName = "Search")
    public SearchResult search() {
        SearchParams params = new SearchParams("weblogic");
        SearchQuery query = new SearchQuery(SearchTargetType.SERVER, "standalone_server", params);

        return new SearchResult();
    }

}
