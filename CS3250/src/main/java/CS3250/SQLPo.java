package CS3250;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class SQLPo{

    String connectionString = "";
    String username = "";
    String password = "";
    Connection con;
    Statement st;
    ResultSet rs;

    public ArrayList<Entry> getEntries() {
        // TODO Auto-generated method stub
        return null;
    }

    public void initializeDatabase(String filename) {

        StringParsers s = new StringParsers();
        var a = s.parseConnectionString(filename);
        connectionString = a[0];
        username = a[1];
        password = a[2];
        try {
            con = (Connection) DriverManager.getConnection(connectionString, username, password);
            st =  (Statement) con.createStatement();
            rs = st.executeQuery("SELECT VERSION()");
            if(rs.next()){
                System.out.println("Connected to..." + rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
// need to create table for PO's and swap this string
    // public void createEntry(String ID, PO p) {
    //     String statement="INSERT INTO POItems(productID,supplierID,stockQuantity,WholesaleCost,salePrice) VALUES('" + e.getProductID() + "', '" + e.getSupplierID() + "' , '"+ e.getStockQuantity() + "' , '" + e.getWholesaleCost() + "' , '" + e.getSalePrice() + "');" ;
    //     try {
    //         st.execute(statement);
            
    //     } catch (SQLException e1) {
    //         e1.printStackTrace();
    //     }

    // }

    public Entry readEntry(String ID) {
        // TODO Auto-generated method stub
        return null;
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
