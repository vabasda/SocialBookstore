package MYY803.SocialBookstore.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Embeddable
public class FavourableAuthorKey implements Serializable{

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="username", referencedColumnName="username")
	})
	private User user;

	@Column(name="author")
	private String author;
	
	@Transient
	private String username;
	
	public FavourableAuthorKey() {
	}
	
	public FavourableAuthorKey(User user,String author) {
		this.user = user;
		this.username = user.getUsername();
		this.author = author;
	}
	
	
	
	public String getAuthor() {
		return this.author;
	}
	public Boolean equals(String username, String author) {
		if(this.username == username && this.author == author) {
			return true;
		}
		return false;
	}
}
