package ru.zhambul.logsearch.type;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by zhambyl on 15/02/2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserInfoRESTResponse {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
