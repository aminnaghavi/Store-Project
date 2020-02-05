package ir.store.managed;

import ir.store.DAO.Entity.ItemsEntity;
import ir.store.DAO.Entity.UsersEntity;
import ir.store.service.ItemsService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;


/**
 * Created by B3 on 1/21/2016.
 */
@ManagedBean(name="newItemManagedBean")
@SessionScoped
public class NewItemManagedBean implements Serializable {
    FacesContext FC;
    private String password;
    private ItemsEntity target_item;
    private UsersEntity current_user;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public ItemsEntity getTarget_item() {
        return target_item;
    }

    public void setTarget_item(ItemsEntity target_item) {
        this.target_item = target_item;
    }
    public UsersEntity getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(UsersEntity current_user) {
        this.current_user = current_user;
    }

    @ManagedProperty("#{itemsService}")
    private ItemsService itemsService;
    public ItemsService getItemsService() {
        return itemsService;
    }

    public void setItemsService(ItemsService itemsService) {
        this.itemsService = itemsService;
    }


    @PostConstruct
    public void init() {
        FC = FacesContext.getCurrentInstance();
        current_user = (UsersEntity) FC.getExternalContext().getSessionMap().get("user");
        if (current_user == null) {
            FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/Error.xhml");
        }
        else if(!current_user.getPermition().equals("admin")){
            System.out.println("is not admin");
            FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/Error.xhml");
        }
    }

    public void done(){
        try {
            List<ItemsEntity> UE=itemsService.searchItems(target_item.getModel(),target_item.getName());
            if(!UE.isEmpty()) {
                FC = FacesContext.getCurrentInstance();
                System.out.println("item is exist!");
                FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/Error.xhml");
            }
            else {
                long time=new java.util.Date().getTime();
                target_item.setDate(new java.sql.Date(time));
                target_item.setOwner(current_user);
                itemsService.insertItem(target_item);
                FC = FacesContext.getCurrentInstance();
                FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/itemlist.xhml");//change me
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public void cancel(){
        FC=FacesContext.getCurrentInstance();
        FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/itemlist.xhml");

    }
}
