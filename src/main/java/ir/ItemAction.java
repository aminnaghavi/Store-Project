package ir;

import ir.store.DAO.Entity.ItemsEntity;

/**
 * Created by B3 on 1/22/2016.
 */

public class ItemAction{
private String Link;

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public ItemAction(String name,String Link,boolean question,myfunc mf){
        this.name=name;
        this.mf=mf;
        this.Link=Link;
        if(question)
            OnClickAction=new String("if (! confirm('Really want to do that?')) return false");
        else
            OnClickAction=new String("");

    }
    public myfunc getMf() {
        return mf;
    }
    public myfunc mf;
    private String OnClickAction;

    public String getOnClickAction() {
        return OnClickAction;
    }

    public void setOnClickAction(String onClickAction) {
        OnClickAction = onClickAction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    void set_func(myfunc mf){
        this.mf=mf;
    }
    public String action(Object target_item){
        mf.func(target_item);
        return Link;
    };
}