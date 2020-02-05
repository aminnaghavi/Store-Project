package ir.store.managed;

import ir.ItemAction;
import ir.ItemLink;
import ir.myfunc;
import ir.store.DAO.Entity.ItemsEntity;
import ir.store.DAO.Entity.RequestsEntity;
import ir.store.DAO.Entity.UsersEntity;
import ir.store.service.ItemsService;
import ir.store.service.RequestsService;
import ir.store.service.UsersService;
import org.springframework.http.RequestEntity;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by B3 on 1/22/2016.
 */
@ManagedBean(name="requestManagedBean")
@SessionScoped
public class RequestManagedBean {
    @ManagedProperty("#{itemsService}")
    private ItemsService itemsService;
    @ManagedProperty("#{requestsService}")
    private RequestsService requsetsService;
    @ManagedProperty("#{usersService}")
    private UsersService usersService;

    private UsersEntity current_user;
    private String searchStatus;
    private String searchItemName;
    private String searchItemModel;
    private String ActionsNumber;
    private List<String> ActionsEnable;
    public boolean enableAction(RequestsEntity RE){
        if(RE.getStatus().equals("pending")){
        if(current_user.getPermition().equals("admin")){
            if(RE.getItem().getOwner().equals(current_user.getUsername())){
                return false;
            }
            else
            {
                return true;
            }
        }
        else
            return false;
        }
        else
            return true;
    }

    public List<RequestsEntity> getRequestsList(){
        if(current_user.getPermition().equals("admin"))
            return requsetsService.getRequests();
        else
            return requsetsService.searchRequests(current_user.getUsername());

    }
    public String getActionsNumber() {
        return ActionsNumber;
    }

    public void setActionsNumber(String actionsNumber) {
        ActionsNumber = actionsNumber;
    }

    private List<RequestsEntity> requests;

    List<ItemAction> ItemActionsList;

    public List<ItemAction> getItemActionsList() {
        return ItemActionsList;
    }

    public void setItemActionsList(List<ItemAction> itemActionsList) {
        ItemActionsList = itemActionsList;
    }

    private List<RequestsEntity>filteredrequests;
    private List<ItemLink> leftpanelitems;

    public List<ItemLink> getLeftpanelitems() {
        return leftpanelitems;
    }

    public void setLeftpanelitems(List<ItemLink> leftpanelitems) {
        this.leftpanelitems = leftpanelitems;
    }

    FacesContext FC;

    public UsersEntity getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(UsersEntity current_user) {
        this.current_user = current_user;
    }

    public String getSearchItemName() {
        return searchItemName;
    }

    public void setSearchItemName(String searchItemName) {
        this.searchItemName = searchItemName;
    }

    public String getSearchItemModel() {
        return searchItemModel;
    }

    public void setSearchItemModel(String searchItemModel) {
        this.searchItemModel = searchItemModel;
    }

    public String getSearchStatus() {
        return searchStatus;
    }

    public void setSearchStatus(String searchStatus) {
        this.searchStatus = searchStatus;
    }

    public List<RequestsEntity> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestsEntity> requests) {
        this.requests = requests;
    }

    public List<RequestsEntity> getFilteredrequests() {
        return filteredrequests;
    }

    public void setFilteredrequests(List<RequestsEntity> filteredrequests) {
        this.filteredrequests = filteredrequests;
    }

    public ItemsService getItemsService() {
        return itemsService;
    }

    public void setItemsService(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    public RequestsService getRequsetsService() {
        return requsetsService;
    }

    public void setRequsetsService(RequestsService requsetsService) {
        this.requsetsService = requsetsService;
    }

    public UsersService getUsersService() {
        return usersService;
    }

    public void setUsersService(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostConstruct
    public void init() {
        leftpanelitems=new ArrayList<ItemLink>();
        leftpanelitems.add(new ItemLink(new String("/pages/unsecure/itemlist"),new String("Manage Items")));
        leftpanelitems.add(new ItemLink(new String("/pages/unsecure/requestlist"),new String("Manage Requests")));


        FC  = FacesContext.getCurrentInstance();
        current_user=(UsersEntity)FC.getExternalContext().getSessionMap().get("user");
        if (current_user == null) {
            FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/Error.xhml");
        }
        else if(!current_user.getPermition().equals("admin")){
            ActionsNumber=new String("1");
            ItemActionsList=new ArrayList<ItemAction>();
//            ItemActionsList.add(new ItemAction(new String("show"),false,new myfunc() {
//                @Override
//                public void func(Object target) {
//                    show((RequestsEntity)target);
//                }
//            }));

            ItemActionsList.add(new ItemAction(new String("cancel"),new String("requestlist.xhtml?faces-redirect=true"),true,new myfunc() {
                @Override
                public void func(Object target) {
                    cancel((RequestsEntity) target);
                }
            }));



            leftpanelitems.add(new ItemLink(new String("/pages/unsecure/Logout"),new String("Logout")));
            System.out.println("is not admin");
            requests = requsetsService.searchRequests(current_user.getUsername());
        }
        else {
            ActionsNumber=new String("2");
            ItemActionsList=new ArrayList<ItemAction>();
//            ItemActionsList.add(new ItemAction(new String("show"),false,new myfunc() {
//                @Override
//                public void func(Object target) {
//                    show((RequestsEntity) target);
//                }
//            }));
            ItemActionsList.add(new ItemAction(new String("approve"),new String("requestlist.xhtml?faces-redirect=true"),false,new myfunc() {
                @Override
                public void func(Object target) {
                    approve((RequestsEntity) target);
                }
            }));
            ItemActionsList.add(new ItemAction(new String("ignore"),new String("requestlist.xhtml?faces-redirect=true"),true,new myfunc() {
                @Override
                public void func(Object target) {
                    ignore((RequestsEntity) target);
                }
            }));
            

            leftpanelitems.add(new ItemLink(new String("/pages/unsecure/userlist"),new String("Manage users")));
            leftpanelitems.add(new ItemLink(new String("/pages/unsecure/Logout"),new String("Logout")));
            requests = requsetsService.getRequests();

        }
    }

    public void cancel(RequestsEntity target_request){
        if(!target_request.getStatus().equals("approved")){
        List<ItemsEntity> IEL=itemsService.searchItems(target_request.getItem().getItemId());
        Iterator<ItemsEntity> IEI=IEL.iterator();
        ItemsEntity target_item=IEI.next();
        current_user.setBalance(current_user.getBalance()+target_item.getPrise());
        usersService.insert(current_user);
            target_item.setNumber(target_item.getNumber()+1);
            itemsService.insertItem(target_item);
        requsetsService.deleteRequest(target_request);
        }
        requests=getRequestsList();
    }
    public void approve(RequestsEntity target_request){
        target_request.setStatus("approved");
        requsetsService.insertRequest(target_request);
        List<ItemsEntity> IEL=itemsService.searchItems(target_request.getItem().getItemId());
        Iterator<ItemsEntity> IEI=IEL.iterator();
        ItemsEntity target_item=IEI.next();
        current_user.setBalance(current_user.getBalance()+target_item.getPrise());
        usersService.insert(current_user);
        requests=getRequestsList();
    }
    public void ignore(RequestsEntity target_request){
        target_request.setStatus("ignored");
        requsetsService.insertRequest(target_request);
        List<ItemsEntity> IEL=itemsService.searchItems(target_request.getItem().getItemId());
        Iterator<ItemsEntity> IEI=IEL.iterator();
        ItemsEntity target_item=IEI.next();
        List<UsersEntity> UEL=usersService.searchUsers(target_request.getUser().getUsername());
        Iterator<UsersEntity> UEI=UEL.iterator();
        UsersEntity target_user=UEI.next();
        target_user.setBalance(target_user.getBalance()+target_item.getPrise());
        usersService.insert(target_user);
        target_item.setNumber(target_item.getNumber()+1);
        itemsService.insertItem(target_item);
        requests=getRequestsList();
    }

    public String searchRequest(){

        if(searchStatus.isEmpty() && searchItemName.isEmpty() && searchItemModel.isEmpty() ) {
            requests=getRequestsList();
        }
        else
        {
            if(current_user.getPermition().equals("admin")){
                requests = requsetsService.searchRequests(searchItemModel,searchItemName,searchStatus);
            }
            else {
                requests = requsetsService.searchRequests(current_user.getUsername(), searchItemModel,searchItemName, searchStatus);
                System.out.println(searchItemName + " " + searchStatus+" "+searchItemModel);
            }

        }
        return "requestlist.xhtml?faces-redirect=true";
    }
}
