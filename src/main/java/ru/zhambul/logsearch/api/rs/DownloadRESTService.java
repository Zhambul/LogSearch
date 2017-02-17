package ru.zhambul.logsearch.api.rs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zhambul.logsearch.core.ResourceReader;
import ru.zhambul.logsearch.dao.UserActionDAO;
import ru.zhambul.logsearch.type.UserAction;

import javax.annotation.PostConstruct;
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
@Produces(MediaType.APPLICATION_OCTET_STREAM)
@Consumes(MediaType.APPLICATION_JSON)
public class DownloadRESTService {

    private final java.nio.file.Path savePath = new ResourceReader().savePath();
    private UserActionDAO userActionDAO;
    private final static Logger log = LoggerFactory.getLogger(DownloadRESTService.class);

    @PostConstruct
    public void init() {
        userActionDAO = new UserActionDAO();
    }

    @GET
    @Path("/{fileName}")
    public Response downloadFile(@PathParam("fileName") String fileName, @Context HttpServletRequest req) {
        if (req.getRemoteUser() == null) {
            log.info("not authenticated");
            throw new NotAuthorizedException("Not authenticated");
        }

        log.info("file download " + fileName + " for user " + req.getRemoteUser());

        userActionDAO.save(new UserAction()
                .setAction("file download")
                .setUserName(req.getRemoteUser())
        );

        File file = savePath.resolve(fileName).toFile();

        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                .build();
    }
}
