package ru.zhambul.logsearch.core;

import ru.zhambul.logsearch.dao.UserPermissionDAO;
import ru.zhambul.logsearch.type.SearchTargetTypeEnum;
import ru.zhambul.logsearch.type.UserPermission;
import ru.zhambul.logsearch.type.DomainConfig;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * Created by zhambyl on 14/02/2017.
 */
public class UserPermissionChecker {

    private final UserPermissionDAO userPermissionDao = new UserPermissionDAO();
    private final DomainConfig domainConfig = DomainConfig.parse();

    public boolean granted(String userName, SearchTargetTypeEnum targetType, String targetName) {
        List<UserPermission> userPermissions = userPermissionDao.findByUserName(userName);
        List<UserPermission> expandedPermissions = expandPermissions(userPermissions, domainConfig);

        return expandedPermissions.stream()
                .anyMatch(it ->
                        it.getTargetTypeEnum() == targetType &&
                                it.getTargetName().equalsIgnoreCase(targetName)
                );
    }

    private List<UserPermission> expandPermissions(List<UserPermission> userPermissions, DomainConfig domainConfig) {
        return userPermissions
                .stream()
                .flatMap(it -> {
                    List<UserPermission> result = new ArrayList<>();
                    result.add(it);

                    switch (it.getTargetTypeEnum()) {
                        case CLUSTER:
                            result.addAll(domainConfig.getServers()
                                    .stream()
                                    .filter(server -> it.getTargetName().equalsIgnoreCase(server.getClusterName()))
                                    .map(server -> createUserPermission(SearchTargetTypeEnum.SERVER, server.getName(), it.getUserName()))
                                    .collect(toList()));
                            break;

                        case DOMAIN:
                            if (domainConfig.getName().equals(it.getTargetName())) {
                                result.addAll(domainConfig.getServers()
                                        .stream()
                                        .map(server -> createUserPermission(SearchTargetTypeEnum.SERVER, server.getName(), it.getUserName()))
                                        .collect(toList()));

                                result.addAll(domainConfig.getClusters()
                                        .stream()
                                        .map(clusterName -> createUserPermission(SearchTargetTypeEnum.CLUSTER, clusterName.getName(), it.getUserName()))
                                        .collect(toList()));
                            }
                            break;
                    }
                    return result.stream();
                })
                .collect(toList());
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
