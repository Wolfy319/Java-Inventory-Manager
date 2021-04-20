package CS3250;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class POTest {
    DataMan init = new MockPODB();
    Database db;
    @Test
    void ConnectionWorks(){
        init.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");
        db = new Database(init);
     
        var sp = init.getEntries();
        assertNotEquals(sp, null);
    }
    
}
