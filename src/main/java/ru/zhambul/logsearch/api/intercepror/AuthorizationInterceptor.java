package ru.zhambul.logsearch.api.intercepror;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zhambul.logsearch.core.UserPermissionService;
import ru.zhambul.logsearch.dao.UserActionDAO;
import ru.zhambul.logsearch.type.SearchRESTRequest;
import ru.zhambul.logsearch.type.UserAction;
import ru.zhambul.logsearch.type.UserPermission;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;
import java.util.List;

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
        checkAuthentication(userName);

        List<UserPermission> userPermissions = getUserPermissions(restRequest, servletRequest, userName);

        if (!isGranted(restRequest, userPermissions)) {
            log.info("authorization fail of user " + userName + " " + restRequest);

            userActionDAO.save(new UserAction()
                    .setUserName(userName)
                    .setAction("authorization fail")
            );
            throw new ForbiddenException("No permission for this target");
        }

        return invocationContext.proceed();
    }

    private void checkAuthentication(String userName) {
        if (userName == null) {
            log.info("not authenticated");
            throw new NotAuthorizedException("not authenticated");
        }
    }

    private boolean isGranted(SearchRESTRequest restRequest, List<UserPermission> userPermissions) {
        return userPermissions.stream()
                .anyMatch(it ->
                        it.getTargetTypeEnum() == restRequest.getTargetTypeEnum() &&
                                it.getTargetName().equals(restRequest.getTargetName())
                );
    }

    private List<UserPermission> getUserPermissions(SearchRESTRequest restRequest, HttpServletRequest servletRequest, String userName) {
        List<UserPermission> userPermissions = (List<UserPermission>) servletRequest.getSession().getAttribute("permissions");

        if (userPermissions == null) {
            log.info("getting permissions for user " + userName + " " + restRequest);
            userPermissions = userPermissionService.expandedPermissions(userName);
            servletRequest.getSession().setAttribute("permissions", userPermissions);
        }
        return userPermissions;
    }
}
