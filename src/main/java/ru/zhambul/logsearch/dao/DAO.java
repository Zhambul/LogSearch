package ru.zhambul.logsearch.dao;

import javax.persistence.EntityManager;
import java.util.function.Supplier;

/**
 * Created by zhambyl on 13/02/2017.
 */
public abstract class DAO<T> {

    private static final EntityManagerHelper emh = new EntityManagerHelper();

    protected EntityManager entityManager;

    protected Class<T> type;

    public DAO(Class<T> type) {
        this.type = type;
        entityManager = emh.getEntityManager();
    }

    public T getById(int id) {
        return withTransaction(() -> entityManager.find(type, id));
    }

    public void save(T entity) {
        withTransaction(() -> entityManager.persist(entity));
    }

    protected <Y> Y withTransaction(Supplier<Y> supplier) {
        try {
            entityManager.getTransaction().begin();
            return supplier.get();
        } finally {
            if(entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().commit();
            }
        }
    }

    protected void withTransaction(Runnable runnable) {
        try {
            entityManager.getTransaction().begin();
            runnable.run();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().commit();
            }
        }
    }
}
