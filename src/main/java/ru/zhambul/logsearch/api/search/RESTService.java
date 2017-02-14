package ru.zhambul.logsearch.api.search;

import ru.zhambul.logsearch.saver.FileType;
import ru.zhambul.logsearch.saver.SearchResultSaver;
import ru.zhambul.logsearch.service.*;
import ru.zhambul.logsearch.type.SearchResult;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Objects;

/**
 * Created by zhambyl on 26/01/2017.
 */
@Path("/search")
public class RESTService {

    @EJB
    private SearchService searchService;

    @EJB
    private SearchResultSaver searchResultSaver;

    @EJB
    private ActionTracker actionTracker;

    @POST
    @Path("/text")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    //todo authentication interceptors
    public SearchResult text(final RESTRequest request) {
        SearchQuery query = createSearchQuery(request);
        return searchService.search(query);
    }

    @POST
    @Path("/file")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RESTFile file(final RESTRequest request) {
        SearchQuery query = createSearchQuery(request);
        SearchResult result = searchService.search(query);

        String fileName = searchResultSaver.save(result,
                FileType.valueOf(request.getOutputType().toUpperCase()));

        return new RESTFile().setName(fileName);
    }

    private SearchQuery createSearchQuery(RESTRequest request) {
        SearchParams params = new SearchParams(request.getRegexp());
        return new SearchQuery(targetType(request.getTargetType()), request.getTargetName(), params);
    }

    private SearchTargetType targetType(String type) {
        Objects.requireNonNull(type);
        return SearchTargetType.valueOf(type.toUpperCase());
    }
}
