package ru.zhambul.logsearch.dao;

import ru.zhambul.logsearch.type.UserAction;

import javax.ejb.Singleton;

/**
 * Created by zhambyl on 13/02/2017.
 */
@Singleton
public class UserActionDao extends AbstractDao<UserAction> {
    public UserActionDao() {
        super(UserAction.class);
    }
}
