package MYY803.SocialBookstore.Entities;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="favourablecategories")
public class FavourableCategory {
	
	@EmbeddedId
	private FavourableCategoryKey catKey;
	
	public FavourableCategory() {
	}
	
	public FavourableCategory(User user, String category){
		catKey = new FavourableCategoryKey(user,category);
	}

	public String getCategory() {
		return catKey.getCategory();
	}
	
}
