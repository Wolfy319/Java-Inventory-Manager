package CS3250;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javafx.collections.ObservableList;

/**
 * Class to connect to and edit a MySQL database containing various product entries
 */
public class SQLData implements DataInterface {

    String connectionString = "";
    String username = "";
    String password = "";
    Connection con;
    Statement st;
    ResultSet rs;

    
    /** 
     * Connects to Database using a connection string and confirms connection
     * @param filename - String containing database connection, username and password
     */
    @Override
    public void initializeDatabase(String filename) {
        parseString(filename);
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


    
    /** 
     * Parses a string containing the database connection, username and password
     * @param s - String to be parsed
     */
    private void parseString(String s){
        s+=" ";
        String buffer = "";
        String[] information = new String[3];
        int place = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' '){
                information[place] = buffer;
                buffer = "";
                place++;
            }
            else{
                buffer+= s.charAt(i);
            }
        }
        connectionString = information[0];
        username = information[1];
        password = information[2];
    }

    
    /** 
     * @param ID
     * @param e
     */
    @Override
    public void createEntry(String ID, Entry e) {
        String statement="INSERT INTO DataEntries(productID,supplierID,stockQuantity,WholesaleCost,salePrice) VALUES('" + e.getProductID() + "', '" + e.getSupplierID() + "' , '"+ e.getStockQuantity() + "' , '" + e.getWholesaleCost() + "' , '" + e.getSalePrice() + "');" ;
        try {
            st.execute(statement);
            
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        
        
    }

    
    /** 
     * @param ID
     * @return Entry
     */
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
    
    /** 
     * Pulls all entries from DB's product table and returns them as a list
     * @return ArrayList<Entry> - List containing all entries from database
     */
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
    
    /** 
     * @param s
     * @return Entry
     */
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

    
    /** 
     * @param ID
     * @param e
     */
    @Override
    public void updateEntry(String ID, Entry e) {
        deleteEntry(ID);
        createEntry(ID,e);
       

    }

    
    /** 
     * @param ID
     */
    @Override
    public void deleteEntry(String ID) {
        String statement = "DELETE FROM DataEntries WHERE productID ='"+ ID + "';";
        try {
            st.execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    /** 
     * @param e
     */
    @Override
    public void saveEntry(Entry e) {
        createEntry(e.getProductID(), e);

    }

    
    /** 
     * @return int
     */
    @Override
    public int retSize() {
        int rsCount = 0;
        try{
        while(rs.next())
        {
            rsCount = rsCount + 1;
        }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rsCount;
    }



}