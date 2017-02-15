package ru.zhambul.logsearch.dao;

import ru.zhambul.logsearch.type.UserPermission;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by zhambyl on 14/02/2017.
 */
public class UserPermissionDAO extends DAO<UserPermission> {

    public UserPermissionDAO() {
        super(UserPermission.class);
    }

    @SuppressWarnings("unchecked")
    public List<UserPermission> findByUserName(String userName) {
        Query query = entityManager.createQuery("SELECT up FROM UserPermission up WHERE up.userName = :userName");
        query.setParameter("userName", userName);
        return query.getResultList();
    }
}
