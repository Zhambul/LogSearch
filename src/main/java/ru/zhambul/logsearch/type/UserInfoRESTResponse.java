package ru.zhambul.logsearch.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

/**
 * Created by zhambyl on 15/02/2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserInfoRESTResponse {

    private String userName;

    private Collection<UserPermission> userPermissions;

    public String getUserName() {
        return userName;
    }

    public UserInfoRESTResponse setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Collection<UserPermission> getUserPermissions() {
        return userPermissions;
    }

    public UserInfoRESTResponse setUserPermissions(Collection<UserPermission> userPermissions) {
        this.userPermissions = userPermissions;
        return this;
    }

    @Override
    public String toString() {
        return "UserInfoRESTResponse{" +
                "userName='" + userName + '\'' +
                ", userPermissions=" + userPermissions +
                '}';
    }
}
