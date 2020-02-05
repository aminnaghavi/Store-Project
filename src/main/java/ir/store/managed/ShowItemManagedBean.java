package ir.store.managed;

import ir.ItemAction;
import ir.myfunc;
import ir.store.DAO.Entity.ItemsEntity;
import ir.store.DAO.Entity.RequestsEntity;
import ir.store.DAO.Entity.UsersEntity;
import ir.store.service.ItemsService;
import ir.store.service.RequestsService;
import ir.store.service.UsersService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


/**
 * Created by B3 on 1/21/2016.
 */
@ManagedBean(name="showItemManagedBean")
@SessionScoped
public class ShowItemManagedBean implements Serializable {
    @ManagedProperty("#{itemsService}")
    private ItemsService itemsService;
    @ManagedProperty("#{requestsService}")
    private RequestsService requsetsService;
    @ManagedProperty("#{usersService}")
    private UsersService usersService;
    @ManagedProperty(value="#{requestManagedBean}")
    private RequestManagedBean requestManagedBean;

    FacesContext FC;
    private String password;
    private UsersEntity current_user;
    private ItemsEntity target_item;
    private ItemAction EditOrPurchase;

    public ItemAction getEditOrPurchase() {
        return EditOrPurchase;
    }

    public void setEditOrPurchase(ItemAction editOrPurchase) {
        EditOrPurchase = editOrPurchase;
    }

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

    public RequestsService getRequsetsService() {
        return requsetsService;
    }

    public void setRequsetsService(RequestsService requsetsService) {
        this.requsetsService = requsetsService;
    }

    public ItemsService getItemsService() {
        return itemsService;
    }

    public void setItemsService(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    public UsersService getUsersService() {
        return usersService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    public RequestManagedBean getRequestManagedBean() {
        return requestManagedBean;
    }

    public void setRequestManagedBean(RequestManagedBean requestManagedBean) {
        this.requestManagedBean = requestManagedBean;
    }

    @PostConstruct
    public void init() {
        FC = FacesContext.getCurrentInstance();
        current_user = (UsersEntity) FC.getExternalContext().getSessionMap().get("user");
        if (current_user == null) {
            FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/Error.xhml");
        } else if (!current_user.getPermition().equals("admin")) {
            EditOrPurchase = new ItemAction(new String("purchase"),new String("requestlist.xhtml?faces-redirect=true"),true, new myfunc() {
                @Override
                public void func(Object target) {
                    purchase();
                }
            });
            System.out.println("is not admin");
        } else {
            EditOrPurchase = new ItemAction(new String("edit"),"", false, new myfunc() {
                @Override
                public void func(Object target) {
                    edit();
                }
            });
        }

    }

    public void edit() {
        System.out.println(target_item.getModel());
        FC = FacesContext.getCurrentInstance();
        FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/edititem.xhml");

    }

    public void cancel() {
        FC = FacesContext.getCurrentInstance();
        FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/itemlist.xhml");

    }

    public void purchase() {
        FC = FacesContext.getCurrentInstance();
        if (target_item.getPrise() > current_user.getBalance() || target_item.getNumber() < 1) {
            FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/Error.xhml");
        } else {
            System.out.println("purchased");
            RequestsEntity newRequest = new RequestsEntity();
            newRequest.setUser(current_user);
            newRequest.setItem(target_item);
            long time = new java.util.Date().getTime();
            newRequest.setDate(new java.sql.Date(time));
            newRequest.setStatus("pending");
            newRequest.setUser(current_user);
            requsetsService.insertRequest(newRequest);
            current_user.setBalance(current_user.getBalance() - target_item.getPrise());
            target_item.setNumber(target_item.getNumber() - 1);
            usersService.insert(current_user);
            itemsService.insertItem(target_item);
            requestManagedBean.setRequests(requestManagedBean.getRequestsList());
            FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/requestlist.xhml");
        }


    }
}
