package ir.store.managed;

import ir.store.DAO.Entity.UsersEntity;
import ir.store.service.UsersService;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="userManagedBean")
@SessionScoped
public class UserManagedBean implements Serializable {
    @ManagedProperty("#{usersService}")
    private UsersService usersService;
    @ManagedProperty(value="#{editUserManagedBean}")
    private EditUserManagedBean editUserManagedBean;
    @ManagedProperty(value="#{showUserManagedBean}")
    private ShowUserManagedBean showUserManagedBean;
    @ManagedProperty(value="#{newUserManagedBean}")
    private NewUserManagedBean newUserManagedBean;

    private UsersEntity current_user;
    private String searchUsername;
    private String searchName;
    private String searchLastname;
    private List<UsersEntity>users;
    public class PanelItem{
        public String outcome;
        public String value;

        public String getOutcome() {
            return outcome;
        }

        public void setOutcome(String outcome) {
            this.outcome = outcome;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public PanelItem(String out,String val){
            outcome=out;
            value=val;
        }
    }
    private List<UsersEntity>filteredusers;
    private List<PanelItem> leftpanelitems;

    public List<PanelItem> getLeftpanelitems() {
        return leftpanelitems;
    }

    public void setLeftpanelitems(List<PanelItem> leftpanelitems) {
        this.leftpanelitems = leftpanelitems;
    }

    FacesContext FC;
    public UsersEntity getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(UsersEntity current_user) {
        this.current_user = current_user;
    }

    public String getSearchUsername() {
        return searchUsername;
    }

    public void setSearchUsername(String searchUsername) {
        this.searchUsername = searchUsername;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchLastname() {
        return searchLastname;
    }

    public void setSearchLastname(String searchLastname) {
        this.searchLastname = searchLastname;
    }

    public List<UsersEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UsersEntity> users) {
        this.users = users;
    }

    public List<UsersEntity> getFilteredusers() {
        return filteredusers;
    }

    public void setFilteredusers(List<UsersEntity> filteredusers) {
        this.filteredusers = filteredusers;
    }

    public UsersService getUsersService() {
        return usersService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }


    public EditUserManagedBean getEditUserManagedBean() {
        return editUserManagedBean;
    }

    public void setEditUserManagedBean(EditUserManagedBean editUserManagedBean) {
        this.editUserManagedBean = editUserManagedBean;
    }

    public ShowUserManagedBean getShowUserManagedBean() {
        return showUserManagedBean;
    }

    public void setShowUserManagedBean(ShowUserManagedBean showUserManagedBean) {
        this.showUserManagedBean = showUserManagedBean;
    }

    public NewUserManagedBean getNewUserManagedBean() {
        return newUserManagedBean;
    }

    public void setNewUserManagedBean(NewUserManagedBean newUserManagedBean) {
        this.newUserManagedBean = newUserManagedBean;
    }

    @PostConstruct
    public void init() {
        leftpanelitems=new ArrayList<PanelItem>();
        leftpanelitems.add(new PanelItem(new String("/pages/unsecure/itemlist"),new String("Manage Items")));
        leftpanelitems.add(new PanelItem(new String("/pages/unsecure/requestlist"),new String("Manage Requests")));


        FC  = FacesContext.getCurrentInstance();
        current_user=(UsersEntity)FC.getExternalContext().getSessionMap().get("user");
        if (current_user == null) {
            FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/Error.xhml");
        }
        else if(!current_user.getPermition().equals("admin")){
            leftpanelitems.add(new PanelItem(new String("/pages/unsecure/Logout"),new String("Logout")));
            System.out.println("is not admin");
            FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/Error.xhml");
        }
        else {
            leftpanelitems.add(new PanelItem(new String("/pages/unsecure/userlist"),new String("Manage users")));
            leftpanelitems.add(new PanelItem(new String("/pages/unsecure/Logout"),new String("Logout")));
            users = usersService.getUsers();
        }
    }
    public void newUser(){
        UsersEntity target_user=new UsersEntity();
        newUserManagedBean.setTarget_user(target_user);
        showUserManagedBean.setTarget_user(target_user);
        users.add(target_user);
        FC  = FacesContext.getCurrentInstance();
        FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/newuser.xhml");
    }
    public void show(UsersEntity target_user){
        showUserManagedBean.setTarget_user(target_user);
        editUserManagedBean.setTarget_user(target_user);
        FC  = FacesContext.getCurrentInstance();
        FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/showuser.xhml");

    }
    public String delete(UsersEntity target_user){
        usersService.deleteUser(target_user);
        System.out.println("deleted");
        users = usersService.getUsers();
        return "userlist.xhtml?faces-redirect=true";
    }
    public void edit(UsersEntity target_user){
        showUserManagedBean.setTarget_user(target_user);
        editUserManagedBean.setTarget_user(target_user);
        FC  = FacesContext.getCurrentInstance();
        FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/edituser.xhml");

    }
    public String searchUser(){
        if(searchUsername==null && searchName==null && searchLastname==null)
            users=getUsers();
        else
            users = usersService.searchUsers(searchUsername,searchName,searchLastname);
        return "userlist.xhtml?faces-redirect=true";
         }
}