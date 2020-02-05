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
import java.util.List;


/**
 * Created by B3 on 1/21/2016.
 */
@ManagedBean(name="newUserManagedBean")
@SessionScoped
public class NewUserManagedBean implements Serializable {
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
        this.target_user = target_user;
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
            List<UsersEntity> UE=usersService.searchUsers(target_user.getUsername());
            if(!UE.isEmpty()) {
                FC = FacesContext.getCurrentInstance();
                System.out.println("user is exist!");
                FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/Error.xhml");
            }
            else {
                System.out.println(HashTextTest.sha1(password));
                target_user.setPassword(HashTextTest.sha1(password));
                long time=new java.util.Date().getTime();
                target_user.setLastseen(new java.sql.Timestamp(time-(time%1000)));
                System.out.println(new java.sql.Timestamp(time-(time%1000)));
                target_user.setVisitcount(0);
                usersService.insert(target_user);
                FC = FacesContext.getCurrentInstance();
                FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/showuser.xhml");
            }
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
