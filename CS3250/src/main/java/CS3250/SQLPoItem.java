package CS3250;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
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

    public void GetAllGivenPOID(int ID){
        //
    }
}
