package CS3250;

public class POItem {
    int buyerID = 0;
    int POID = 0;
    int ID = 0;
    int ItemID = 0;
    int Quantity = 0;
    String BuyerName = "";
    String ProductName = "";

    public void SaveItem(){


    }

    public String getBuyerName() {
        return BuyerName;
    }
    public String getProductName() {
        return ProductName;
    }

    public int getBuyerID() {
        return buyerID;
    }
    public int getID() {
        return ID;
    }
    public int getItemID() {
        return ItemID;
    }
    public int getPOID() {
        return POID;
    }
    public int getQuantity() {
        return Quantity;
    }
    public void setBuyerID(int buyerID) {
        this.buyerID = buyerID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public void setItemID(int itemID) {
        ItemID = itemID;
    }
    public void setPOID(int pOID) {
        POID = pOID;
    }
    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
