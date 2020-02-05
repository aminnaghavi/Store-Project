package ir;

/**
 * Created by B3 on 1/22/2016.
 */
public class ItemLink{
    public String outcome;
    public String value;

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ItemLink(String out,String val){
        outcome=out;
        value=val;
    }
}