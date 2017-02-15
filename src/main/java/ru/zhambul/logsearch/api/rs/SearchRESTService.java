package ru.zhambul.logsearch.api.rs;

import ru.zhambul.logsearch.api.intercepror.AuthorizationInterceptor;
import ru.zhambul.logsearch.core.FileWriter;
import ru.zhambul.logsearch.core.SearchService;
import ru.zhambul.logsearch.dao.UserActionDAO;
import ru.zhambul.logsearch.type.*;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by zhambyl on 26/01/2017.
 */
@Path("/search")
@Stateless
public class SearchRESTService {

    private final SearchService searchService = new SearchService();
    private final FileWriter fileWriter = new FileWriter();
    private UserActionDAO userActionDAO;

    //todo api for all servers and clusters
    @PostConstruct
    public void init() {
        userActionDAO = new UserActionDAO();
    }

    @POST
    @Path("/text")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors(AuthorizationInterceptor.class)
    public SearchResult text(final SearchRESTRequest request, @Context HttpServletRequest req) {
        SearchQuery query = createSearchQuery(request);
        userActionDAO.save(new UserAction()
                .setAction("text search")
                .setUserName(req.getRemoteUser())
        );
        return searchService.search(query);
    }

    @POST
    @Path("/file")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors(AuthorizationInterceptor.class)
    public DownloadRESTResponse file(final SearchRESTRequest request, @Context HttpServletRequest req) {
        SearchQuery query = createSearchQuery(request);

        userActionDAO.save(new UserAction()
                .setAction("file search")
                .setUserName(req.getRemoteUser())
        );

        SearchResult result = searchService.search(query);

        String fileName = fileWriter.save(result,
                FileTypeEnum.valueOf(request.getOutputType().toUpperCase()));

        return new DownloadRESTResponse().setFileName(fileName);
    }

    private SearchQuery createSearchQuery(SearchRESTRequest request) {
        SearchParams params = new SearchParams(request.getRegexp());
        return new SearchQuery(request.getTargetTypeEnum(), request.getTargetName(), params);
    }
}
