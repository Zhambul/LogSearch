package ru.zhambul.logsearch.type;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by zhambyl on 13/02/2017.
 */
@Entity
@Table(name = "user_action")
public class UserAction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String userName;

    @Column
    private String action;

    @Column
    private Date timestamp;

    public UserAction() {
        timestamp = new Date(new java.util.Date().getTime());
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

    public Date getTimestamp() {
        return timestamp;
    }

    public UserAction setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
