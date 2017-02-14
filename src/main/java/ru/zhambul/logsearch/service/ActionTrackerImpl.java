package ru.zhambul.logsearch.service;

import ru.zhambul.logsearch.dao.UserActionDao;
import ru.zhambul.logsearch.type.UserAction;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created by zhambyl on 13/02/2017.
 */
@Stateless
public class ActionTrackerImpl implements ActionTracker {

    @Inject
    private UserActionDao userActionDao;

    @Override
    public void track(UserAction userAction) {
        userActionDao.save(userAction);
    }
}
