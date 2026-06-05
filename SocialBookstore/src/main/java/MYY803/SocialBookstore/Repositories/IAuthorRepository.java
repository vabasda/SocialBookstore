package MYY803.SocialBookstore.Repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import MYY803.SocialBookstore.Entities.FavourableAuthor;

public interface IAuthorRepository  extends JpaRepository<FavourableAuthor,String>{

	ArrayList<FavourableAuthor> findByAuthKeyUserUsername(String username);

}
