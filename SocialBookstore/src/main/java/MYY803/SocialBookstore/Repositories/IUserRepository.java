package MYY803.SocialBookstore.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import MYY803.SocialBookstore.Entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User,String>{//UserKey
	
	User findByUsername(String username); 
	
	User findByFullName(String fullname);
}
