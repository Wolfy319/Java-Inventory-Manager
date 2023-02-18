package UI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connects to the mySql database
 * @author Kyle Brown
 */
public class UIDBConnector {
    /**
     * Connects to database
     * @return - Returns mySql connection
     * @throws SQLException - throws if mySql error
     */
    public static Connection getConnection() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/testDB", "root", "testconnection123!");

        return connection;
    }


}
