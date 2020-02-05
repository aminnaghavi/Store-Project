package ir.store.DAO.Impl;

import ir.store.DAO.Entity.ItemsEntity;
import ir.store.DAO.ItemsDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Created by B3 on 12/18/2015.
 */
@Component
@Qualifier("itemsDAOImpl")
public class ItemsDAOImpl implements ItemsDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public ItemsDAOImpl() {
        //nothing
    }

    public List<ItemsEntity> getItems() {
        Query query = sessionFactory.getCurrentSession().createQuery("from ItemsEntity ");
        return query.list();
    }

    public List<ItemsEntity> searchItems(int itemID) {
        Query query;
        query = sessionFactory.getCurrentSession().createQuery("from ItemsEntity IE where IE.itemId=:ItemID");
        query.setParameter("ItemID", itemID);
        return query.list();
    }

    public List<ItemsEntity> searchItems(String model, String name) {
        Query query;
        query = sessionFactory.getCurrentSession().createQuery("from ItemsEntity IE where IE.name like :ItemName and IE.model like :ItemModel");
        query.setParameter("ItemName", "%"+name+"%");
        query.setParameter("ItemModel", "%"+model+"%");
        return query.list();
    }

    public void insertItem(ItemsEntity itemsEntity) {
        sessionFactory.getCurrentSession().saveOrUpdate(itemsEntity);
    }

    public void deleteItem(ItemsEntity itemsEntity) {
        sessionFactory.getCurrentSession().delete(itemsEntity);


    }
}
