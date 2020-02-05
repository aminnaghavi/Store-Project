package ir.store.managed;

import ir.store.DAO.Entity.UsersEntity;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


/**
 * Created by B3 on 1/21/2016.
 */
@ManagedBean(name="showUserManagedBean")
@SessionScoped
public class ShowUserManagedBean implements Serializable {

    FacesContext FC;
    private String password;
    private UsersEntity current_user;
    private UsersEntity target_user;
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
    }

    public UsersEntity getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(UsersEntity current_user) {
        this.current_user = current_user;
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
    public void submit(){
        FC=FacesContext.getCurrentInstance();
        FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/showuser.xhml");


    }
    public void edit(){
        System.out.println(target_user.getUsername());
        FC=FacesContext.getCurrentInstance();
        FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/edituser.xhml");

    }
    public void cancel(){
        FC=FacesContext.getCurrentInstance();
        FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/userlist.xhml");

    }
}
