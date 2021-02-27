package UI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class UIDBConnector {
    
    public static Connection getConnection() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");

        return connection;
    }


}
