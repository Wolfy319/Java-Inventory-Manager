package CS3250;

/**
 * Class used to hold important user information like username, password and email in encrypted form
 */
public class User {
	private byte[] username;
	private byte[] password;
	private byte[] salt;
	private int ID = 0;
	
	
	/** 
	 * Returns encrypted username
	 * @return byte[] - Byte array version of hashed username
	 */
	public byte[] getUsername() {
		return username;
	}
	
	
	/** 
	 * Sets hashed username
	 * @param username - Byte array version of hashed username
	 */
	public void setUsername(byte[] username) {
		this.username = username;
	}
	
	
	/** 
	 * Returns hashed password
	 * @return byte[] - Byte array version of hashed password
	 */
	public byte[] getPassword() {
		return password;
	}
	
	
	/** 
	 * Sets hashed password
	 * @param password - Byte array version of hashed password
	 */
	public void setPassword(byte[] password) {
		this.password = password;
	}
	
	
	/** 
	 * Returns randomized salt used to hash the password
	 * @return byte[] - Randomized byte array
	 */
	public byte[] getSalt() {
		return salt;
	}
	
		
	/** 
	 * Returns ID of user, used to index on a database
	 * @return int - User ID
	 */
	public int getID() {
		return ID;
	}
	
	
	/** 
	 * Sets user ID, used to index on a database
	 * @param id - User ID
	 */
	public void setID(int id){
		ID = id;
	}
	
	
	/** 
	 * Sets the randomized salt used to hash the password
	 * @param salt - Randomized byte array
	 */
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	
}
