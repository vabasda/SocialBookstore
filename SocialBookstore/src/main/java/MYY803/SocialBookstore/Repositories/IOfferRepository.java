package MYY803.SocialBookstore.Repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import MYY803.SocialBookstore.Entities.Offer;
import MYY803.SocialBookstore.Entities.OfferKey;
import MYY803.SocialBookstore.Entities.User;
import jakarta.transaction.Transactional;

@Repository
public interface IOfferRepository extends JpaRepository<Offer,OfferKey>{

	List<Offer> findByOfferKeyUser(User user);

	List<Offer> findByOfferKeyUserUsername(String username);
	
	ArrayList<Offer> findByOfferKeyBookTitle(String title);
	
	 @Transactional
	 Offer findByOfferKeyUserUsernameAndOfferKeyBookTitle(String username, String title);
	 
	 @Transactional
	  void deleteByOfferKeyUserUsernameAndOfferKeyBookTitle(String username, String title);

	
}