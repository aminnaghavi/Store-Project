package ir.store.Aspect;

import ir.store.DAO.Entity.ItemsEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by B3 on 1/26/2016.
 */
@Aspect
public class ItemManagedBeanAspect {
        @After("execution(* ir.store.managed.ShowItemManagedBean.purchase(..))")
    public void logAspectPurchase(JoinPoint joinpoint){
        //save to DB
        System.out.println(joinpoint.getSourceLocation()+" "+((ItemsEntity)(joinpoint.getThis())).getItemId()+" "+joinpoint.getSignature());

    }


}
