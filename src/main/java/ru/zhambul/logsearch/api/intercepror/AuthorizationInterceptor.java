package ru.zhambul.logsearch.api.intercepror;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zhambul.logsearch.core.UserPermissionService;
import ru.zhambul.logsearch.dao.UserActionDAO;
import ru.zhambul.logsearch.type.SearchRESTRequest;
import ru.zhambul.logsearch.type.UserAction;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;

/**
 * Created by zhambyl on 14/02/2017.
 */
public class AuthorizationInterceptor {

    private final UserPermissionService userPermissionService = new UserPermissionService();
    private final UserActionDAO userActionDAO = new UserActionDAO();
    private final static Logger log = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @AroundInvoke
    public Object checkPermission(InvocationContext invocationContext) throws Exception {
        SearchRESTRequest restRequest = (SearchRESTRequest) invocationContext.getParameters()[0];
        HttpServletRequest servletRequest = (HttpServletRequest) invocationContext.getParameters()[1];

        String userName = servletRequest.getRemoteUser();
        if (userName == null) {
            log.info("not authenticated");
            throw new NotAuthorizedException("not authenticated");
        }

        HttpSession session = servletRequest.getSession();

        Boolean granted = (Boolean) session.getAttribute(restRequest.getTargetName());

        if (granted == null) {
            log.info("checking permission for user " + userName + " " + restRequest);

            if (!userPermissionService.granted(userName, restRequest.getTargetTypeEnum(),
                    restRequest.getTargetName())) {

                log.info("authorization fail of user " + userName + " " + restRequest);

                userActionDAO.save(new UserAction()
                        .setUserName(userName)
                        .setAction("authorization fail")
                );
                throw new ForbiddenException("No permission for this target");
            }
            session.setAttribute(restRequest.getTargetName(), true);
        }

        return invocationContext.proceed();
    }
}
