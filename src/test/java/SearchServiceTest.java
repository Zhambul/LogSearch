import org.junit.Before;
import org.junit.Test;
import ru.zhambul.logsearch.type.LogEntry;
import ru.zhambul.logsearch.type.converter.DomainConfigConverter;
import ru.zhambul.logsearch.type.converter.InStrToLogEntriesConverter;
import ru.zhambul.logsearch.type.converter.InStrToStringConverter;
import ru.zhambul.logsearch.type.converter.StringToLogEntriesConverter;
import ru.zhambul.logsearch.type.service.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Created by zhambyl on 19/01/2017.
 */
public class SearchServiceTest {

    private LocalSearchService searchService;

    @Before
    public void setUp() throws Exception {
        searchService = new LocalSearchService();
        searchService.setDomainConfigConverter(new DomainConfigConverter());
        InStrToLogEntriesConverter converter = new InStrToLogEntriesConverter();
        converter.setIn(new InStrToStringConverter());
        converter.setOut(new StringToLogEntriesConverter());
        searchService.setLogEntryConverter(converter);
        searchService.init();
    }

    @Test
    public void server_search() {
        SearchParams params = new SearchParams("weblogic");
        SearchQuery query = new SearchQuery(SearchTargetType.SERVER, "standalone_server", params);

        SearchResult result = searchService.search(query);
        List<LogEntry> logs = result.getLogs();
        System.out.println(logs.size());
    }

    @Test
    public void server_search_from_to() {
        SearchParams params = new SearchParams("weblogic", new Date(0), Date.from(Instant.now()));
        SearchQuery query = new SearchQuery(SearchTargetType.SERVER, "standalone_server", params);

        SearchResult result = searchService.search(query);
        List<LogEntry> logs = result.getLogs();
        System.out.println(logs.size());
    }

    @Test
    public void cluster_search() {
        SearchParams params = new SearchParams("weblogic");
        SearchQuery query = new SearchQuery(SearchTargetType.CLUSTER, "Cluster-0", params);

        SearchResult result = searchService.search(query);
        List<LogEntry> logs = result.getLogs();
        System.out.println(logs.size());
    }

    @Test
    public void cluster_search_from_to() {
        SearchParams params = new SearchParams("weblogic", new Date(0), Date.from(Instant.now()));
        SearchQuery query = new SearchQuery(SearchTargetType.CLUSTER, "Cluster-0", params);

        SearchResult result = searchService.search(query);
        List<LogEntry> logs = result.getLogs();
        System.out.println(logs.size());
    }

    @Test
    public void domain_search() {
        SearchParams params = new SearchParams("weblogic");
        SearchQuery query = new SearchQuery(SearchTargetType.DOMAIN, "base_domain", params);

        SearchResult result = searchService.search(query);
        List<LogEntry> logs = result.getLogs();
        System.out.println(logs.size());
    }
}
