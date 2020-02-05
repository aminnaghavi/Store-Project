package ir.store.DAO.Entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Set;

/**
 * Created by B3 on 1/22/2016.
 */
@Entity
@Table(name = "items", schema = "", catalog = "storedb")
public class ItemsEntity {
    private int itemId;
    private String model;
    private String name;
    private Integer prise;
    private Integer number;
    private String describtion;
    private UsersEntity owner;
    private Date date;
    private Set<RequestsEntity> requests;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "itemID",nullable = false)
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Basic
    @Column(name = "model",nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "name",nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "prise",nullable = true)
    public Integer getPrise() {
        return prise;
    }

    public void setPrise(Integer prise) {
        this.prise = prise;
    }

    @Basic
    @Column(name = "number",nullable = true)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Basic
    @Column(name = "describtion",nullable = false)
    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    @Basic
    @Column(name = "date",nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @OneToMany(mappedBy = "item")
    public Set<RequestsEntity> getRequests() {
        return requests;
    }

    public void setRequests(Set<RequestsEntity> requests) {
        this.requests = requests;
    }


    @OneToOne
    @JoinColumn(name="owner",nullable = false)

    public UsersEntity getOwner() {
        return owner;
    }

    public void setOwner(UsersEntity owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemsEntity that = (ItemsEntity) o;

        if (itemId != that.itemId) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (prise != null ? !prise.equals(that.prise) : that.prise != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (describtion != null ? !describtion.equals(that.describtion) : that.describtion != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemId;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (prise != null ? prise.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (describtion != null ? describtion.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
