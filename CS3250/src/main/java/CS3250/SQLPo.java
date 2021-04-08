package CS3250;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import UI.observablePO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SQLPo {

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
            st = (Statement) con.createStatement();
            rs = st.executeQuery("SELECT VERSION()");
            if (rs.next()) {
                System.out.println("Connected to..." + rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // need to create table for PO's and swap this string
    public void createEntry(String ID, observablePO p) {
        String statement = "INSERT INTO PO(productID,quantity,date,email,custLoc) VALUES('" + p.getProductID() + "', '" + p.getQuantity()
                + "' , '" + p.getDate() + "' , '" + p.getEmail() + "' , '" + p.getCustomerLocation()  +"');";
        String statement2 = "GET * FROM PO WHERE productID = '" + p.getProductID() + "' AND date = '" + p.getDate() + "';";
        try {
            st.execute(statement);
            rs = st.executeQuery(statement2);
            p.setID(rs.getInt("ID"));

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    
    public List<UI.observablePO> GenerateShortPOs(){
        List<UI.observablePO> arr = new ArrayList<UI.observablePO>();
        String statement2 = "SELECT * FROM PO;";
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
                po.setID(rs.getInt("ID"));
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

}
