package CS3250;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;

class UserAuthDBTest {
        DataMan init = new MockUserDB();
        Database db;
        @Test
        void ConnectionWorks(){
            String connectionString = StringParsers.readConfig(".config");

            init.initializeDatabase(connectionString);
            db = new Database(init);
        
                assertNotEquals(db.listEntries(), null);
        }

}
