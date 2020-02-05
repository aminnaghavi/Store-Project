package ir.store.managed;

import ir.store.DAO.Entity.UsersEntity;
import ir.store.service.UsersService;
import ir.HashTextTest;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

@ManagedBean(name="loginManagedBean")
@SessionScoped
public class LoginManagedBean implements Serializable{

    private String username;
    private String password;
    private String result;
    @ManagedProperty("#{usersService}")
    private UsersService usersService;
    public UsersService getUsersService() {
        return usersService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String login(){
        try {
/*
            UsersEntity UE=new UsersEntity();
            UE.setUsername("amin");
            UE.setPassword(HashTextTest.sha1("123456"));
            UE.setName("amin");
            UE.setLastname("nagh");
            UE.setEmail("amin@bw.com");
            UE.setBalance(0);
            UE.setVisitcount(0);
            java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
            long time=new java.util.Date().getTime();
            UE.setLastseen(new java.sql.Timestamp(time-(time%1000)));
            System.out.println(new java.sql.Timestamp(time-(time%1000)));
            UE.setPermition("admin");
            usersService.insert(UE);
*/

            List<UsersEntity> UEL=usersService.searchUsers(this.username);
            if(!UEL.isEmpty()) {

                Iterator<UsersEntity> UEI = UEL.iterator();
                UsersEntity current_user = UEI.next();
                if (HashTextTest.sha1(password).equals(current_user.getPassword())) {
                    long time=new java.util.Date().getTime();
                    current_user.setLastseen(new java.sql.Timestamp(time-(time%1000)));
                    System.out.println(new java.sql.Timestamp(time-(time%1000)));
                    current_user.setVisitcount(current_user.getVisitcount()+1);
                    this.result = "You must directed to next page";
                    usersService.insert(current_user);
                    FacesContext FC = FacesContext.getCurrentInstance();
                    FC.getExternalContext().getSessionMap().put("user",current_user);
                    return "itemlist.xhtml?faces-redirect=true";
                } else {
                    this.result = "username or password is incorrect";

                }
            }
        }
        catch (Exception e){
            System.out.println("hash "+e.getMessage());
            e.printStackTrace();
        }
        return "";
    }
    @PostConstruct
    public void init(){
        result = "";
    }


}