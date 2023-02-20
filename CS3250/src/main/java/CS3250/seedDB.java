package CS3250;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class seedDB {
    static CSVParser parser = new CSVParser();

    public static void seedUsers() throws NoSuchAlgorithmException, InvalidKeySpecException {
        UserData data = new UserData();
        data.initializeDatabase("jdbc:mysql://localhost/testdb root testconnection123!");
        UserAuthenticator.createUser("admin", "123", data);
        UserAuthenticator.createUser("testing", "test", data);
        UserAuthenticator.createUser("pineapple", "onpizza", data);
        UserAuthenticator.createUser("user1", "userpass", data);
    }
    public static void seedOrders() {
        String file = "";
        SQLPo entry = new SQLPo();
        entry.initializeDatabase("jdbc:mysql://localhost/testdb root testconnection123!");
        SQLData data = new SQLData();
        parser.readOrdersCSV(file, entry, data);
    }

    public static void seedProducts() {
        SQLData data = new SQLData();
        data.initializeDatabase("jdbc:mysql://localhost/testdb root testconnection123!");
        String file = "";

        parser.readProductsCSV(file, data);
    }
}
