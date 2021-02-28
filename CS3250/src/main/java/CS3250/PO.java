package CS3250;

import java.util.ArrayList;
import java.util.List;

public class PO {
    int ID = 0;
    int userID = 0;
    List<Integer> itemPOs = new ArrayList<Integer>();
    String Username = "";

    public int getUserID() {
        return userID;
    }
    public String getUsername() {
        return Username;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public void setUsername(String username) {
        Username = username;
    }
    public void setItemPOs(List<Integer> itemPOs) {
        this.itemPOs = itemPOs;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public int getID() {
        return ID;
    }


    
}
