//import org.junit.Test;
//import ru.zhambul.logsearch.core.SearchService;
//import ru.zhambul.logsearch.type.*;
//
//import java.time.Instant;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by zhambyl on 19/01/2017.
// */
//public class SearchServiceTest {
//
//    private SearchService searchService = new SearchService();
//
//    @Test
//    public void server_search() {
//        SearchParams params = new SearchParams("weblogic");
//        SearchQuery query = new SearchQuery(SearchTargetTypeEnum.SERVER, "standalone_server", params);
//
//        SearchResult result = searchService.search(query);
//        List<LogEntry> logs = result.getLogs();
//        System.out.println(logs.size());
//    }
//
//    @Test
//    public void server_search_from_to() {
//        SearchParams params = new SearchParams("weblogic", new Date(0), Date.from(Instant.now()));
//        SearchQuery query = new SearchQuery(SearchTargetTypeEnum.SERVER, "standalone_server", params);
//
//        SearchResult result = searchService.search(query);
//        List<LogEntry> logs = result.getLogs();
//        System.out.println(logs.size());
//    }
//
//    @Test
//    public void cluster_search() {
//        SearchParams params = new SearchParams("weblogic");
//        SearchQuery query = new SearchQuery(SearchTargetTypeEnum.CLUSTER, "Cluster-0", params);
//
//        SearchResult result = searchService.search(query);
//        List<LogEntry> logs = result.getLogs();
//        System.out.println(logs.size());
//    }
//
//    @Test
//    public void cluster_search_from_to() {
//        SearchParams params = new SearchParams("weblogic", new Date(0), Date.from(Instant.now()));
//        SearchQuery query = new SearchQuery(SearchTargetTypeEnum.CLUSTER, "Cluster-0", params);
//
//        SearchResult result = searchService.search(query);
//        List<LogEntry> logs = result.getLogs();
//        System.out.println(logs.size());
//    }
//
//    @Test
//    public void domain_search() {
//        SearchParams params = new SearchParams("weblogic");
//        SearchQuery query = new SearchQuery(SearchTargetTypeEnum.DOMAIN, "base_domain", params);
//
//        SearchResult result = searchService.search(query);
//        List<LogEntry> logs = result.getLogs();
//        System.out.println(logs.size());
//    }
//}
