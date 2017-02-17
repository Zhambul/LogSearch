package ru.zhambul.logsearch.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zhambul.logsearch.api.intercepror.AuthorizationInterceptor;
import ru.zhambul.logsearch.dao.UserPermissionDAO;
import ru.zhambul.logsearch.type.DomainConfig;
import ru.zhambul.logsearch.type.SearchTargetTypeEnum;
import ru.zhambul.logsearch.type.UserPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;


/**
 * Created by zhambyl on 14/02/2017.
 */
public class UserPermissionService {

    private final UserPermissionDAO userPermissionDao = new UserPermissionDAO();
    private final DomainConfig domainConfig = DomainConfig.parse();
    private final static Logger log = LoggerFactory.getLogger(AuthorizationInterceptor.class);


    //todo TreeSet distinct, not wrapper distinct
    //todo refactor
    public List<UserPermission> expandedPermissions(String userName) {
        List<UserPermission> permissions = userPermissionDao.findByUserName(userName);
        Set<PermissionWrapper> expandedPermissions = permissions
                .stream()
                .flatMap(permission -> expand(permission, true))
                .map(PermissionWrapper::new)
                .distinct()
                .collect(toSet());
        log.info("expanded permissions " + expandedPermissions);
        Set<PermissionWrapper> restricted = permissions
                .stream()
                .flatMap(permission -> expand(permission, false))
                .map(PermissionWrapper::new)
                .distinct()
                .collect(toSet());

        log.info("restricted " + restricted);

        expandedPermissions.removeAll(restricted);

        return expandedPermissions
                .stream()
                .map(PermissionWrapper::unwrap)
                .collect(toList());
    }

    private Stream<UserPermission> expand(UserPermission permission, boolean granted) {
        List<UserPermission> result = new ArrayList<>();
        if (permission.isGranted() == granted) {
            result.add(permission);

            switch (permission.getTargetTypeEnum()) {
                case CLUSTER:
                    result.addAll(domainConfig.getServers()
                            .stream()
                            .filter(server -> permission.getTargetName().equals(server.getClusterName()))
                            .map(server -> createUserPermission(SearchTargetTypeEnum.SERVER,
                                    server.getName(), permission.getUserName()))
                            .collect(toList()));
                    break;

                case DOMAIN:
                    if (domainConfig.getName().equals(permission.getTargetName())) {
                        result.addAll(domainConfig.getServers()
                                .stream()
                                .map(server -> createUserPermission(SearchTargetTypeEnum.SERVER,
                                        server.getName(), permission.getUserName()))
                                .collect(toList()));

                        result.addAll(domainConfig.getClusters()
                                .stream()
                                .map(cluster -> createUserPermission(SearchTargetTypeEnum.CLUSTER,
                                        cluster.getName(), permission.getUserName()))
                                .collect(toList()));
                    }
                    break;
            }
        }
        return result.stream();
    }

    private static class PermissionWrapper {
        private final UserPermission permission;

        private PermissionWrapper(UserPermission permission) {
            this.permission = permission;
        }

        public boolean equals(Object other) {
            if (other instanceof PermissionWrapper) {
                return ((PermissionWrapper) other)
                        .permission.getTargetName().equals(permission.getTargetName())

                        &&

                        ((PermissionWrapper) other)
                                .permission.getTargetType().equals(permission.getTargetType());
            } else {
                return false;
            }
        }

        public int hashCode() {
            int result = 31;
            result = result * 17 + permission.getTargetName().hashCode();
            result = result * 17 + permission.getTargetType().hashCode();
            return result;
        }

        public UserPermission unwrap() {
            return permission;
        }

        @Override
        public String toString() {
            return permission.toString();
        }
    }

    private UserPermission createUserPermission(SearchTargetTypeEnum targetType,
                                                String targetName,
                                                String userName) {
        UserPermission userPermission = new UserPermission();

        userPermission.setTargetTypeEnum(targetType);
        userPermission.setTargetName(targetName);
        userPermission.setUserName(userName);
        return userPermission;
    }
}
