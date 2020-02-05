package ir.store.SOAP.Server;

import ir.store.DAO.Entity.ItemsEntity;
import ir.store.service.ItemsService;
import ir.store.service.UsersService;

import javax.faces.bean.ManagedProperty;
import javax.jws.WebService;
import java.util.Iterator;
import java.util.List;

/**
 * Created by B3 on 1/25/2016.
 */
@WebService(endpointInterface = "ir.store.SOAP.Server.FindServiceInterface")
public class FindServiceImpl implements FindServiceInterface {
    @ManagedProperty("#{itemsService}")
    private ItemsService itemsService;

    public ItemsService getItemsService() {
        return itemsService;
    }

    public void setItemsService(ItemsService itemsService) {
        this.itemsService = itemsService;
    }
    @ManagedProperty("#{usersService}")
    private UsersService usersService;

    public UsersService getUsersService() {
        return usersService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }
    @Override
    public String[] findItems(String username,String password,String ItemModel,String ItemName){
        boolean access=usersService.access(username,password);
        if(access)
        {
            List<ItemsEntity> IEL= itemsService.searchItems(ItemModel,ItemName);
            Iterator<ItemsEntity> IEI=IEL.iterator();
            String[] result=new String[IEL.size()];
            for(int i=0;IEI.hasNext();i++){
                ItemsEntity cur=IEI.next();
                result[i]=cur.getItemId()+" "+cur.getName()+" "+cur.getModel()+" "+cur.getNumber().toString();
            }
           if(result.length!=0){
               return result;
           }
            result[0]="no item found";
            return result;
        }
        return null;
    }
}
