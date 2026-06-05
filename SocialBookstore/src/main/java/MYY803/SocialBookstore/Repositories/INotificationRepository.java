package MYY803.SocialBookstore.Repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import MYY803.SocialBookstore.Entities.Notification;
import jakarta.transaction.Transactional;

@Repository
public interface INotificationRepository extends JpaRepository<Notification,String>{
	
	ArrayList<Notification> findByUsername(String username);

	@Transactional
	void deleteByMessageAndUsername(String message, String username);
}