package ru.zhambul.logsearch.dao;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import java.util.function.Supplier;

/**
 * Created by zhambyl on 13/02/2017.
 */
public abstract class AbstractDao<T> implements Dao<T> {

    @EJB
    private EntityManagerHelper emh;

    private EntityManager entityManager;

    protected Class<T> type;

    public AbstractDao(Class<T> type) {
        this.type = type;
    }

    @PostConstruct
    public void init() {
        entityManager = emh.get();
    }

    @Override
    public T getById(int id) {
        return withTransaction(() -> entityManager.find(type, id));
    }

    @Override
    public void save(T entity) {
        withTransaction(() -> entityManager.persist(entity));
    }

    protected <Y> Y withTransaction(Supplier<Y> supplier) {
        try {
            entityManager.getTransaction().begin();
            return supplier.get();
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    protected void withTransaction(Runnable runnable) {
        try {
            entityManager.getTransaction().begin();
            runnable.run();
        } finally {
            entityManager.getTransaction().commit();
        }
    }
}
