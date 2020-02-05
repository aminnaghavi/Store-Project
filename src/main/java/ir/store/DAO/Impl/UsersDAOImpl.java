package ir.store.DAO.Impl;

import ir.store.DAO.Entity.UsersEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ir.store.DAO.UsersDAO;
import java.util.List;

/**
 * Created by a4 on 12/8/2015.
 */
@Component
@Qualifier("usersDAOImpl")
public class UsersDAOImpl implements UsersDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<UsersEntity> getUsers() {
        Query query = sessionFactory.getCurrentSession().createQuery("from UsersEntity ");
        return query.list();
    }

    public List<UsersEntity> searchUsers(String username) {
        Query query = sessionFactory.getCurrentSession().createQuery("from UsersEntity UE where UE.username=:Username");
        query.setParameter("Username",username);
        return query.list();
    }

    public List<UsersEntity> searchUsers(String username,String name,String lastname) {
        Query query;
        query = sessionFactory.getCurrentSession().createQuery("from UsersEntity UE where UE.username like :UserName and UE.name like :FirstName and UE.lastname like :LastName");
        query.setParameter("UserName", "%"+username+"%");
        query.setParameter("FirstName", "%"+name+"%");
        query.setParameter("LastName", "%"+lastname+"%");
        return query.list();
    }

    public void insertUser(UsersEntity usersEntity) {
        sessionFactory.getCurrentSession().saveOrUpdate(usersEntity);
    }

    public void deleteUser(UsersEntity usersEntity) {
        sessionFactory.getCurrentSession().delete(usersEntity);

    }
    public boolean access(String username,String password){
        Query query;
        query=sessionFactory.getCurrentSession().createQuery("from UsersEntity UE where UE.username=:Username and UE.password=sha1(:Password)");
        query.setParameter("Username",username);
        query.setParameter("Password",password);
        if(query.list().isEmpty())
            return false;
        return true;
    }
}
