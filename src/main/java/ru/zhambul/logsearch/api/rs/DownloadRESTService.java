package ru.zhambul.logsearch.api.rs;

import ru.zhambul.logsearch.api.intercepror.AuthorizationInterceptor;
import ru.zhambul.logsearch.core.ResourceReader;
import ru.zhambul.logsearch.dao.UserActionDAO;
import ru.zhambul.logsearch.type.UserAction;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

/**
 * Created by zhambyl on 13/02/2017.
 */
@Path("/download")
@LocalBean
public class DownloadRESTService {

    private final java.nio.file.Path savePath = new ResourceReader().savePath();
    private UserActionDAO userActionDAO;

    @PostConstruct
    public void init() {
        userActionDAO = new UserActionDAO();
    }

    @GET
    @Path("/{fileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_JSON)
    @Interceptors(AuthorizationInterceptor.class)
    public Response downloadFile(@PathParam("fileName") String fileName, @Context HttpServletRequest req) {
        File file = savePath.resolve(fileName).toFile();
        userActionDAO.save(new UserAction()
                .setAction("file download")
                .setUserName(req.getRemoteUser())
        );
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                .build();
    }
}
