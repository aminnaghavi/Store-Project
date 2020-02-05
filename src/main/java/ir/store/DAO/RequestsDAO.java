package ir.store.DAO;

import ir.store.DAO.Entity.RequestsEntity;

import java.util.List;

/**
 * Created by B3 on 1/22/2016.
 */
public interface RequestsDAO {
    public List<RequestsEntity> getRequests();
    public List<RequestsEntity> searchRequestsByID(int requestID);
    public List<RequestsEntity> searchRequests(String itemModel,String itemName,String status);
    public List<RequestsEntity> searchRequests(String username,String itemModel,String itemName,String status);
    //public List<RequestsEntity> searchRequests(String username,int item,String status);
    public List<RequestsEntity> searchRequests(String username);
    //public List<RequestsEntity> searchRequests(int item,String status);
    public void insertRequest(RequestsEntity requestsEntity);
    public void deleteRequest(RequestsEntity requestsEntity);

}
