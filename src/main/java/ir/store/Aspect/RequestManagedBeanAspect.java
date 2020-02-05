package ir.store.Aspect;

import ir.store.DAO.Entity.RequestsEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;

/**
 * Created by B3 on 1/26/2016.
 */
public class RequestManagedBeanAspect {
    @After(" execution(* ir.store.managed.RequestManagedBean.cancel(..)) ")
    public void logAspect4(JoinPoint joinpoint){
        //save to DB
        System.out.println(joinpoint.getSourceLocation()+" "+((RequestsEntity[])(joinpoint.getArgs()))[0].getItem()+" "+joinpoint.getSignature());

    }
}
