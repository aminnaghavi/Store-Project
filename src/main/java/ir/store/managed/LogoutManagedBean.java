package ir.store.managed;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Created by B3 on 1/23/2016.
 */
@ManagedBean(name="logoutManagedBean")
@RequestScoped
public class LogoutManagedBean {
    @PostConstruct
    public void init(){
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();

    }
    public String clear(){
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        return "list.xhtml";
    }

}
