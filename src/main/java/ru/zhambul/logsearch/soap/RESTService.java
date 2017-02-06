package ru.zhambul.logsearch.soap;

import ru.zhambul.logsearch.type.service.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by zhambyl on 26/01/2017.
 */
@Path("/")
@Stateless
public class RESTService {

    @EJB
    private SearchService searchService;

    @POST
    @Path("/search")
    @Produces("application/json")
    @Consumes("application/json")
    public String search(final RESTRequest request) {
        SearchQuery query = createSearchQuery(request);
        SearchResult result = searchService.search(query);

        return String.valueOf(result.getLogs().size());
    }

    private SearchQuery createSearchQuery(RESTRequest request) {
        SearchParams params = new SearchParams(request.getRegexp());
        return new SearchQuery(targetType(request.getTargetType()), request.getTargetName(), params);
    }

    private SearchTargetType targetType(String type) {
        return SearchTargetType.valueOf(type.toUpperCase());
    }
}
