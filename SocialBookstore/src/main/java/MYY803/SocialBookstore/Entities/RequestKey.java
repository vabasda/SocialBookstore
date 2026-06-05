package MYY803.SocialBookstore.Entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

@Embeddable
public class RequestKey implements Serializable{

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="requesterer", referencedColumnName="username")
	})
	private User requester;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="offerer", referencedColumnName="username"),
		@JoinColumn(name="title", referencedColumnName="title")
	})
	private Offer offer;
	
	
	public RequestKey() {
	}
	
	public RequestKey(User requester,Offer offer) {
		this.requester = requester;
		this.offer = offer;
		
	}
	
	public User getRequester() {
		return this.requester;
	}
	
	public Offer getOffer() {
		return this.offer;
	}
	
}
