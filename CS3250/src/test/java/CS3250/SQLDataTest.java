package CS3250;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import CS3250.DataInterface;
import CS3250.Entry;
import CS3250.SQLData;

public class SQLDataTest {
    DataMan init = new MockEntriesDB();
    Database db;
    @Test
    void ConnectionWorks(){
        String connectionString = StringParsers.readConfig(".config");

        init.initializeDatabase(connectionString);
        db = new Database(init);
        
        assertNotEquals(db.listEntries(), null);
    }
    


}
