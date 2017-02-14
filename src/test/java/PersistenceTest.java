//import org.junit.Test;
//import ru.zhambul.logsearch.dao.UserActionDao;
//import ru.zhambul.logsearch.type.UserAction;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
///**
// * Created by zhambyl on 13/02/2017.
// */
//public class PersistenceTest {
//
//    @Test
//    public void asd() {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("entityManager");
//        EntityManager entityManager = emf.createEntityManager();
//
//        entityManager.getTransaction().begin();
//
//        UserAction action = new UserAction();
//        action.setAction("");
//        entityManager.persist(action);
//
//        entityManager.getTransaction().commit();
//        entityManager.close();
//    }
//
//    @Test
//    public void userActionDao() {
//        UserAction action = new UserAction();
//        action.setAction("qwewqeqwe");
//
//        UserActionDao dao = new UserActionDao();
//        dao.save(action);
//        UserAction action1 = dao.getById(1);
//
//        assert action1.getAction().equals(action.getAction());
//    }
//}
