package ir.store.Aspect;

import ir.LogElement;
import ir.store.DAO.Entity.ItemsEntity;
import ir.store.DAO.Entity.RequestsEntity;
import ir.store.DAO.ItemsDAO;
import ir.store.DAO.RequestsDAO;
import ir.store.service.ItemsService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.ManagedProperty;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by B3 on 1/23/2016.
 */
@Aspect
public class LoggingAspect {
    @Autowired
    ItemsDAO itemsDAO;
    @Autowired
    RequestsDAO requestsDAO;
    public static List<LogElement> logElements;
    public LoggingAspect(){
        logElements=new ArrayList<LogElement>();
    }
    @After("execution(* ir.store.DAO.Impl.RequestsDAOImpl.insertRequest(..))")
    public void logAfter(JoinPoint joinpoint){
        RequestsEntity curRequest=(RequestsEntity)joinpoint.getArgs()[0];
        List<ItemsEntity> IEL=itemsDAO.searchItems(curRequest.getItem().getItemId());
        Iterator<ItemsEntity> IEI=IEL.iterator();
        ItemsEntity curItem=IEI.next();
        List<RequestsEntity> REL=requestsDAO.searchRequests(curRequest.getUser().getUsername(),curItem.getModel(),curItem.getName(),"pending");
        RequestsEntity[] REI=REL.toArray(new RequestsEntity[0]);
        int lastRequestID=REI[REI.length-1].getRequestId();
        System.out.println(lastRequestID);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
        logElements.add(new LogElement(lastRequestID,curRequest.getUser().getUsername(),curItem.getModel(),curItem.getName(),curRequest.getDate().toString(),dateFormat.format(date).toString()));
        System.out.println("Item "+curItem.getModel()+" "+curItem.getName());
        System.out.println(((RequestsEntity)(joinpoint.getArgs()[0])).getUser().getUsername());
    }
}
