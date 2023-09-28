package CS3250;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.Statement;

import UI.observablePO;

public class PODB implements DataMan<observablePO>{
    SQLData inventory = new SQLData();
    String connectionString = "";
    String username = "";
    String password = "";
    Connection con;
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
            inventory.con = con;
            st = (Statement) con.createStatement();
            inventory.st = st;
             rs = st.executeQuery("SELECT VERSION()");
            inventory.rs = this.rs;
            if (rs.next()) {
                System.out.println("Connected to..." + rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // need to create table for PO's and swap this string
    public void createEntry(String ID, observablePO p) {
        Entry inventoryItem = inventory.readEntry(p.getProductID());
        if(poExists(p.getProductID(), Integer.parseInt(p.getQuantity()), p.getDate(), p.getEmail(), p.getCustomerLocation())) {
            System.out.println("Order already exists");
            return;
        }
        if(inventoryItem == null) {
            System.out.println("Ordered item " + p.getProductID() + " doesn't exist!");
            return;
        }
        else {
            if(inventoryItem.getStockQuantity() < Integer.parseInt(p.getQuantity())) {
                System.out.println("Order quantity exceeds quantity in inventory!");
                return;
            }
            else {
                int currentQuantity = inventoryItem.getStockQuantity();
                inventoryItem.setStockQuantity(currentQuantity - Integer.parseInt(p.getQuantity()));
                inventory.updateEntry(p.getProductID(), inventoryItem);
            }
        }
        String statement = "INSERT INTO PO(productID,quantity,date,email,custLoc) VALUES('" + p.getProductID() + "', '" + p.getQuantity()
            + "' , '" + p.getDate() + "' , '" + p.getEmail() + "' , '" + p.getCustomerLocation()  +"');";
        String statement2 = "GET * FROM PO WHERE productID = '" + p.getProductID() + "' AND date = '" + p.getDate() + "';";
        try {
            st.execute(statement);
            // rs = st.executeQuery(statement2);
            // p.setID(rs.getString("ID"));

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    
    public List<UI.observablePO> getEntries(){
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

    public observablePO readEntry(String ID) {
        String statement2 = "SELECT * FROM PO WHERE id = '" + ID + "';";
        observablePO po = new observablePO();
        try {
            rs = st.executeQuery(statement2);
            rs.next();
            po.setDate(rs.getString("date"));
            po.setID(rs.getString("ID"));
            po.setProductID(rs.getString("productID"));
            po.setCustomerLocation((rs.getString("custLoc")));
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return po;
    }

    public boolean poExists(String PID, int quantity, String date, String email, String location) {
        String statement2 = "SELECT * FROM PO WHERE productID = '" + PID + "' AND quantity = '" + quantity + "' AND date = '" + date + "' AND email = '" + email + "' AND custLoc = '" + location + "';";       
        try {
            rs = st.executeQuery(statement2);
            return rs.next();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return false;
    }

    public void updateEntry(String ID, observablePO e) {
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

    public Connection getConnection() {
        return con;
    }

}


