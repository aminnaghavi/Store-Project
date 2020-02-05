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


/**
 * Created by B3 on 1/21/2016.
 */
@ManagedBean(name="editItemManagedBean")
@SessionScoped
public class EditItemManagedBean implements Serializable {
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

        this.target_item=target_item;
        FC=FacesContext.getCurrentInstance();
        FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/edititem.xhml");

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
            itemsService.insertItem(target_item);
            FC = FacesContext.getCurrentInstance();
            FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/itemlist.xhml");

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
