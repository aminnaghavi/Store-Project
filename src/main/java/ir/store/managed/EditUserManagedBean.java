package ir.store.managed;

import ir.HashTextTest;
import ir.store.DAO.Entity.UsersEntity;
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
@ManagedBean(name="editUserManagedBean")
@SessionScoped
public class EditUserManagedBean implements Serializable {
    FacesContext FC;
    private String password;
    private UsersEntity target_user;
    private UsersEntity current_user;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public UsersEntity getTarget_user() {
        return target_user;
    }

    public void setTarget_user(UsersEntity target_user) {

        this.target_user=target_user;
        FC=FacesContext.getCurrentInstance();
        FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/edituser.xhml");

    }
    public UsersEntity getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(UsersEntity current_user) {
        this.current_user = current_user;
    }

    @ManagedProperty("#{usersService}")
    private UsersService usersService;
    public UsersService getUsersService() {
        return usersService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
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
            System.out.println(HashTextTest.sha1(password));
            target_user.setPassword(HashTextTest.sha1(password));
            usersService.insert(target_user);
            FC = FacesContext.getCurrentInstance();
            FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/userlist.xhml");

        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    public void cancel(){
        FC=FacesContext.getCurrentInstance();
        FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/userlist.xhml");

    }
}
