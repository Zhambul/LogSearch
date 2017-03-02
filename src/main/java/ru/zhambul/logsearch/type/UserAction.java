package ru.zhambul.logsearch.type;


import java.util.Date;

/**
 * Created by zhambyl on 13/02/2017.
 */
public class UserAction {

    private int id;
    private String userName;
    private String action;
    private long timestamp;

    public UserAction() {
        timestamp = new Date().getTime();
    }

    public int getId() {
        return id;
    }

    public UserAction setId(int id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserAction setUserName(String user) {
        this.userName = user;
        return this;
    }

    public String getAction() {
        return action;
    }

    public UserAction setAction(String action) {
        this.action = action;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
