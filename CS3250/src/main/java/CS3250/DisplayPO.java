package CS3250;

import java.sql.Date;
import java.util.List;

public class DisplayPO {
    String name = "";
    Date date;
    List<POItem> li = null;
    double total = 0.0;

    public void setDate(Date date) {
        this.date = date;
    }
    public void setLi(List<POItem> li) {
        this.li = li;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public Date getDate() {
        return date;
    }
    public List<POItem> getLi() {
        return li;
    }
    public String getName() {
        return name;
    }
    public double getTotal() {
        return total;
    }
    
    
}
