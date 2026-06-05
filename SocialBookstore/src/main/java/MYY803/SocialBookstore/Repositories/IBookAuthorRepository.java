package MYY803.SocialBookstore.Repositories;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import MYY803.SocialBookstore.Entities.BookAuthors;
import jakarta.transaction.Transactional;

@Repository
public interface IBookAuthorRepository extends JpaRepository<BookAuthors,String>{

	ArrayList<BookAuthors> findByBookAuthKeyBookTitle(String title);

	ArrayList<BookAuthors> findByBookAuthKeyAuthor(String author);

	@Transactional
	ArrayList<BookAuthors> findByBookAuthKeyBookTitleAndBookAuthKeyAuthor(String title, String author1);
	
}
