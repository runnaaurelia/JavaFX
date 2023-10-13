package modelData;

public class User {
	public static User us;
	//Variabel us dapat diakses dan dimodifikasi dari mana saja dalam program, membuatnya berguna untuk menyimpan informasi yang perlu dibagikan ke beberapa kelas atau metode.
	
	private Integer userId;
	private String username;
	private String email;
	private String password;
	private String role;
	
	public User(Integer userId, String username, String email, String password, String role) {
		super();
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username; 
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
