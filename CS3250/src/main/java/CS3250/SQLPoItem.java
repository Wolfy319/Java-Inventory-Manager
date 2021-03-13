package CS3250;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class SQLPoItem {

    String connectionString = "";
    String username = "";
    String password = "";
    Connection con;
    Statement st;
    ResultSet rs;
    public void InitializeDatabase(String connection){
        StringParsers s = new StringParsers();
        var a = s.parseConnectionString(connection);
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

    // if you send 0 to this function it gives you all items in table, if you give an id it gives only ones with that POID
    public List<POItem> GetAllGivenPOID(int ID){
        String statement = "";
        if(ID == 0){
            statement = "SELECT * FROM POItems;";
        }
        else{
            statement = "SELECT * FROM POItems WHERE POID =" + ID+ ";";
        }
        List<POItem> arr = new ArrayList<POItem>();
            try {
            st = (Statement) con.createStatement();
            rs = st.executeQuery(statement);
                POItem p = new POItem();
                while(rs.next()){
                    p.setPOID(rs.getInt("POID"));
                    p.setItemID(rs.getInt("ItemID"));
                    p.setBuyerID(rs.getInt("BuyerID"));
                    p.setQuantity(rs.getInt("Quantity"));
                    p.setID(rs.getInt("ID"));
                    arr.add(p);
                }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return arr;
    }
    public POItem getItem(int ID){
        String statement = "SELECT * FROM POItems WHERE ID =" + ID+ ";";
        try {
        st = (Statement) con.createStatement();
        rs = st.executeQuery(statement);
            POItem p = new POItem();
            rs.next();
                p.setPOID(rs.getInt("POID"));
                p.setItemID(rs.getInt("ItemID"));
                p.setBuyerID(rs.getInt("BuyerID"));
                p.setQuantity(rs.getInt("Quantity"));
                p.setID(rs.getInt("ID"));
                return p;
            } catch(SQLException e){e.printStackTrace();}
            return null;
        }

    public void createEntry(int ID, POItem item){
        String sql = "INSERT INTO POItems(POID,ItemID,BuyerID, Quantity) VALUES(?,?,?,?)";
        try (PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql)) {
                pst.setInt(1, item.getPOID());
                pst.setInt(2, item.getItemID());
                pst.setInt(3, item.getBuyerID());
                pst.setInt(4, item.getQuantity());
                pst.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public void deleteItem(int ID){
        String statement = "DELETE FROM POItems WHERE ID ='"+ ID + "';";
        try {
            st.execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}