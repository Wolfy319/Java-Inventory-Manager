package UI;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import CS3250.StringParsers;

/**
 * Connects to the mySql database
 * @author Kyle Brown
 */
public class UIDBConnector {
    /**
     * Connects to database
     * @return - Returns mySql connection
     * @throws SQLException - throws if mySql error
     * @throws IOException
     */
    public static Connection getConnection() throws SQLException, IOException{
        String connectionString = StringParsers.readConfig(".config");
        StringParsers p = new StringParsers();
        String[] dbConnection = p.parseConnectionString(connectionString);
        Connection connection = DriverManager.getConnection(dbConnection[0], dbConnection[1], dbConnection[2]);
        return connection;
    }


}
