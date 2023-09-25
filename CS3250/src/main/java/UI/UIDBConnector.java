package UI;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import CS3250.StringParsers;
import CS3250.DataMan;
import CS3250.Entry;
import CS3250.ItemsDB;
import CS3250.PODB;
import CS3250.UserData;


/**
 * Connects to the mySql database
 * @author Kyle Brown
 */
public class UIDBConnector {
    UserData data = new UserData();
    ItemsDB itemsDB = new ItemsDB();
    PODB podb = new PODB();
    /**
     * Connects to database
     * @return - Returns mySql connection
     * @throws SQLException - throws if mySql error
     * @throws IOException
     */
    public static Connection getConnection() throws SQLException, IOException{
        String connectionString = "jdbc:mysql://localhost/testdb root root";
        StringParsers p = new StringParsers();
        String[] dbConnection = p.parseConnectionString(connectionString);
        Connection connection = DriverManager.getConnection(dbConnection[0], dbConnection[1], dbConnection[2]);
        return connection;
    }
    public ItemsDB getItemsConnection() throws SQLException{
        itemsDB.initializeDatabase("jdbc:mysql://localhost/testdb root root");
        return itemsDB;
    }
    public PODB getPOConnection() throws SQLException{
        podb.initializeDatabase("jdbc:mysql://localhost/testdb root root");
        return podb;
    }


}
