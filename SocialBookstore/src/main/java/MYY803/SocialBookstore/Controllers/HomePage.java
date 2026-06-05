package MYY803.SocialBookstore.Controllers;

import java.util.ArrayList;
import java.util.List;

import MYY803.SocialBookstore.Entities.BookAuthors;
import MYY803.SocialBookstore.Entities.Offer;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import MYY803.SocialBookstore.Entities.Book;
import MYY803.SocialBookstore.Entities.BookAuthors;
import MYY803.SocialBookstore.Entities.FavourableAuthor;
import MYY803.SocialBookstore.Entities.FavourableCategory;
import MYY803.SocialBookstore.Entities.Notification;
import MYY803.SocialBookstore.Entities.Offer;
import MYY803.SocialBookstore.Entities.Request;
import MYY803.SocialBookstore.Entities.User;
import MYY803.SocialBookstore.Entities.UserKey;
import MYY803.SocialBookstore.Repositories.IAuthorRepository;
import MYY803.SocialBookstore.Repositories.IBookAuthorRepository;
import MYY803.SocialBookstore.Repositories.IBookRepository;
import MYY803.SocialBookstore.Repositories.IFavourableCategoryRepository;
import MYY803.SocialBookstore.Repositories.INotificationRepository;
import MYY803.SocialBookstore.Repositories.IOfferRepository;
import MYY803.SocialBookstore.Repositories.IRequestRepository;
import MYY803.SocialBookstore.Repositories.IUserRepository;
import jakarta.persistence.Transient;

public class HomePage {

	
	
	
	
	public HomePage() {
		
	}
	
	public void searchMyOffers(Model model, User user, IBookAuthorRepository bookAuthRepository, IBookRepository bookRepository, IOfferRepository offerRepository) {
		ArrayList<ArrayList<String>> myOffers = new ArrayList<>();
		List<Offer> offers = offerRepository.findByOfferKeyUserUsername(user.getUsername());
		ArrayList<BookAuthors> authors = new ArrayList<>();
		for(int i = 0; i < offers.size(); i ++){
			ArrayList<String> myMiniOffer = new ArrayList<>();
			myMiniOffer.add(bookRepository.findByTitle(offers.get(i).getTitle()).getTitle());
			authors = bookAuthRepository.findByBookAuthKeyBookTitle(bookRepository.findByTitle(offers.get(i).getTitle()).getTitle());
			for(int j = 0; j < authors.size(); j++) {
				myMiniOffer.add(authors.get(j).getAuthors());
			}
			myOffers.add(myMiniOffer);
        }
		model.addAttribute("myOffers", myOffers);
	}
	
	
	public void searchMyRequests(Model model, User user, IRequestRepository requestRepository) {
		ArrayList<Request> myRequests = new ArrayList<>();
		ArrayList<ArrayList<String>> myRequestsInfo = new ArrayList<>();
		myRequests = requestRepository.findByRequestKeyOfferOfferKeyUserUsername(user.getUsername());
		if(!myRequests.isEmpty()) {
			for(int k=0; k<myRequests.size(); k++) {
				ArrayList<String> requestInfo = new ArrayList<>();
				requestInfo.add(myRequests.get(k).getRequestKey().getOffer().getTitle());
				requestInfo.add(myRequests.get(k).getRequestKey().getRequester().getUsername());
				myRequestsInfo.add(requestInfo);
			}
			model.addAttribute("myRequestsInfo", myRequestsInfo);
		}
	}
	
	public void searchMyNotifications(Model model, INotificationRepository notifRepository, User user) {
		ArrayList<Notification> notifications = new ArrayList<>();
		notifications = notifRepository.findByUsername(user.getUsername());
		ArrayList<String> messages = new ArrayList<>();
		if(notifications.isEmpty()) {
			messages.add("No new messages");
		}
		else {
			
			for(Notification notificationMessage: notifications) {
				messages.add(notificationMessage.getMessage());
			}
		}
		model.addAttribute("messages",messages);
	}
	
	public void searchMyResults() {
		
	}
	
	public void sendNotifications(Model model, String title,String username, User user, IRequestRepository requestRepository, INotificationRepository notifRepository) {

		ArrayList<Request> requests =new ArrayList<>();
		requests = requestRepository.findByRequestKeyOfferOfferKeyUserUsernameAndRequestKeyOfferOfferKeyBookTitle(user.getUsername(),title);
		for (Request request:requests) {
			String message;
			if(!request.getRequestKey().getRequester().getUsername().equals(username)) {
				 message = "Your request for the book "+title+" of "+request.getRequestKey().getOffer().getUser().getUsername()+"has been rejected !";
			}
			else {
				 message = "Your request for the book "+title+" of "+request.getRequestKey().getOffer().getUser().getUsername()+" has been accepted ! Good Readings !";
			}
			Notification notification = new Notification(request.getRequestKey().getRequester().getUsername(),message);
			notifRepository.save(notification);
		}
	}
	
	public void updateOffersAndRequests(String title, User user, IRequestRepository requestRepository ,IOfferRepository offerRepository) {
		requestRepository.deleteByRequestKeyOfferOfferKeyUserUsernameAndRequestKeyOfferOfferKeyBookTitle(user.getUsername(),title);
		if(requestRepository.findByRequestKeyOfferOfferKeyUserUsernameAndRequestKeyOfferOfferKeyBookTitle(user.getUsername(),title).isEmpty()) {
			offerRepository.deleteByOfferKeyUserUsernameAndOfferKeyBookTitle(user.getUsername(),title);
		}
		else {
			System.out.println("Try again");
		}
	}
	
	public void showContactInfo(String username ,User user , INotificationRepository notifRepository,IUserRepository userRepository) {
		User user1 = userRepository.findByUsername(username);
		String phoneNumber =user1.getPhoneNumber();
		String address = user1.getAddress();
		String fullName = user1.getFullName();
		String info = "Contact Information of accepted requester:<br> Full Name: " + fullName + "<br> Address: " + address +"<br> Phone number: " + phoneNumber;
		Notification notif = new Notification(user.getUsername(), info);
		notifRepository.save(notif);
	}
	
	public ArrayList<Book> findBooksWithSpecificCategories(ArrayList<FavourableCategory> categories,IBookRepository bookRepository){
		ArrayList<Book> listOfBooks = new ArrayList<>();
		for(FavourableCategory category : categories) {
			ArrayList<Book> books = bookRepository.findByCategory(category.getCategory());
			for(Book book:books) {
				listOfBooks.add(book);
			}
		}
		return listOfBooks;
		
	}
	
	public void findOffersForSpecificBooks(ArrayList<Book> listOfBooks, IOfferRepository offerRepository, IBookAuthorRepository bookAuthRepository) {
		
	}
	
	public ArrayList<ArrayList<String>> searchByTitle(String title,User user, IBookRepository bookRepository, IBookAuthorRepository bookAuthRepository , IOfferRepository offerRepository) {
		ArrayList<ArrayList<String>> offersDetailsList = new ArrayList<>();
		ArrayList<Book> books = bookRepository.findByTitleContaining(title);
		for(Book book:books) {
			ArrayList<BookAuthors> bookAuth = bookAuthRepository.findByBookAuthKeyBookTitle(book.getTitle());
			ArrayList<Offer> offers = offerRepository.findByOfferKeyBookTitle(book.getTitle());
			for(Offer offer : offers) {
				ArrayList<String> newOfferDetails = makeOfferDetail(bookAuth, book, offer); // make the new OfferDetails
				checkIfOfferDetailsExist(offersDetailsList, newOfferDetails, user);    // check if offer already exists or i am the offerer and add it to the list 
			}
		}
		
		
		return offersDetailsList;
	}
	
	public ArrayList<ArrayList<String>> searchByAuthors(String author, User user, IBookRepository bookRepository, IBookAuthorRepository bookAuthRepository , IOfferRepository offerRepository) {
		String[] authors = author.split(",");
		ArrayList<ArrayList<String>> offersDetailsList = new ArrayList<>();
		for (String author1 : authors) {
			ArrayList<BookAuthors> bookAuthors = bookAuthRepository.findByBookAuthKeyAuthor(author1);
			ArrayList<String> bookTitles = new ArrayList<>();
			ArrayList<Offer> bookOffers = new ArrayList<>();
			for(BookAuthors bookAuthor: bookAuthors) {
				bookTitles.add(bookAuthor.getBookAuthorKey().getBook().getTitle());
			}
			for(String bookTitle: bookTitles) {
				ArrayList<Offer> bookOffers1 = offerRepository.findByOfferKeyBookTitle(bookTitle);
				for(Offer offer: bookOffers1) {
					bookOffers.add(offer);
				}
			}
			for(Offer offer : bookOffers) {
				String bookTitle = offer.getTitle();
				Book book = bookRepository.findByTitle(bookTitle);
				ArrayList<BookAuthors> bookAuth = bookAuthRepository.findByBookAuthKeyBookTitle(bookTitle);
				ArrayList<String> newOfferDetails = makeOfferDetail(bookAuth, book, offer); // make the new OfferDetails
				checkIfOfferDetailsExist(offersDetailsList, newOfferDetails, user);    // check if offer already exists or i am the offerer and add it to the list 
			}	
		}
		return offersDetailsList;
	}
	
	public ArrayList<ArrayList<String>> searchByTitleAndAuthors(String title, String author, User user, IBookRepository bookRepository, IBookAuthorRepository bookAuthRepository , IOfferRepository offerRepository) {
		ArrayList<ArrayList<String>> offersDetailsList = new ArrayList<>();
		String[] authors = author.split(",");
		ArrayList<Book> books = bookRepository.findByTitleContaining(title);
		for(Book book:books) {
			for (String author1 : authors) {
				if(!(bookAuthRepository.findByBookAuthKeyBookTitleAndBookAuthKeyAuthor(book.getTitle(),author1)).isEmpty()) {
					
					ArrayList<BookAuthors> bookAuth = bookAuthRepository.findByBookAuthKeyBookTitle(book.getTitle());
					ArrayList<Offer> offers = offerRepository.findByOfferKeyBookTitle(book.getTitle());
					for(Offer offer : offers) {
						ArrayList<String> newOfferDetails = makeOfferDetail(bookAuth, book, offer); // make the new OfferDetails
						checkIfOfferDetailsExist(offersDetailsList, newOfferDetails, user);    // check if offer already exists or i am the offerer and add it to the list 
					}
				}
			}
		}
		return offersDetailsList;
	}
		
	
	public ArrayList<String> makeOfferDetail(ArrayList<BookAuthors> bookAuthors, Book book, Offer offer) {
		ArrayList<String> newOfferDetails = new ArrayList<>();
		newOfferDetails.add(book.getTitle());
		String category = book.getCategory();
		
		String authors= "";
		int numOfAuthors = bookAuthors.size()-1;
		for(int i=0; i<numOfAuthors; i++) {
			authors+=bookAuthors.get(i).getAuthors() + ", ";
		}
		authors+=bookAuthors.get(numOfAuthors).getAuthors();
		newOfferDetails.add(authors);
		newOfferDetails.add(category);
		newOfferDetails.add(offer.getUser().getUsername());
		return newOfferDetails;
	}
	
	public void checkIfOfferDetailsExist(ArrayList<ArrayList<String>> offersDetailsList, ArrayList<String> newOfferDetails, User user) {
		boolean flag = false;
		
		for(ArrayList<String> offerDetails : offersDetailsList) { // check if offer aldready exists in list
			if((offerDetails.get(0).equals(newOfferDetails.get(0)) && offerDetails.get(2).equals(newOfferDetails.get(2)))) {
				flag = true;
				break;
			}
		}
		if(user.getUsername().equals(newOfferDetails.get(3))){ // check if i am the offerer
			flag = true; 
		}
		if(flag == false) {
			
			offersDetailsList.add(newOfferDetails);
		}
		
	}
	
	public ArrayList<ArrayList<String>> searchByCategory(User user, IFavourableCategoryRepository catRepository, IOfferRepository offerRepository, IBookAuthorRepository bookAuthRepository, IBookRepository bookRepository){
		ArrayList<FavourableCategory> categories = catRepository.findByCatKeyUserUsername(user.getUsername());
		ArrayList<ArrayList<String>> offersDetailsList = new ArrayList<>();
		ArrayList<Book> listOfBooks =findBooksWithSpecificCategories(categories, bookRepository); // returns all the books who have a specific category
		for (Book book : listOfBooks) {
			ArrayList<Offer> offers = offerRepository.findByOfferKeyBookTitle(book.getTitle());
			ArrayList<BookAuthors> bookAuth = bookAuthRepository.findByBookAuthKeyBookTitle(book.getTitle());
			for(Offer offer:offers) {
				ArrayList<String> newOfferDetails =makeOfferDetail(bookAuth, book, offer); // make the new OfferDetails
				checkIfOfferDetailsExist(offersDetailsList, newOfferDetails,user); // check if offer already exists or i am the offerer and add it to the list 
			}
		}
		return offersDetailsList;
	}
	
	public ArrayList<ArrayList<String>> searchByAuthors(User user, IAuthorRepository authRepository, IOfferRepository offerRepository, IBookAuthorRepository bookAuthRepository, IBookRepository bookRepository){
		ArrayList<FavourableAuthor> authors = authRepository.findByAuthKeyUserUsername(user.getUsername());
		ArrayList<ArrayList<String>> offersDetailsList = new ArrayList<>();
		
		for(FavourableAuthor author: authors) {
			ArrayList<BookAuthors> bookAuthorsList = bookAuthRepository.findByBookAuthKeyAuthor(author.getAuthor());
			for(BookAuthors bookAuthorList : bookAuthorsList) {
				String title = bookAuthorList.getBookAuthorKey().getBook().getTitle();
				ArrayList<Offer> offers = offerRepository.findByOfferKeyBookTitle(title);
				Book book = bookRepository.findByTitle(title);
				ArrayList<BookAuthors> bookAuth = bookAuthRepository.findByBookAuthKeyBookTitle(book.getTitle());
				for(Offer offer : offers) {
					ArrayList<String> newOfferDetails = makeOfferDetail(bookAuth, book, offer); // make the new OfferDetails
					checkIfOfferDetailsExist(offersDetailsList, newOfferDetails, user);    // check if offer already exists or i am the offerer and add it to the list 
				}
			}
		}
		return offersDetailsList;
	}
}
