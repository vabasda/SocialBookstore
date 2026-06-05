package MYY803.SocialBookstore.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserKey implements Serializable{

	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	public UserKey() {
	}
	
	public UserKey(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	public Boolean equals(String username, String password) {
		if(this.username == username && this.password == password) {
			return true;
		}
		return false;
	}
}
