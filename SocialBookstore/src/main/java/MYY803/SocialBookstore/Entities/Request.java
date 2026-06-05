package MYY803.SocialBookstore.Entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="requests")
public class Request {

	@EmbeddedId
	private RequestKey requestKey;
	
	public Request() {	
	}
	
	public Request(User requester,Offer offer){
		requestKey = new RequestKey(requester,offer);
	}
	
	public RequestKey getRequestKey() {
		return this.requestKey;
	}
	
}
