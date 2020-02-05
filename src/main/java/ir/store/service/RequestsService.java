package ir.store.service;

import ir.store.DAO.Entity.RequestsEntity;
import ir.store.DAO.RequestsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by B3 on 1/22/2016.
 */
@Service("requestsService")
@Transactional
public class RequestsService {
    @Autowired
    RequestsDAO requestsDAO;
    public List<RequestsEntity> getRequests(){return requestsDAO.getRequests();}
    public List<RequestsEntity> searchRequestsByID(int requestID){return requestsDAO.searchRequestsByID(requestID);}
    public List<RequestsEntity> searchRequests(String username,String itemModel,String itemName,String status){return requestsDAO.searchRequests(username,itemModel,itemName,status);}
    public List<RequestsEntity> searchRequests(String username){return requestsDAO.searchRequests(username);}
    public void insertRequest(RequestsEntity requestsEntity){requestsDAO.insertRequest(requestsEntity);}
    public void deleteRequest(RequestsEntity requestsEntity){requestsDAO.deleteRequest(requestsEntity);}
    public List<RequestsEntity> searchRequests(String itemModel,String itemName,String status){return requestsDAO.searchRequests(itemModel,itemName,status);}
}
