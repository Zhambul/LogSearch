package ru.zhambul.logsearch.api.rs;

import ru.zhambul.logsearch.type.LoginRESTRequest;
import ru.zhambul.logsearch.type.UserInfoRESTResponse;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by zhambyl on 15/02/2017.
 */
@Path("/user")
public class UserInfoRESTService {

    @Path("/info")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfoRESTResponse info(LoginRESTRequest loginRequest,
                                     @Context HttpServletRequest req) {
        String userName = req.getRemoteUser();

        if (userName != null) {
            UserInfoRESTResponse restResponse = new UserInfoRESTResponse();
            restResponse.setUserName(userName);
            return restResponse;
        }
        throw new NotAuthorizedException("Not authenticated");
    }
}
