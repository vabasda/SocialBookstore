package MYY803.SocialBookstore.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="books")
public class Book{

	@Id
	@Column(name = "title")
	private String title;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "summary")
	private String summary;
	
	public Book() {
		
	}
	
	public Book(String title,String category, String summary) {
		this.title = title;
		this.category = category;
		this.summary = summary;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public String getSummary() {
		return this.summary;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
}
