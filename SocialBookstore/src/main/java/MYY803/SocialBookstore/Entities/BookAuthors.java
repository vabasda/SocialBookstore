package MYY803.SocialBookstore.Entities;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="bookauthors")
public class BookAuthors {
	
	@EmbeddedId
	private BookAuthorsKey bookAuthKey;
	
	public BookAuthors() {
	}
	
	public BookAuthors(Book book, String author){
		bookAuthKey = new BookAuthorsKey(book,author);
	}
	
	public String getAuthors() {
		return bookAuthKey.getAuthors();
	}
	
	public BookAuthorsKey getBookAuthorKey() {
		return this.bookAuthKey;
	}
}
