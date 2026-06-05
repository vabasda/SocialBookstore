package MYY803.SocialBookstore.Repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import MYY803.SocialBookstore.Entities.FavourableCategory;

@Repository
public interface IFavourableCategoryRepository extends JpaRepository<FavourableCategory,String>{
	
	 ArrayList<FavourableCategory> findByCatKeyUserUsername(String username);
	
}
