package ir.store.DAO.Entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by B3 on 1/22/2016.
 */
@Entity
@Table(name = "users", schema = "", catalog = "storedb")
public class UsersEntity {
    private String username;
    private String password;
    private String name;
    private String lastname;
    private String email;
    private int balance;
    private int visitcount;
    private Timestamp lastseen;
    private String permition;
     private Set<RequestsEntity> requests;
    @Id
    @Column(name = "username",nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password",nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    @Column(name = "lastname",nullable = false)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "email",nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "balance",nullable = false,columnDefinition = "int default 0")
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "visitcount",nullable = false,columnDefinition = "int default 0")
    public int getVisitcount() {
        return visitcount;
    }

    public void setVisitcount(int visitcount) {
        this.visitcount = visitcount;
    }

    @Basic
    @Column(name = "lastseen",nullable = false)
    public Timestamp getLastseen() {
        return lastseen;
    }

    public void setLastseen(Timestamp lastseen) {
        this.lastseen = lastseen;
    }

    @Basic
    @Column(name = "permition",nullable = false)
    public String getPermition() {
        return permition;
    }

    public void setPermition(String permition) {
        this.permition = permition;
    }



    @OneToMany(mappedBy = "user")
   // @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    public Set<RequestsEntity> getRequests() {
        return requests;
    }

    public void setRequests(Set<RequestsEntity> requests) {
        this.requests = requests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (balance != that.balance) return false;
        if (visitcount != that.visitcount) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (lastseen != null ? !lastseen.equals(that.lastseen) : that.lastseen != null) return false;
        if (permition != null ? !permition.equals(that.permition) : that.permition != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + balance;
        result = 31 * result + visitcount;
        result = 31 * result + (lastseen != null ? lastseen.hashCode() : 0);
        result = 31 * result + (permition != null ? permition.hashCode() : 0);
        return result;
    }
}
