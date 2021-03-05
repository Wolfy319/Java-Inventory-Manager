package CS3250;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;

class UserAuthDBTest {
	static UserData data = new UserData();
	@Test
	void test() throws NoSuchAlgorithmException, InvalidKeySpecException {
        data.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");
    	
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
        
        assertEquals(UserAuthenticator.authenticate("pineapple", "onpizza", data), true);
        assertNotEquals(UserAuthenticator.authenticate("pineapple", "fakepassword", data), true);
        assertNotEquals(UserAuthenticator.authenticate("notarealuser", "onpizza", data), true);
        assertNotEquals(UserAuthenticator.authenticate("notarealuser", "fakepassword", data), true);
	}

}
