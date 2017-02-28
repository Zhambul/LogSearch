package ru.zhambul.logsearch.api.rs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zhambul.logsearch.core.ResourceReader;
import ru.zhambul.logsearch.core.UserActionService;
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
    private UserActionService userActionService;

    @PostConstruct
    public void init() {
        userActionService = new UserActionService();
    }

    @GET
    @Path("/{fileName}")
    public Response downloadFile(@PathParam("fileName") String fileName, @Context HttpServletRequest req) {
        if (req.getRemoteUser() == null) {
            throw new NotAuthorizedException("Not authenticated");
        }

        userActionService.save(new UserAction()
                .setAction("file download")
                .setUserName(req.getRemoteUser())
        );

        File file = savePath.resolve(fileName).toFile();

        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                .build();
    }
}
