package MYY803.SocialBookstore.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;


@Embeddable
public class BookAuthorsKey implements Serializable{

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="title", referencedColumnName="title")
	})
	private Book book;

	@Column(name="author")
	private String author;
	
	
	
	public BookAuthorsKey() {
	}
	
	public BookAuthorsKey(Book book,String author) {
		this.book = book;
		this.author = author;
	}

	public String getAuthors() {
		return this.author;
	}
	
	public Book getBook() {
		return this.book;
	}
}