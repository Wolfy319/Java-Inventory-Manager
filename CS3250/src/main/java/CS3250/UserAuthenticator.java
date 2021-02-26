package CS3250;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
 
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
 
public class UserAuthenticator {
	public User createUser(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		User newUser = new User();
		
		newUser.setUsername(getEncryptedUsername(username));
		newUser.setSalt(generateSalt());
		newUser.setPassword(getEncryptedPassword(password, newUser.getSalt()));
		return newUser;
	}
 
	public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt)
	   throws NoSuchAlgorithmException, InvalidKeySpecException {
	  // Hash the clear-text password using the same salt that was used for the original hash
	  byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
	 
	  // Authentication succeeds if encrypted password that the user entered is equal to the stored hash
	  return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
	 }
 
	public byte[] getEncryptedUsername(String username) throws InvalidKeySpecException, NoSuchAlgorithmException {
		String algorithm = "PBKDF2WithHmacSHA1"; // PBKDF2 with SHA-1 as the hashing algorithm
		byte[] pepper = {0,0,0,0,0,0,0,0}; // Use the same salt for every username for easy lookup
		
		// Tells key factory what type of key to make
		KeySpec spec = new PBEKeySpec(username.toCharArray(), pepper, 2, 160);
		
		// Instantiate factory with the algorithm to be used
		SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
		
		// Return hashed username
		return f.generateSecret(spec).getEncoded();
	}
	
	public byte[] getEncryptedPassword(String password, byte[] salt)
	throws NoSuchAlgorithmException, InvalidKeySpecException {
	  
		String algorithm = "PBKDF2WithHmacSHA1"; // PBKDF2 with SHA-1 as the hashing algorithm
		int derivedKeyLength = 160; // SHA-1 generates 160 bit hashes, so that's what makes sense here
		int iterations = 5000; // How many times the hash get repeated
		
		// Tells key factory what type of key to make
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
		
		// Instantiate factory with the algorithm to be used
		SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
		
		// Return hashed password
		return f.generateSecret(spec).getEncoded();
	}
 
	public byte[] generateSalt() throws NoSuchAlgorithmException {
		// Apparently important to use SecureRandom instead of just Random
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
 
		// Generate a 8 byte (64 bit) salt
		byte[] salt = new byte[8];
		random.nextBytes(salt);
 
		return salt;
	 }
}