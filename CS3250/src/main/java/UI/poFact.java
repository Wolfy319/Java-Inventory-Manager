package UI;

import javafx.beans.property.SimpleStringProperty;

public class poFact {

    public final SimpleStringProperty userID = new SimpleStringProperty();
    public final SimpleStringProperty total = new SimpleStringProperty();
    public final SimpleStringProperty date = new SimpleStringProperty();


    public poFact(String userID, String total, String date){
        this.userID.set(userID);
        this.total.set(total);
        this.date.set(date);
    }

    public final String getUserID(){
        return this.userID.get();
    }

    public final String getTotal(){
        return this.total.get();
    }

    public final String getDate(){
        return this.date.get();
    }

    public final void setUserID(String userID){
        this.userID.set(userID);
    }

    public final void setTotal(String total){
        this.total.set(total);
    }

    public final void setDate(String date){
        this.total.set(date);
    }








    
}
