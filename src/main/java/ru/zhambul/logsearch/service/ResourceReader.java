package ru.zhambul.logsearch.service;

import javax.ejb.Local;

/**
 * Created by zhambyl on 13/02/2017.
 */
@Local
public interface ResourceReader {

    String read(String name);
}
