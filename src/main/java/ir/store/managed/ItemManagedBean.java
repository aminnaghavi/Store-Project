package ir.store.managed;

import ir.ItemAction;
import ir.ItemLink;
import ir.myfunc;
import ir.store.DAO.Entity.ItemsEntity;
import ir.store.DAO.Entity.RequestsEntity;
import ir.store.DAO.Entity.UsersEntity;
import ir.ItemAction;
import ir.store.service.ItemsService;
import ir.store.service.RequestsService;
import ir.store.service.UsersService;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="itemManagedBean")
@SessionScoped
public class ItemManagedBean implements Serializable {
    @ManagedProperty("#{itemsService}")
    private ItemsService itemsService;
    @ManagedProperty("#{requestsService}")
    private RequestsService requsetsService;
    @ManagedProperty("#{usersService}")
    private UsersService usersService;
    @ManagedProperty(value="#{editItemManagedBean}")
    private EditItemManagedBean editItemManagedBean;
    @ManagedProperty(value="#{showItemManagedBean}")
    private ShowItemManagedBean showItemManagedBean;
    @ManagedProperty(value="#{newItemManagedBean}")
    private NewItemManagedBean newItemManagedBean;


    private UsersEntity current_user;
    private String searchModel;
    private String searchName;
    private int searchID;
    private String ActionsNumber;
    private String IsAdmin;

    public String getIsAdmin() {
        return IsAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        IsAdmin = isAdmin;
    }

    public String getActionsNumber() {
        return ActionsNumber;
    }

    public void setActionsNumber(String actionsNumber) {
        ActionsNumber = actionsNumber;
    }

    private List<ItemsEntity>items;

    List<ItemAction> ItemActionsList;

    public List<ItemAction> getItemActionsList() {
        return ItemActionsList;
    }

    public void setItemActionsList(List<ItemAction> itemActionsList) {
        ItemActionsList = itemActionsList;
    }

    private List<ItemsEntity>filtereditems;
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

    public String getSearchModel() {
        return searchModel;
    }

    public void setSearchModel(String searchModel) {
        this.searchModel = searchModel;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public int getSearchID() {
        return searchID;
    }

    public void setSearchID(int searchID) {
        this.searchID = searchID;
    }

    public List<ItemsEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemsEntity> items) {
        this.items = items;
    }

    public List<ItemsEntity> getFiltereditems() {
        return filtereditems;
    }

    public void setFiltereditems(List<ItemsEntity> filtereditems) {
        this.filtereditems = filtereditems;
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

    public EditItemManagedBean getEditItemManagedBean() {
        return editItemManagedBean;
    }

    public void setEditItemManagedBean(EditItemManagedBean editItemManagedBean) {
        this.editItemManagedBean = editItemManagedBean;
    }

    public ShowItemManagedBean getShowItemManagedBean() {
        return showItemManagedBean;
    }

    public void setShowItemManagedBean(ShowItemManagedBean showItemManagedBean) {
        this.showItemManagedBean = showItemManagedBean;
    }

    public NewItemManagedBean getNewItemManagedBean() {
        return newItemManagedBean;
    }

    public void setNewItemManagedBean(NewItemManagedBean newItemManagedBean) {
        this.newItemManagedBean = newItemManagedBean;
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
            IsAdmin=new String("hidden");
            ActionsNumber=new String("2");
            ItemActionsList=new ArrayList<ItemAction>();
            ItemActionsList.add(new ItemAction(new String("show"),"",false,new myfunc() {
                @Override
                public void func(Object target) {
                    show((ItemsEntity)target);
                }
            }));

            ItemActionsList.add(new ItemAction(new String("purchase"),new String("requestlist.xhtml?faces-redirect=true"),true,new myfunc() {
                @Override
                public void func(Object target) {
                    purchase((ItemsEntity) target);
                }
            }));



            leftpanelitems.add(new ItemLink(new String("/pages/unsecure/Logout.xhtml"),new String("Logout")));
            System.out.println("is not admin");
            items = itemsService.getItems();
        }
        else {
            IsAdmin=new String("visible");
            ActionsNumber=new String("3");
            ItemActionsList=new ArrayList<ItemAction>();
            ItemActionsList.add(new ItemAction(new String("show"),"",false,new myfunc() {
                @Override
                public void func(Object target) {
                    show((ItemsEntity)target);
                }
            }));
            ItemActionsList.add(new ItemAction(new String("delete"),"",true,new myfunc() {
                @Override
                public void func(Object target) {
                    delete((ItemsEntity) target);
                }
            }));

            ItemActionsList.add(new ItemAction(new String("edit"),"",false,new myfunc() {
                @Override
                public void func(Object target) {
                    edit((ItemsEntity)target);
                }
            }));

            leftpanelitems.add(new ItemLink(new String("/pages/unsecure/userlist"),new String("Manage users")));
            leftpanelitems.add(new ItemLink(new String("/pages/unsecure/Logout.xhtml"),new String("Logout")));
            items = itemsService.getItems();
        }
    }
    public void newItem(){
        ItemsEntity target_item=new ItemsEntity();
        newItemManagedBean.setTarget_item(target_item);
        showItemManagedBean.setTarget_item(target_item);
        items.add(target_item);
        FC  = FacesContext.getCurrentInstance();
        FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/newitem.xhml");
    }
    public void show(ItemsEntity target_item){
        showItemManagedBean.setTarget_item(target_item);
        editItemManagedBean.setTarget_item(target_item);
        FC  = FacesContext.getCurrentInstance();
        FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/showitem.xhml");
        System.out.println("show data");
    }
    public String delete(ItemsEntity target_item){
        itemsService.deleteItem(target_item);
        System.out.println("deleted");
        items = itemsService.getItems();
        return "itemlist.xhtml?faces-redirect=true";
    }
    public void edit(ItemsEntity target_item){
        showItemManagedBean.setTarget_item(target_item);
        editItemManagedBean.setTarget_item(target_item);
        FC  = FacesContext.getCurrentInstance();
        FC.getApplication().getNavigationHandler().handleNavigation(FC, null, "/pages/unsecure/edititem.xhml");

    }
    public void purchase(ItemsEntity target_item){
        showItemManagedBean.setTarget_item(target_item);
       showItemManagedBean.purchase();
    }
    public String searchItem(){

        if(searchName==null && searchModel==null) {
            items=getItems();
        }
        else
        {
            items = itemsService.searchItems(searchModel,searchName);

        }
        return "itemlist.xhtml?faces-redirect=true";
    }
}