package CS3250;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

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
    public void createEntry(String ID, PO p) {
        String statement = "INSERT INTO PO(userID,total,date) VALUES('" + p.getUserID() + "', '" + p.getTotal()
                + "' , '" + p.getDate() + "');";
        String statement2 = "GET * FROM PO WHERE userID = '" + p.getUserID() + "' AND date = '" + p.getDate() + "';";
        try {
            st.execute(statement);
            rs = st.executeQuery(statement2);
            p.setID(rs.getInt("ID"));
            savePoItems(p.items, p);

        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    private void savePoItems(List<POItem> l, PO pp) {
        SQLPoItem poinit = new SQLPoItem();
        poinit.InitializeDatabase(connectionString);
        POItem pi = new POItem();
        for (int j = 0; j < l.size(); j++) {
            pi.setItemID(l.get(j).ItemID);
            pi.setBuyerID(pp.getUserID());
            pi.setPOID(pp.getID());
            pi.setQuantity(l.get(j).Quantity);
            poinit.createEntry(0, pi);

        }
    }

    public DisplayPO GenerateFullPO(int ID){
        UserData u = new UserData();
        u.initializeDatabase(connectionString + " " + username + " " + password);
        DisplayPO disp = new DisplayPO();
        var po = getPo(ID);
        var usr = u.getUser(po.userID);
        var name = usr.getUsername();
        String n = new String(name);
        disp.setName(n);
        disp.setDate(Date.valueOf(po.getDate()));
        disp.setTotal(po.getTotal());
        disp.setLi(po.items);
        return disp;
    }

    
    public List<String> GenerateShortPOs(){
        List<String> arr = new ArrayList<String>();
        String statement2 = "SELECT * FROM PO;";
        UserData u = new UserData();
        u.initializeDatabase(connectionString + " " + username + " " + password);
        String line = "";
        try{
            rs = st.executeQuery(statement2);
            while (rs.next()) {
                line = "";
                var usr =(u.getUser(rs.getInt("userID")));
                line +=  new String(usr.getUsername()) + "*";
                line += rs.getString("date") + "*";
                line += rs.getString("total");
                 
            }             
        }
        catch(Exception e){}
        return arr;
    }

    private PO getPo(int ID) {
        String statement2 = "SELECT * FROM PO WHERE id = '" + ID + "';";
        PO po = new PO();
        try {
            rs = st.executeQuery(statement2);
            rs.next();
            po.setDate(rs.getString("date"));
            po.setID(rs.getInt("ID"));
            po.setTotal(rs.getDouble("total"));
            po.setUserID(rs.getInt("userID"));
            po.setItems(getItems(po));
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return po;
    }

    private List<POItem> getItems(PO po) {
        String statement2 = "SELECT * FROM POItems WHERE POID = '" + po.ID + "';";
        List<POItem> li = new ArrayList<POItem>();
        try {
            var rs = st.executeQuery(statement2);
            while (rs.next()) {
                POItem pi = new POItem();
                pi.setBuyerID(rs.getInt("BuyerID"));
                pi.setID(rs.getInt("ID"));
                pi.setItemID(rs.getInt("ItemID"));
                pi.setPOID(po.ID);
                pi.setQuantity(rs.getInt(("Quantity")));
                li.add(pi);
            }
        } catch (Exception e) {
            System.out.println("error");
        }
        return li;
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
