package ru.zhambul.logsearch.dao;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by zhambyl on 13/02/2017.
 */
@Singleton
public class EntityManagerHelper {

    private EntityManager entityManager;

    public EntityManager get() {
        if(entityManager == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("entityManager");
            entityManager = emf.createEntityManager();
        }

        return entityManager;
    }
}
