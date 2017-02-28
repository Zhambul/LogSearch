package ru.zhambul.logsearch.api.interceptor;

import ru.zhambul.logsearch.core.UserActionService;
import ru.zhambul.logsearch.type.SearchRESTRequest;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;

/**
 * Created by zhambyl on 14/02/2017.
 */
public class AuthorizationInterceptor {

    private final UserActionService userActionDAO = new UserActionService();

    @AroundInvoke
    public Object checkPermission(InvocationContext invocationContext) throws Exception {
        SearchRESTRequest restRequest = (SearchRESTRequest) invocationContext.getParameters()[0];
        HttpServletRequest servletRequest = (HttpServletRequest) invocationContext.getParameters()[1];

        String userName = servletRequest.getRemoteUser();

        if (userName == null) {
            throw new NotAuthorizedException("not authenticated");
        }

        //todo auth

        return invocationContext.proceed();
    }
}
