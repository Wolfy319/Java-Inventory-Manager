package CS3250;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class POTest {
    DataMan init = new MockPODB();
    Database db;
    @Test
    void ConnectionWorks(){
        String connectionString = StringParsers.readConfig(".config");

        init.initializeDatabase(connectionString);
        db = new Database(init);
     
        var sp = init.getEntries();
        assertNotEquals(sp, null);
    }
    
}
