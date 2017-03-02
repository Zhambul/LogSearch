package ru.zhambul.logsearch.api.interceptor;

import ru.zhambul.logsearch.core.UserActionService;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotAuthorizedException;

/**
 * Created by zhambyl on 14/02/2017.
 */
public class AuthorizationInterceptor {

    @Inject
    private UserActionService userActionService;

    @AroundInvoke
    public Object checkPermission(InvocationContext invocationContext) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) invocationContext.getParameters()[1];

        if (servletRequest.getRemoteUser() == null) {
            throw new NotAuthorizedException("not authenticated");
        }

        if (!servletRequest.isUserInRole("Administrators")) {
            throw new ForbiddenException("not admin");
        }

        return invocationContext.proceed();
    }
}
