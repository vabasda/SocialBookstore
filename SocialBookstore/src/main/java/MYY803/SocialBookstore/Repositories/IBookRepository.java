package MYY803.SocialBookstore.Repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import MYY803.SocialBookstore.Entities.Book;

@Repository
public interface IBookRepository extends JpaRepository<Book,String>{
	
	Book findByTitle(String title);

	ArrayList<Book> findByCategory(String category);

	ArrayList<Book> findByTitleContaining(String title);
}