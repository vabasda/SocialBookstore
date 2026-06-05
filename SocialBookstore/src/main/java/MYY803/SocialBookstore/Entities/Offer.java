package MYY803.SocialBookstore.Entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="offers")
public class Offer {

	
	@EmbeddedId
	private OfferKey offerKey;
	
	
	public Offer() {
	}
	
	public Offer(User user,Book book){
		offerKey = new OfferKey(user,book);
	}
	
	public User getUser() {
		return offerKey.getUser();
	}
	
	public String getTitle() {
		return offerKey.getTitle();
	}
	
	public String getUsername() {
		return offerKey.getUsername();
	}
	
}
