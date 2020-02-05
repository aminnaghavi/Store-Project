package ir.store.SOAP.Server;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by B3 on 1/25/2016.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface FindServiceInterface {
    @WebMethod
    public String[] findItems(String username,String password,String ItemModel,String ItemName);
}
