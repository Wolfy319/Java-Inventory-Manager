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
            init.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");
            db = new Database(init);
        /*
         	Authorized Users:
            Username 	Password
            --------    --------  
            "pineapple" "onpizza"
    		"owner"     "password1234"
    		"employee"  "cs3250"
    		"admin"     "team3"
    		"customer"  "password"
         */
        
                assertNotEquals(db.listEntries(), null);
        }

}
