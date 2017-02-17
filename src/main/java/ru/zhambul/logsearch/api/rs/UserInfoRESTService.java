package ru.zhambul.logsearch.api.rs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zhambul.logsearch.core.UserPermissionService;
import ru.zhambul.logsearch.dao.UserActionDAO;
import ru.zhambul.logsearch.type.LoginRESTRequest;
import ru.zhambul.logsearch.type.UserAction;
import ru.zhambul.logsearch.type.UserInfoRESTResponse;
import ru.zhambul.logsearch.type.UserPermission;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by zhambyl on 15/02/2017.
 */
@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserInfoRESTService {

    private UserPermissionService userPermissionService;
    private final static Logger log = LoggerFactory.getLogger(UserInfoRESTService.class);
    private UserActionDAO userActionDAO;

    @PostConstruct
    public void init() {
        userActionDAO = new UserActionDAO();
        userPermissionService = new UserPermissionService();
    }

    @Path("/info")
    @GET
    //todo interceptor for logging and user actions
    public UserInfoRESTResponse info(LoginRESTRequest loginRequest,
                                     @Context HttpServletRequest req) {
        String userName = req.getRemoteUser();

        if (userName == null) {
            log.info("not authenticated");
            throw new NotAuthorizedException("Not authenticated");
        }
        List<UserPermission> userPermissions = userPermissionService.expandedPermissions(userName);

        log.info("getting permissions for user " + userName);
        req.getSession().setAttribute("permissions", userPermissions);

        UserInfoRESTResponse response = new UserInfoRESTResponse()
                .setUserName(userName)
                .setUserPermissions(userPermissions);

        log.info("getting user info " + response);
        userActionDAO.save(new UserAction()
                .setAction("getting user info")
                .setUserName(req.getRemoteUser())
        );
        return response;
    }
}
