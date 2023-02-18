package CS3250;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import UI.observablePO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SQLPo {
    SQLData inventory = new SQLData();
    String connectionString = "";
    String username = "";
    String password = "";
    Connection con = null;
    Statement st;
    ResultSet rs;

    public void initializeDatabase(String filename) {
        StringParsers s = new StringParsers();
        var a = s.parseConnectionString(filename);
        connectionString = a[0];
        username = a[1];
        password = a[2];

        try {
            con = (Connection) DriverManager.getConnection(connectionString, username, password);
            inventory.con = this.con;
            st = con.createStatement();
            inventory.st = this.st;
            rs = st.executeQuery("SELECT VERSION()");
            inventory.rs = this.rs;
            if (rs.next()) {
                System.out.println("Connected to..." + rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setConnection(Connection loaded) {
        try {
            con = inventory.con = loaded;
            st = inventory.st = con.createStatement();
            rs = inventory.rs = st.executeQuery("SELECT VERSION()");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public SQLData getData(){
        return inventory;
    }

    // need to create table for PO's and swap this string
    public void createEntry(String ID, observablePO p) {
        Entry inventoryItem = inventory.readEntry(p.getProductID());
        if(poExists(Integer.valueOf(p.getProductID()), Integer.valueOf(p.getQuantity()), p.getDate(), p.getEmail(), p.getCustomerLocation())) {
            System.out.println("Order already exists");
            return;
        }
        if(inventoryItem == null) {
            System.out.println("Ordered item " + p.getProductID() + " doesn't exist!");
            return;
        }
        else {
            if(inventoryItem.getStockQuantity() < Integer.valueOf(p.getQuantity())) {
                System.out.println("Order quantity exceeds quantity in inventory!");
                return;
            }
            else {
                int currentQuantity = inventoryItem.getStockQuantity();
                inventoryItem.setStockQuantity(currentQuantity - Integer.valueOf(p.getQuantity()));
                inventory.updateEntry(p.getProductID(), inventoryItem);
            }
        }
        String statement = "INSERT INTO PO(productID,quantity,date,email,custLoc) VALUES('" + p.getProductID() + "', '" + p.getQuantity()
            + "' , '" + p.getDate() + "' , '" + p.getEmail() + "' , '" + p.getCustomerLocation()  +"');";
        String statement2 = "GET * FROM PO WHERE productID = '" + p.getProductID() + "' AND date = '" + p.getDate() + "';";
        try {
            st.execute(statement);
            rs = st.executeQuery(statement2);
            p.setID(rs.getString("ID"));

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    
    public List<UI.observablePO> GenerateShortPOs(){
        List<UI.observablePO> arr = new ArrayList<UI.observablePO>();
        String statement2 = "SELECT * FROM PO;";
        UI.observablePO po = new UI.observablePO();
        try{
            rs = st.executeQuery(statement2);
            while (rs.next()) {
                po = new UI.observablePO();
                po.setProductID(rs.getString("productID"));
                po.setEmail( rs.getString("email"));
                po.setDate(rs.getString("date"));
                po.setID(rs.getString("ID"));
                po.setCustomerLocation(rs.getString("custLoc"));
                po.quantity(rs.getString("quantity"));
                arr.add(po);
            }             
        }
        catch(Exception e){
            System.out.println(e);
        }
        return arr;
    }

    public PO getPo(int ID) {
        String statement2 = "SELECT * FROM PO WHERE id = '" + ID + "';";
        PO po = new PO();
        try {
            rs = st.executeQuery(statement2);
            rs.next();
            po.setDate(rs.getString("date"));
            po.setID(rs.getInt("ID"));
            po.setProductID(rs.getString("productID"));
            po.customerLocation = rs.getString("custLoc");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return po;
    }

    public boolean poExists(int PID, int quantity, String date, String email, String location) {
        String statement2 = "SELECT * FROM PO WHERE productID = '" + PID + "' AND quantity = '" + quantity + "' AND date = '" + date + "' AND email = '" + email + "' AND custLoc = '" + location + "';";       
        try {
            rs = st.executeQuery(statement2);
            return rs.next();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return false;
    }

    public void updateInventory(String productID, int orderedQuantity) {
		
	}

    public void updateEntry(String ID, Entry e) {
        // TODO Auto-generated method stub

    }

    public void deleteEntry(String id) {
        // TODO Auto-generated method stub

    }

    public void saveEntry(Entry e) {
        // TODO Auto-generated method stub

    }

    public int retSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    public List<UI.observablePO> GenerateWeeklyPO(String statement2){
        List<UI.observablePO> arr = new ArrayList<UI.observablePO>();
        UserData u = new UserData();
        u.initializeDatabase(connectionString + " " + username + " " + password);
        UI.observablePO po = new UI.observablePO();
        try{
            rs = st.executeQuery(statement2);
            while (rs.next()) {
                po = new UI.observablePO();
                po.setProductID(rs.getString("productID"));
                po.setEmail( rs.getString("email"));
                po.setDate(rs.getString("date"));
                po.setID(rs.getString("ID"));
                po.setCustomerLocation(rs.getString("custLoc"));
                po.quantity(rs.getString("quantity"));
                arr.add(po);
            }             
        }
        catch(Exception e){
            System.out.println(e);
        }
        return arr;
    }

    public Connection getConnection() {
        return con;
    }

}
