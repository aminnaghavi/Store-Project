package ir.store.managed;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by B3 on 1/23/2016.
 */
@ManagedBean(name="errorManagedBean")
@SessionScoped
public class ErrorManagedBean {


    @PostConstruct
    public void init(){

    }

}
