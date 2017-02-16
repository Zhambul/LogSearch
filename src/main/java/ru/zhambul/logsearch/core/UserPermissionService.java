package ru.zhambul.logsearch.core;

import ru.zhambul.logsearch.dao.UserPermissionDAO;
import ru.zhambul.logsearch.type.DomainConfig;
import ru.zhambul.logsearch.type.SearchTargetTypeEnum;
import ru.zhambul.logsearch.type.UserPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


/**
 * Created by zhambyl on 14/02/2017.
 */
public class UserPermissionService {

    private final UserPermissionDAO userPermissionDao = new UserPermissionDAO();
    private final DomainConfig domainConfig = DomainConfig.parse();

    public boolean granted(String userName, SearchTargetTypeEnum targetType, String targetName) {
        List<UserPermission> expandedPermissions = expandedPermissions(userName);

        return expandedPermissions.stream()
                .anyMatch(it ->
                        it.getTargetTypeEnum() == targetType &&
                                it.getTargetName().equals(targetName)
                );
    }

    public List<UserPermission> expandedPermissions(String userName) {
        return userPermissionDao.findByUserName(userName)
                .stream()
                .flatMap(this::expand)
                .map(PermissionWrapper::new)
                .distinct()
                .map(PermissionWrapper::unwrap)
                .collect(toList());
    }

    private Stream<UserPermission> expand(UserPermission permission) {
        List<UserPermission> result = new ArrayList<>();
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
    }

    private UserPermission createUserPermission(SearchTargetTypeEnum targetType,
                                                String targetName, String userName) {
        UserPermission userPermission = new UserPermission();

        userPermission.setTargetTypeEnum(targetType);
        userPermission.setTargetName(targetName);
        userPermission.setUserName(userName);

        return userPermission;
    }
}
