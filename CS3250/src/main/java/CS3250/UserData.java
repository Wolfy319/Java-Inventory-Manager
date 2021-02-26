package CS3250;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class UserData {

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
        String statement="INSERT INTO Users(username,password,salt) VALUES('" + e.getUsername() + "', '" + e.getPassword() + "' , '"+ e.getSalt() + "');" ;
        try {
            st.execute(statement);
            
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

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
