package MYY803.SocialBookstore.Entities;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="favourableauthors")
public class FavourableAuthor {
	
	@EmbeddedId
	private FavourableAuthorKey authKey;
	
	public FavourableAuthor() {
		// TODO Auto-generated constructor stub
	}
	
	public FavourableAuthor(User user, String author){
		authKey = new FavourableAuthorKey(user,author);
	}
	
	public String getAuthor() {
		return this.authKey.getAuthor();
	}
}
