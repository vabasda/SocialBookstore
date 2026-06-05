package MYY803.SocialBookstore.Repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import MYY803.SocialBookstore.Entities.Request;
import jakarta.transaction.Transactional;

@Repository
public interface IRequestRepository extends JpaRepository<Request,String>{

	ArrayList<Request> findByRequestKeyOfferOfferKeyUserUsername(String username);
	
	@Transactional
	ArrayList<Request> findByRequestKeyOfferOfferKeyUserUsernameAndRequestKeyOfferOfferKeyBookTitle(String username,String title);
	
	@Transactional
	void deleteByRequestKeyOfferOfferKeyUserUsernameAndRequestKeyOfferOfferKeyBookTitle(String username,String title);
	
}
