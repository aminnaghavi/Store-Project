package ir.store.DAO;

import ir.store.DAO.Entity.ItemsEntity;

import java.util.List;

/**
 * Created by B3 on 1/22/2016.
 */
public interface ItemsDAO {
    public List<ItemsEntity> getItems();
    public List<ItemsEntity> searchItems(int itemID);
    public List<ItemsEntity> searchItems(String model, String name);
    public void insertItem(ItemsEntity itemsEntity);
    public void deleteItem(ItemsEntity itemsEntity);
}
