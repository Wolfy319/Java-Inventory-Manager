package CS3250;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;


public class UserData {

    String connectionString = "";
    String username = "";
    String password = "";
    Connection con;
    Statement st;
    ResultSet rs;
    
    
    public ArrayList<User> getUsers() {
        String statement = "SELECT * FROM Users;";
        String s = "";
        ArrayList<User> arr = new ArrayList<User>();
        try {

            rs = st.executeQuery(statement);
            while(rs.next()){
                    s += rs.getString(1);
                    s += "_" +  rs.getBytes(2);
                    s += "_" +  rs.getBytes(3);
                    arr.add((parseEntry(s)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }

    
    public void initializeDatabase(String filename) {
        parseString(filename);
        try {
            con = (Connection) DriverManager.getConnection(connectionString, username, password);
            st =  (Statement) con.createStatement(); 
            rs = st.executeQuery("SELECT VERSION()");
            if(rs.next()){
                System.out.println("Connected to..." + rs.getString(1) + " Users");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

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

   
    public void createEntry(String ID, User e) {        
        byte[] ubytes = e.getUsername();
        byte[] pbytes = e.getPassword();
        byte[] sbytes = e.getSalt();
        String sql = "INSERT INTO Users(Username,Password,salt) VALUES(?,?,?)";
        try (PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql)) {
                pst.setBytes(1, ubytes);
                pst.setBytes(2, pbytes);
                pst.setBytes(3, sbytes);
                pst.executeUpdate();
        } catch (SQLException ex) {
        }
    }
    // sends username and returns list of passwords.
    public ArrayList<User> getUser(String username) {
        String statement = "SELECT * FROM Users;";
        String s = "";
        ArrayList<User> arr = new ArrayList<User>();
        try {

            rs = st.executeQuery(statement);
            while(rs.next()){
                byte[] c = rs.getBytes("Username");
                byte[] tc = username.getBytes();
                if (Arrays.equals(c, tc)) {
                    s += rs.getString(1);
                    s += "_" +  rs.getBytes(2);
                    s += "_" +  rs.getBytes(3);
                    arr.add((parseEntry(s)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }

    private User parseEntry(String s){
        User e = new User();
        var ar = s.split("_");
        e.setUsername(ar[0].getBytes());
        e.setPassword(ar[1].getBytes());
        e.setSalt(ar[2].getBytes());
        return e;
    }
   
    public void updateUser(String ID, User e) {
        deleteUser(e.getUsername());
        createEntry("holder",e);

    }

  
    public void deleteUser(byte[] id) {
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
