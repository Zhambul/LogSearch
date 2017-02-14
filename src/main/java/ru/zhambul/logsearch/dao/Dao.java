package ru.zhambul.logsearch.dao;

/**
 * Created by zhambyl on 13/02/2017.
 */
public interface Dao<T> {

    T getById(int id);

    void save(T entity);
}
