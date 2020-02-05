package ir.store.Job;
/**
 * Created by B3 on 1/24/2016.
 */


import ir.LogElement;
import ir.store.Aspect.LoggingAspect;
import ir.store.DAO.Entity.ItemsEntity;
import ir.store.DAO.Entity.RequestsEntity;
import ir.store.DAO.ItemsDAO;
import ir.store.DAO.RequestsDAO;
import ir.store.SOAP.Client.IntArray;
import ir.store.SOAP.Client.StringArray;
import ir.store.SOAP.Client.WebServiceImplService;
import ir.store.SOAP.Client.WebServiceInterface;
import ir.store.service.ItemsService;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.faces.bean.ManagedProperty;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class QuartzJob extends QuartzJobBean {

    @Autowired
    RequestsDAO requestsDAO;
    private static int count;

    @Override
    protected void executeInternal(JobExecutionContext jobContext)
            throws JobExecutionException {
        System.out.println("--------------------------------------------------------------------");
        System.out.println("MyJob start: " + jobContext.getFireTime());
        JobDetail jobDetail = jobContext.getJobDetail();
        System.out.println("MyJob end: " + jobContext.getJobRunTime() + ", key: " + jobDetail.getKey());
        System.out.println("MyJob next scheduled time: " + jobContext.getNextFireTime());


        StringArray SAUsername=new StringArray();
        StringArray SADate=new StringArray();
        StringArray SATime=new StringArray();
        StringArray SAItemModel=new StringArray();
        StringArray SAItemName=new StringArray();
        IntArray IARequestID=new IntArray();
        List<String> username=(SAUsername).getItem();
        List<String> date=(SADate).getItem();
        List<String> time=(SATime).getItem();
        List<String> itemmodel=(SAItemModel).getItem();
        List<String> itemname=(SAItemName).getItem();
        List<Integer> requestID=(IARequestID).getItem();
       Iterator<LogElement> REI=LoggingAspect.logElements.iterator();
        System.out.println("i'm executed");
        while(REI.hasNext())
        {
            LogElement RC=REI.next();

            username.add(RC.getUsername());
            itemmodel.add(RC.getItemModel());
            itemname.add(RC.getItemName());
            time.add(RC.getTime());
            date.add(RC.getDate());
            requestID.add(new Integer(RC.getRequestID()));
        }
        if(LoggingAspect.logElements.size()!=0) {
            WebServiceImplService WSImp = new WebServiceImplService();
            WebServiceInterface WSInt = WSImp.getWebServiceImplPort();
            IntArray filterID=WSInt.recieveLog(IARequestID, SAUsername, SAItemModel, SAItemName, SADate, SATime);
            List<Integer> filterList=filterID.getItem();
            Integer[] FA=filterList.toArray(new Integer[0]);
            if(FA.length==1 && FA[0]==0)
            {

            }
            else
            {
                for(Integer i:FA) {
                    System.out.println(i.intValue()+".......................................................................................");
                    List<RequestsEntity> deletedlist=requestsDAO.searchRequestsByID((int)i.intValue());
                    Iterator<RequestsEntity> deletediterator=deletedlist.iterator();
                    System.out.println(i.intValue()+".......................................................................................");
                    RequestsEntity deletedReq=deletediterator.next();
                    requestsDAO.deleteRequest(deletedReq);
                }
            }


        }
            count++;
            System.out.println("Job count " + count);
            System.out.println("--------------------------------------------------------------------");



    }
}
