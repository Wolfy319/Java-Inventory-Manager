package UI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import CS3250.DataMan;
import CS3250.Entry;
import CS3250.ItemsDB;
import CS3250.UserData;

/**
 * Connects to the mySql database
 * @author Kyle Brown
 */
public class UIDBConnector {
    UserData data = new UserData();
    ItemsDB itemsDB = new ItemsDB();
    /**
     * Connects to database
     * @return - Returns mySql connection
     * @throws SQLException - throws if mySql error
     */
    public static Connection getConnection() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false", "team3", "UpdateTrello!1");

        return connection;
    }
    public DataMan<Entry> getItemsConnection() throws SQLException{
        itemsDB.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");
        return itemsDB;
    }


}
