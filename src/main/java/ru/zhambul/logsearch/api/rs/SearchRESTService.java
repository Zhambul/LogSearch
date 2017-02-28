package ru.zhambul.logsearch.api.rs;

import ru.zhambul.logsearch.api.interceptor.AuthorizationInterceptor;
import ru.zhambul.logsearch.core.LogEntriesWriter;
import ru.zhambul.logsearch.core.Searcher;
import ru.zhambul.logsearch.core.SearcherFabric;
import ru.zhambul.logsearch.core.UserActionService;
import ru.zhambul.logsearch.type.*;

import javax.annotation.PostConstruct;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by zhambyl on 26/01/2017.
 */
@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Interceptors(AuthorizationInterceptor.class)
public class SearchRESTService {

    private SearcherFabric fabric = new SearcherFabric();
    private final LogEntriesWriter logEntriesWriter = new LogEntriesWriter();
    private UserActionService userActionService;

    @PostConstruct
    public void init() {
        userActionService = new UserActionService();
    }

    @POST
    @Path("/text")
    public List<LogEntry> text(SearchRESTRequest request, @Context HttpServletRequest req) {
        userActionService.save(new UserAction()
                .setAction("text search")
                .setUserName(req.getRemoteUser())
        );

        return getLogEntries(request);
    }

    @POST
    @Path("/file")
    public DownloadRESTResponse file(SearchRESTRequest request, @Context HttpServletRequest req) {
        userActionService.save(new UserAction()
                .setAction("file search")
                .setUserName(req.getRemoteUser())
        );

        List<LogEntry> logEntries = getLogEntries(request);

        String fileName = logEntriesWriter.write(logEntries, request.getOutputType());

        return new DownloadRESTResponse().setFileName(fileName);
    }

    private List<LogEntry> getLogEntries(SearchRESTRequest request) {
        SearchQuery query = new SearchQuery()
                .setRegExp(request.getRegexp());
        Searcher searcher = fabric.create(request.getTargetType(), request.getTargetName());
        return searcher.search(query);
    }
}
