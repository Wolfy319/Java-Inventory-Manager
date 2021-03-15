package CS3250;

public class User {
	private byte[] username;
	private byte[] password;
	private byte[] salt;
	private int ID = 0;
	private String email;
	private String role;

	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public byte[] getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
