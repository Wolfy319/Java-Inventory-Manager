package CS3250;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import UI.dataBaseItems;
import javafx.collections.ObservableList;

/**
 * Class to connect to and edit a MySQL database containing various product entries
 */
public class SQLData implements DataInterface {

    String connectionString = "";
    String username = "";
    String password = "";
    Connection con = null;
    Statement st;
    ResultSet rs;

    
    /** 
     * Connects to Database using a connection string and confirms connection
     * @param filename - String containing database connection, username and password
     */
    @Override
    public void initializeDatabase(String filename) {
        StringParsers s = new StringParsers();
        var a = s.parseConnectionString(filename);
        connectionString = a[0];
        username = a[1];
        password = a[2];
        try {
            con = (Connection) DriverManager.getConnection(connectionString, username, password);
            st =  con.createStatement();
            rs = st.executeQuery("SELECT VERSION()");
            if(rs.next()){
                System.out.println("Connected to..." + rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    /** 
     * Inserts an entry into the database
     * @param ID - Product ID
     * @param e - Entry object to be inserted
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
     * Returns an entry from the database
     * @param ID - Product ID of product to be read
     * @return Entry - Entry to be read
     */
    @Override
    public Entry readEntry(String ID) {
        String statement = "SELECT * FROM DataEntries HAVING productID ='"+ ID + "';";
        String s = "";
        Entry result = new Entry();
        try {
            rs = st.executeQuery(statement);
            if(!rs.next()) {
                return null;
            }
            result = parseEntry(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public List<UI.dataBaseItems> GenerateShortData(){
        List<UI.dataBaseItems> arr = new ArrayList<UI.dataBaseItems>();
        String statement2 = "SELECT * FROM DataEntries;";
        dataBaseItems data;
        try{
            rs = st.executeQuery(statement2);
            while (rs.next()) {
                data = new dataBaseItems(rs.getString("productID"),
                             rs.getString("supplierID"), 
                             (Integer.toString(rs.getInt("stockQuantity"))),
                             Double.toString(rs.getDouble("WholesaleCost")), 
                             Double.toString(rs.getDouble("salePrice")));
                arr.add(data);
            }             
        }
        catch(Exception e){
            System.out.println(e);
        }
        return arr;
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
            Entry e = parseEntry(rs);
            al.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return al;
       

    }
    
    /** 
     * Creates a new entry object from a string
     * @param s - String containing product ID, supplier ID, stock quantity, wholesale cost and sale price of a product
     * @return Entry - Entry object filled with fields from string
     */
    private Entry parseEntry(ResultSet rs){
        Entry e = new Entry();
        try{
            e.setProductID(rs.getString("productID"));
            e.setSupplierID(rs.getString("supplierID"));
            e.setStockQuantity(Integer.parseInt(rs.getString("stockQuantity")));
            e.setWholesaleCost(Double.parseDouble(rs.getString("WholesaleCost")));
            e.setSalePrice(Double.parseDouble(rs.getString("salePrice")));
        }catch(Exception exc) {
        }
        return e;
    }

    
    /** 
     * Updates an existing entry in the database
     * @param ID - Product ID of the item to be updated
     * @param e - Entry containing updated fields
     */
    @Override
    public void updateEntry(String ID, Entry e) {
        deleteEntry(ID);
        createEntry(ID,e);
       

    }

    
    /** 
     * Deletes an entry from the database
     * @param ID - Product ID of item to be deleted
     */
    @Override
    public void deleteEntry(String ID) {
        String statement = "DELETE FROM DataEntries WHERE productID ='"+ ID + "';";
        System.out.println(statement);
        try {
            st.execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Saves an entry into the database
     * @param e - Entry to be saved
     */
    @Override
    public void saveEntry(Entry e) {
        createEntry(e.getProductID(), e);

    }

    
    /** 
     * Returns the total number of entries inside of the database
     * @return int - Number of entries in database
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

    public Connection getConnection() {
        return con;
    }

}
