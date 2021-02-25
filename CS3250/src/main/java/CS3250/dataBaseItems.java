package CS3250;

public class dataBaseItems {

    String id;
    int quantity;
    float cost,sale;
    String sid;

    public dataBaseItems(String id, int quantity, float cost, float sale, String sid) {
        this.id = id;
        this.quantity = quantity;
        this.cost = cost;
        this.sale = sale;
        this.sid = sid;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getCost() {
        return cost;
    }

    public float getSale() {
        return sale;
    }

    public String getSid() {
        return sid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setSale(float sale) {
        this.sale = sale;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }


}
