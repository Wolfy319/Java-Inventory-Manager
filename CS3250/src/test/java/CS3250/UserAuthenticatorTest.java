package CS3250;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;


class UserAuthenticatorTest {
	UserAuthenticator auth = new UserAuthenticator();
	byte[] salt;
	byte[] hashedUser1;
	byte[] hashedUser2;
	byte[] hashedPass1;
	byte[] hashedPass2;
	@Test 
	public void GetEncryptedWorks() throws InvalidKeySpecException, NoSuchAlgorithmException {
		// Make a bunch of hashes to make sure that the hashing works
		for(int i = 0; i < 30; i++) {
			salt = auth.generateSalt();
			hashedUser1 = auth.getEncryptedUsername("username");
			hashedUser2 = auth.getEncryptedUsername("username");
			hashedPass1 = auth.getEncryptedPassword("password", salt);
			hashedPass2 = auth.getEncryptedPassword("password", salt);
			
			// It doesn't matter what the hashed password is as long as it's different
			// from the original and is repeatable
			assertNotEquals(hashedUser1.toString(), "username");
			assertNotEquals(hashedPass1.toString(), "password");
			assertEquals(Arrays.equals(hashedUser1, hashedUser2), true);
			assertEquals(Arrays.equals(hashedPass1, hashedPass2), true);
		}
	}
	
	@Test
	public void CreateUserWorks() throws InvalidKeySpecException, NoSuchAlgorithmException {
		User testUser;
		byte[] hashedUsername;
		byte[] hashedPassword;
		
		// Make sure that create user makes the same user everytime
		for(int i = 0; i < 30; i++) {
			testUser = auth.createUser("test", "admin");
			assertNotEquals(testUser.getPassword().toString(), "admin");
			assertNotEquals(testUser.getUsername().toString(), "test");
			
			hashedUsername = auth.getEncryptedUsername("test");
			hashedPassword = auth.getEncryptedPassword("admin", testUser.getSalt());
			
			assertEquals(Arrays.equals(testUser.getUsername(), hashedUsername), true);
			assertEquals(Arrays.equals(testUser.getPassword(), hashedPassword), true);
		}
		
	}
	
	@Test 
	public void GetRandomSaltWorks() throws NoSuchAlgorithmException {
		byte[] salt1;
		byte[] salt2;
		
		// Make sure that the salts are actually random
		for(int i = 0; i < 1000; i++) {
			salt1 = auth.generateSalt();
			salt2 = auth.generateSalt();
			
			assertNotEquals(Arrays.equals(salt1, salt2), true);
		}
	}

}
