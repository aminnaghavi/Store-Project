package ir;

/**
 * Created by B3 on 1/26/2016.
 */

public class LogElement {
    public boolean filtered;
    public int requestID;
    public String username;
    public String itemModel;
    public String itemName;
    public String date;
    public String time;

    public boolean isFiltered() {
        return filtered;
    }

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getItemModel() {
        return itemModel;
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LogElement(int requestID,
                      String username,
                      String itemModel,
                      String itemName,
                      String date,
                      String time)
    {
        this.requestID=requestID;
        this.username=username;
        this.itemModel=itemModel;
        this.itemName=itemName;
        this.date=date;
        this.time=time;
    }
    public void copy(LogElement logElement){
        this.requestID=logElement.requestID;
        this.username=logElement.username;
        this.itemModel=logElement.itemModel;
        this.itemName=logElement.itemName;
        this.date=logElement.date;
        this.time=logElement.time;
    }
}
