package ru.zhambul.logsearch.api.rs;

import ru.zhambul.logsearch.core.UserActionService;
import ru.zhambul.logsearch.type.LoginRESTRequest;
import ru.zhambul.logsearch.type.UserAction;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by zhambyl on 14/02/2017.
 */
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationRESTService {

    //todo inject
    private UserActionService userActionService;

    @PostConstruct
    public void init() {
        userActionService = new UserActionService();
    }

    @POST
    @Path("/login")
    public Response login(LoginRESTRequest loginRequest,
                          @Context HttpServletRequest req) {
        if (req.getRemoteUser() == null) {
            try {
                req.login(loginRequest.getLogin(), loginRequest.getPassword());
            } catch (ServletException e) {
                userActionService.save(new UserAction()
                        .setUserName(loginRequest.getLogin())
                        .setAction("authentication fail"));
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            userActionService.save(new UserAction()
                    .setUserName(loginRequest.getLogin())
                    .setAction("login"));
            return Response.status(Response.Status.ACCEPTED).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Path("/logout")
    public Response logout(@Context HttpServletRequest req) {
        try {
            req.logout();
            userActionService.save(new UserAction()
                    .setUserName(req.getRemoteUser())
                    .setAction("logout"));
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        return Response.status(Response.Status.OK).build();
    }
}
