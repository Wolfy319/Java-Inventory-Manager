package UI;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class dataBaseItems {


    public SimpleStringProperty id = new SimpleStringProperty();
    public SimpleIntegerProperty quantity = new SimpleIntegerProperty();
    public SimpleFloatProperty cost = new SimpleFloatProperty();
    public SimpleFloatProperty sale = new SimpleFloatProperty();
    public SimpleStringProperty sid = new SimpleStringProperty();

   // StringProperty id;
    //IntegerProperty quantity;
    //FloatProperty cost,sale;
    //StringProperty sid;

    public dataBaseItems(String string, int i, float f, float g, String string2) {
        this.id.set(string);
        this.quantity = i;
        this.cost = f;
        this.sale = g;
        this.sid = string2;
    }

    public String getId() {
        return this.id.get();
    }

    public int getQuantity() {
        return this.quantity.get();
    }

    public float getCost() {
        return this.cost.get();
    }

    public float getSale() {
        return this.sale.get();
    }

    public String getSid() {
        return this.sid.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public void setCost(float cost) {
        this.cost.set(cost);
    }

    public void setSale(float sale) {
        this.sale.set(sale);
    }

    public void setSid(String sid) {
        this.sid.set(sid);
    }


}
