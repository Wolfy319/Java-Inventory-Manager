package CS3250;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javafx.collections.ObservableList;

public class SQLData implements DataInterface {

    String connectionString = "";
    String username = "";
    String password = "";
    Connection con;
    Statement st;
    ResultSet rs;

    @Override
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

    @Override
    public void createEntry(String ID, Entry e) {
        String statement="INSERT INTO DataEntries(productID,supplierID,stockQuantity,WholesaleCost,salePrice) VALUES('" + e.getProductID() + "', '" + e.getSupplierID() + "' , '"+ e.getStockQuantity() + "' , '" + e.getWholesaleCost() + "' , '" + e.getSalePrice() + "');" ;
        try {
            st.execute(statement);
            
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        
        
    }

    @Override
    public Entry readEntry(String ID) {
        String statement = "SELECT * FROM DataEntries HAVING productID ='"+ ID + "';";
        try {
            rs = st.executeQuery(statement);
            rs.next();
            System.out.println(rs.getString("productID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Entry> getEntries(){
        String statement = "SELECT * FROM DataEntries;";
        String s = "";
        ArrayList<Entry> al = new ArrayList<Entry>();       
        try {
            rs = st.executeQuery(statement);
            
            while(rs.next()){
            s += rs.getString(1);
            s += "_" +  rs.getString(2);
            s += "_" +  rs.getString(3);
            s += "_" +  rs.getString(4);
            s +=  "_" + rs.getString(5);
            al.add(parseEntry(s));
            s = "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
       

    }
    private Entry parseEntry(String s){
        Entry e = new Entry();
        var ar = s.split("_");
        e.setProductID(ar[0]);
        e.setSupplierID(ar[1]);
        e.setStockQuantity(Integer.parseInt(ar[2]));
        e.setWholesaleCost(Double.parseDouble(ar[3]));
        e.setSalePrice(Double.parseDouble(ar[4]));
        return e;
    }

    @Override
    public void updateEntry(String ID, Entry e) {
        deleteEntry(ID);
        createEntry(ID,e);
       

    }

    @Override
    public void deleteEntry(String ID) {
        String statement = "DELETE FROM DataEntries WHERE productID ='"+ ID + "';";
        try {
            st.execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveEntry(Entry e) {
        createEntry(e.getProductID(), e);

    }

    @Override
    public int retSize() {
        // TODO Auto-generated method stub
        return 0;
    }


}