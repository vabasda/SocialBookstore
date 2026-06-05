package MYY803.SocialBookstore.Entities;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {

	@Id
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "name")
	private String fullName;
	
	@Column(name = "age") 
	private String age;
	
	@Column(name = "address")	
	private String address;
	
	@Column(name = "phonenumber")
	private String phoneNumber;



	public User() {
	}
	
    public User(String username, String password) {
    	this.username = username;
    	this.password = password;
    }
	
	public User(String username, String password, String fullName, String age, String address, String phoneNumber) {
		//userKey = new UserKey(username,password);
		this.username = username;
    	this.password = password;
		this.fullName = fullName;
		this.age = age;
		this.address = address;
		this.phoneNumber = phoneNumber;
		
	}
	
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

	
	public String getFullName() {
		return fullName;
	}
	
	public String getAge() {
		return age;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}