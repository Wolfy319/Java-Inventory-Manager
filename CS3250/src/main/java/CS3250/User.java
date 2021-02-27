package CS3250;

public class User {
	private byte[] username;
	private byte[] password;
	private byte[] salt;
	
	public byte[] getUsername() {
		return username;
	}
	
	public void setUsername(byte[] username) {
		this.username = username;
	}
	
	public byte[] getPassword() {
		return password;
	}
	
	public void setPassword(byte[] password) {
		this.password = password;
	}
	
	public byte[] getSalt() {
		return salt;
	}
	
		public int getID() {
		return ID;
	}
	
	public void setID(int id){
		ID = id;
	}
	
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	
}
