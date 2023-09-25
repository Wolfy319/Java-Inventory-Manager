package CS3250;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.nio.file.Paths;

public class seedDB {
    static CSVParser parser = new CSVParser();

    public static void seedUsers() throws NoSuchAlgorithmException, InvalidKeySpecException {
        UserData data = new UserData();
        data.initializeDatabase("jdbc:mysql://localhost/inventorydb root root");
        UserAuthenticator.createUser("testing", "test", data);
        UserAuthenticator.createUser("pineapple", "onpizza", data);
        UserAuthenticator.createUser("user1", "userpass", data);

        data.getUsers();
    }

    public static void seedOrders() {
        String file = "archive\\Java-Inventory-Manager\\CS3250\\src\\main\\java\\CS3250\\pos.csv";
        SQLPo entry = new SQLPo();
        entry.initializeDatabase("jdbc:mysql://localhost/testdb root root");
        SQLData data = new SQLData();
        parser.readOrdersCSV(file, entry, data);
    }

    public static void seedProducts() {
        SQLData data = new SQLData();
        data.initializeDatabase("jdbc:mysql://localhost/testdb root root");
        String file = "archive\\Java-Inventory-Manager\\CS3250\\src\\main\\java\\CS3250\\products.csv";

        parser.readProductsCSV(file, data);
    }

    // public static void main(String[] args) {
    //     try {
    //         seedProducts();
    //         seedOrders();
    //     } catch(Exception e) {
    //         e.printStackTrace();
    //     }
    // }
}
