package MYY803.SocialBookstore.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Embeddable
public class FavourableCategoryKey implements Serializable{

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="username", referencedColumnName="username")
	})
	private User user;

	@Column(name="category")
	private String category;
	
	@Transient
	private String username;

	
	public FavourableCategoryKey() {
	}
	
	public FavourableCategoryKey(User user,String category) {
		this.user = user;
		this.username = user.getUsername();
		this.category = category;
	}
	
	
	
	public String getCategory() {
		return this.category;
	}
	public Boolean equals(String username,String category) {
		if(this.username == username && this.category == category) {
			return true;
		}
		return false;
	}
}
