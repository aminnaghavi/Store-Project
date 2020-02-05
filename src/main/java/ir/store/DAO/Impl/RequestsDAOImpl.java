package ir.store.DAO.Impl;

import ir.store.DAO.Entity.RequestsEntity;
import ir.store.DAO.RequestsDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by B3 on 12/20/2015.
 */
@Component
@Qualifier("requestsDAOImpl")
public class RequestsDAOImpl implements RequestsDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public RequestsDAOImpl() {
        //nothing
    }
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<RequestsEntity> getRequests() {
        Query query = sessionFactory.getCurrentSession().createQuery("from RequestsEntity ");
        return query.list();
    }

    public List<RequestsEntity> searchRequestsByID(int requestID) {
        Query query = sessionFactory.getCurrentSession().createQuery("from RequestsEntity RE where RE.requestId=:RequestID");
        query.setParameter("RequestID", requestID);
        return query.list();
    }
    public List<RequestsEntity> searchRequests(String username) {
        Query query = sessionFactory.getCurrentSession().createQuery("from RequestsEntity RE where RE.user.username=:Username");
        query.setParameter("Username", username);
        return query.list();
    }
    public List<RequestsEntity> searchRequests(String username,String itemModel,String itemName,String status) {
        Query query;
        if(status==null) {
            query = sessionFactory.getCurrentSession().createQuery("from RequestsEntity RE where RE.user.username=:Username and RE.item.model like :ItemModel and RE.item.name like :ItemName ");
        }
        else
        {
                query = sessionFactory.getCurrentSession().createQuery("from RequestsEntity RE where RE.user.username=:Username and RE.item.model like :ItemModel and RE.item.name like :ItemName and RE.status=:Status ");

        }
        query.setParameter("ItemModel","%"+ itemModel+"%");
        query.setParameter("ItemName","%"+ itemName+"%");
        query.setParameter("Username", username);
        query.setParameter("Status", status);
        return query.list();
    }
    public List<RequestsEntity> searchRequests(String itemModel,String itemName,String status) {
        Query query;
        if(status==null) {
            query = sessionFactory.getCurrentSession().createQuery("from RequestsEntity RE where  RE.item.model like :ItemModel and RE.item.name like :ItemName ");
        }
        else
        {
            query = sessionFactory.getCurrentSession().createQuery("from RequestsEntity RE where  RE.item.model like :ItemModel and RE.item.name like :ItemName and RE.status=:Status ");

        }
        query.setParameter("ItemModel","%"+ itemModel+"%");
        query.setParameter("ItemName","%"+ itemName+"%");
        query.setParameter("Status", status);
        return query.list();
    }
    public void insertRequest(RequestsEntity requestsEntity) {
        sessionFactory.getCurrentSession().saveOrUpdate(requestsEntity);
    }

    public void deleteRequest(RequestsEntity requestsEntity) {
        sessionFactory.getCurrentSession().delete(requestsEntity);


    }
}
