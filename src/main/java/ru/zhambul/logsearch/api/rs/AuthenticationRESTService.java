package ru.zhambul.logsearch.api.rs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zhambul.logsearch.dao.UserActionDAO;
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

    private UserActionDAO userActionDAO;
    private final static Logger log = LoggerFactory.getLogger(AuthenticationRESTService.class);

    @PostConstruct
    public void init() {
        userActionDAO = new UserActionDAO();
    }

    @Path("/login")
    @POST
    public Response login(LoginRESTRequest loginRequest,
                          @Context HttpServletRequest req) {
        if (req.getRemoteUser() == null) {
            try {
                req.login(loginRequest.getLogin(), loginRequest.getPassword());
            } catch (ServletException e) {
                log.info("authentication fail " + loginRequest);
                userActionDAO.save(new UserAction()
                        .setUserName(loginRequest.getLogin())
                        .setAction("authentication fail"));
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            log.info("authentication success " + loginRequest);

            userActionDAO.save(new UserAction()
                    .setUserName(loginRequest.getLogin())
                    .setAction("login"));
            return Response.status(Response.Status.ACCEPTED).build();
        }
        log.info("authentication skipped " + loginRequest);
        return Response.status(Response.Status.OK).build();
    }

    @Path("/logout")
    @POST
    public Response logout(@Context HttpServletRequest req) {
        log.info("log out" + req.getRemoteUser());

        try {
            req.logout();
            userActionDAO.save(new UserAction()
                    .setUserName(req.getRemoteUser())
                    .setAction("logout"));
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        return Response.status(Response.Status.OK).build();
    }
}
