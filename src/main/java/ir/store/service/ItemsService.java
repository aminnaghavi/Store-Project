package ir.store.service;

import ir.store.DAO.Entity.ItemsEntity;
import ir.store.DAO.ItemsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by B3 on 1/22/2016.
 */
@Service("itemsService")
@Transactional
public class ItemsService {
    @Autowired
    ItemsDAO itemsDAO;
    public List<ItemsEntity> getItems(){return itemsDAO.getItems();}
    public List<ItemsEntity> searchItems(int itemID){return itemsDAO.searchItems(itemID);}
    public List<ItemsEntity> searchItems(String model, String name){return itemsDAO.searchItems(model, name);}
    public void insertItem(ItemsEntity itemsEntity){itemsDAO.insertItem(itemsEntity);}
    public void deleteItem(ItemsEntity itemsEntity){itemsDAO.deleteItem(itemsEntity);}

}
