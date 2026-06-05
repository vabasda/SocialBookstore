package MYY803.SocialBookstore.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="notifications")
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "message")
	private String message;
	
	
	public Notification() {
		
	}
	
	public Notification(String username, String message) {
		this.username = username;
		this.message = message;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getMessage() {
		return this.message;
	}
}
