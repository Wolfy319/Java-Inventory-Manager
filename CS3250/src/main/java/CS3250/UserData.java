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

            User currUser = new User();
            rs = st.executeQuery(statement);
            while(rs.next()){
                byte[] c = rs.getBytes("Username");
                    currUser.setUsername(c);
                    currUser.setPassword(rs.getBytes(2));
                    currUser.setSalt(rs.getBytes(3));
                    currUser.setID(rs.getInt("ID"));
                    arr.add(currUser);
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

    public CS3250.User getUser(int id) {
        String statement = "SELECT * FROM Users WHERE ID = '" + id + "';";
        try {
                User currUser = new User();
            rs = st.executeQuery(statement);
            while(rs.next()){
                    currUser.setUsername(rs.getBytes(1));
                    currUser.setPassword(rs.getBytes(2));
                    currUser.setSalt(rs.getBytes(3));
                    currUser.setID(rs.getInt("ID"));
                    return currUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // sends username and returns list of passwords.
   public ArrayList<User> getUser(byte[] username) {
        String statement = "SELECT * FROM Users;";
        String s = "";
        ArrayList<User> arr = new ArrayList<User>();
        try {
                User currUser = new User();
            rs = st.executeQuery(statement);
            while(rs.next()){
                byte[] c = rs.getBytes("Username");
                byte[] tc = username;
                if (Arrays.equals(c, tc)) {
                    currUser.setUsername(username);
                    currUser.setPassword(rs.getBytes(2));
                    currUser.setSalt(rs.getBytes(3));
                    currUser.setID(rs.getInt("ID"));
                    arr.add(currUser);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }
   
    public void updateUser(int ID, User e) {
        deleteUser(e.getID());
        createEntry("holder",e);

    }

  
    public void deleteUser(int id) {
        String statement = "DELETE FROM Users WHERE ID ='"+ id + "';";
        try {
            st.execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   

        public void saveEntry(User e) {
            String newEntry = String.valueOf(e.getID());
            createEntry(newEntry, e);
        }
    

    public int retSize() {
        String statement = "SELECT COUNT(*) FROM Users;";
        try {
            rs = st.executeQuery(statement);
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        

    }
    
}
