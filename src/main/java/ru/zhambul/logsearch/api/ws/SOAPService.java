package ru.zhambul.logsearch.api.ws;

import ru.zhambul.logsearch.type.LogEntry;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by zhambyl on 23/01/2017.
 */
@WebService(name = "LogSearch")
@Stateless
public class SOAPService {

    private SearchService searchService = new SearchService();

    @WebMethod(operationName = "Search")
    public List<LogEntry> search() {
//        SearchParams params = new SearchParams("weblogic");
//        SearchQuery query = new SearchQuery(SearchTargetTypeEnum.SERVER, "standalone_server", params, regExp, fromDate, toDate);
//
//        return searchService.search(query);
        return null;
    }

}
