package ir.store.DAO;

import ir.store.DAO.Entity.UsersEntity;

import java.util.List;

/**
 * Created by a4 on 12/8/2015.
 */
public interface UsersDAO {
    public List<UsersEntity> getUsers();

    public List<UsersEntity> searchUsers(String username);

    public List<UsersEntity> searchUsers(String username,String name,String lastname);

    public void insertUser(UsersEntity usersEntity);

    public void deleteUser(UsersEntity usersEntity);

    public boolean access(String username,String password);

}
