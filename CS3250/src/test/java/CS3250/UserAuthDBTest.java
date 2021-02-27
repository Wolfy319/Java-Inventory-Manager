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
            UserAuthenticator.createUser("pineapple", "onpizza", data);
    		UserAuthenticator.createUser("owner", "password1234", data);
    		UserAuthenticator.createUser("employee", "cs3250", data);
    		UserAuthenticator.createUser("admin", "team3", data);
    		UserAuthenticator.createUser("customer", "password", data);
         */
        assertEquals(UserAuthenticator.authenticate("pineapple", "onpizza", data), true);
        assertNotEquals(UserAuthenticator.authenticate("pineapple", "fakepassword", data), true);
        assertNotEquals(UserAuthenticator.authenticate("notarealuser", "onpizza", data), true);
        assertNotEquals(UserAuthenticator.authenticate("notarealuser", "fakepassword", data), true);
	}

}
