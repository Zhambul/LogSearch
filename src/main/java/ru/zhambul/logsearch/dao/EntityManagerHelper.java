package ru.zhambul.logsearch.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by zhambyl on 13/02/2017.
 */
public class EntityManagerHelper {

    private EntityManager entityManager;

    public EntityManagerHelper() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("entityManager");
        entityManager = emf.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
