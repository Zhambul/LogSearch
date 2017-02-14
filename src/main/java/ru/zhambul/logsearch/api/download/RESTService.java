package ru.zhambul.logsearch.api.download;

import ru.zhambul.logsearch.saver.SearchResultSaver;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

/**
 * Created by zhambyl on 13/02/2017.
 */
@Path("/download")
public class RESTService {

    @EJB
    private SearchResultSaver searchResultSaver;

    @GET
    @Path("/{fileName}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response downloadFile(@PathParam("fileName") String fileName) {
        File file = searchResultSaver.getByName(fileName);

        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"")
                .build();
    }
}
