package ir.store.DAO.Entity;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Created by B3 on 1/22/2016.
 */
@Entity
@Table(name = "requests", schema = "", catalog = "storedb")
public class RequestsEntity {
    private int requestId;
    private String vendor;
    private ItemsEntity item;
    private Date date;
    private String status;
    private UsersEntity user;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "requestID",nullable = false)
    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }


    @Basic
    @Column(name = "date",nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "status",nullable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @ManyToOne
    @JoinColumn(name="user",nullable = false)

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }


    @ManyToOne
    @JoinColumn(name="item",nullable = false)

    public ItemsEntity getItem() {
        return item;
    }

    public void setItem(ItemsEntity item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestsEntity that = (RequestsEntity) o;

        if (requestId != that.requestId) return false;
        if (item != that.item) return false;
        if (vendor != null ? !vendor.equals(that.vendor) : that.vendor != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = requestId;
        result = 31 * result + (vendor != null ? vendor.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
