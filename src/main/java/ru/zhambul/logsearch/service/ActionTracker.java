package ru.zhambul.logsearch.service;

import ru.zhambul.logsearch.type.UserAction;

import javax.ejb.Local;

/**
 * Created by zhambyl on 13/02/2017.
 */
@Local
public interface ActionTracker {

    void track(UserAction userAction);
}
