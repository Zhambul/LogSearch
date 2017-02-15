package ru.zhambul.logsearch.api.intercepror;

import ru.zhambul.logsearch.core.UserPermissionChecker;
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

    private final UserPermissionChecker userPermissionChecker = new UserPermissionChecker();
    private final UserActionDAO userActionDAO = new UserActionDAO();

    @AroundInvoke
    public Object checkUserRole(InvocationContext invocationContext) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) invocationContext.getParameters()[1];
        String userName = servletRequest.getRemoteUser();
        if (userName == null) {
            throw new NotAuthorizedException("Not authenticated");
        }

        SearchRESTRequest restRequest = (SearchRESTRequest) invocationContext.getParameters()[0];
        HttpSession session = servletRequest.getSession();

        Boolean granted = (Boolean) session.getAttribute(restRequest.getTargetName());

        if (granted == null) {
            System.out.println("checking");
            if (!userPermissionChecker.granted(userName, restRequest.getTargetTypeEnum(), restRequest.getTargetName())) {
                userActionDAO.save(new UserAction()
                        .setUserName(userName)
                        .setAction("authorization fail")
                );
                throw new ForbiddenException("No permission for this target");
            }
            session.setAttribute(restRequest.getTargetName(), true);
        }
        System.out.println("not checking");
        return invocationContext.proceed();
    }
}
