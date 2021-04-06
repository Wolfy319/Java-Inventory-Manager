package CS3250;
public class PO {
    int ID = 0;
    String productID = "";
    int quantity = 0;
    String customerLocation = "";
    String date = "";
    String email = "";

    public String getCustomerLocation() {
        return customerLocation;
    }
    public int getQuantity() {
        return quantity;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getProductID() {
        return productID;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }
    public void setProductID(String ProductID) {
        this.productID = ProductID;
    }
    public void quantity(int q) {
        this.quantity = q ;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public int getID() {
        return ID;
    }
}