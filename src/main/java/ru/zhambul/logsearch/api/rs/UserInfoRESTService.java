package ru.zhambul.logsearch.api.rs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zhambul.logsearch.core.UserPermissionService;
import ru.zhambul.logsearch.type.LoginRESTRequest;
import ru.zhambul.logsearch.type.UserInfoRESTResponse;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by zhambyl on 15/02/2017.
 */
@Path("/user")
public class UserInfoRESTService {

    private UserPermissionService userPermissionService;
    private final static Logger log = LoggerFactory.getLogger(UserInfoRESTService.class);

    @PostConstruct
    public void init() {
        userPermissionService = new UserPermissionService();
    }

    @Path("/info")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfoRESTResponse info(LoginRESTRequest loginRequest,
                                     @Context HttpServletRequest req) {
        String userName = req.getRemoteUser();

        if (userName == null) {
            log.info("not authenticated");
            throw new NotAuthorizedException("Not authenticated");
        }

        UserInfoRESTResponse response = new UserInfoRESTResponse()
                .setUserName(userName)
                .setUserPermissions(userPermissionService.expandedPermissions(userName));

        log.info("getting user info " + response);

        return response;
    }
}
