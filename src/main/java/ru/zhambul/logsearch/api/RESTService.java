package ru.zhambul.logsearch.api;

import ru.zhambul.logsearch.saver.FileType;
import ru.zhambul.logsearch.saver.SearchResultSaver;
import ru.zhambul.logsearch.service.SearchParams;
import ru.zhambul.logsearch.service.SearchQuery;
import ru.zhambul.logsearch.service.SearchService;
import ru.zhambul.logsearch.service.SearchTargetType;
import ru.zhambul.logsearch.type.SearchResult;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.File;
import java.util.Objects;

/**
 * Created by zhambyl on 26/01/2017.
 */
@Path("/")
public class RESTService {

    @EJB
    private SearchService searchService;

    @EJB
    private SearchResultSaver searchResultSaver;

    @POST
    @Path("/search")
    @Produces("application/json")
    @Consumes("application/json")
    public SearchResult search(final RESTRequest request) {
        SearchQuery query = createSearchQuery(request);
        return searchService.search(query);
    }

    @POST
    @Path("/search-file")
    @Produces("application/json")
    @Consumes("application/json")
    public String searchFile(final RESTRequest request) {
        SearchQuery query = createSearchQuery(request);
        SearchResult result = searchService.search(query);
        File file = searchResultSaver.save(result,
                FileType.valueOf(request.getOutputType().toUpperCase()),
                "res");

        return file.toString();
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
