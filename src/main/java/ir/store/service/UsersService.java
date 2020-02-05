package ir.store.service;

import ir.store.DAO.Entity.RequestsEntity;
import ir.store.DAO.Entity.UsersEntity;
import ir.store.DAO.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by a4 on 12/8/2015.
 */
@Service("usersService")
@Transactional
public class UsersService {
    @Autowired
    UsersDAO usersDAO;
    public void insert(UsersEntity usersEntity){usersDAO.insertUser(usersEntity);}
    public boolean access(String username,String password){return usersDAO.access(username,password);}
    public List<UsersEntity> searchUsers(String username){return usersDAO.searchUsers(username);}
    public List<UsersEntity> getUsers(){return usersDAO.getUsers();}
    public void deleteUser(UsersEntity usersEntity){usersDAO.deleteUser(usersEntity);}
    public List<UsersEntity> searchUsers(String username,String name,String lastname){return usersDAO.searchUsers(username,name,lastname);}

}
