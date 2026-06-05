package MYY803.SocialBookstore.Entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

@Embeddable
public class OfferKey implements Serializable{

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="username", referencedColumnName="username")
	})
	private User user;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="title", referencedColumnName="title")
	})
	private Book book;
	
	
	public OfferKey() {
	}
	
	public OfferKey(User user,Book book) {
		this.user = user;
		this.book = book;
	}

	public String getUsername() {
		return this.user.getUsername();
	}

	public String getTitle() {
		return this.book.getTitle();
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	
}
