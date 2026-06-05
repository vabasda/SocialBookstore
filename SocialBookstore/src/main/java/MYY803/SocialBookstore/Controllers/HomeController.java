package MYY803.SocialBookstore.Controllers;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

@Controller
public class HomeController {

	@Transient
	private User user;
	
	private HomePage homepage;
	
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private IFavourableCategoryRepository catRepository;
	@Autowired
	private IAuthorRepository authRepository;
	@Autowired
	private IBookAuthorRepository bookAuthRepository;
	@Autowired
	private IBookRepository bookRepository;
	@Autowired
	private IOfferRepository offerRepository;
	@Autowired
	private IRequestRepository requestRepository;
	@Autowired
	private INotificationRepository notifRepository;
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@GetMapping("/")
    public String showLoginPage(Model model) {
        
        return "loginPage"; 
    }
	
	@PostMapping("/login")
	public String checkLogin(Model model,@ModelAttribute User user) {
		setUser(user);
		User tempUser = userRepository.findByUsername(user.getUsername());
		if(tempUser != null) {
			if (user.getPassword().equals(tempUser.getPassword())){
				return "redirect:/homePage";
			}
			else {
				String errorMessage = "Wrong username or password! Please try again or create new account.";
				model.addAttribute("errorMessage", errorMessage);
				return "loginPage";
			}
		}
		else {
			String errorMessage = "Wrong username or password! Please try again or create new account.";
			model.addAttribute("errorMessage", errorMessage);
			return "loginPage";
		}
	}
	
	@GetMapping("/logout")
	public String logOut() {
		this.user = null;
		return "loginPage";
	}
	
	@GetMapping("/createAccount")
    public String showCreateAccountPage(Model model) {
        
        return "createAccountPage"; 
    }
	
	@PostMapping("/register")
    public String checkCreateAccountPage(Model model,@ModelAttribute User user,@RequestParam(value = "categories", required = false) List<String> categories,@RequestParam(value = "authors", required = false) String authors) {
		if(userRepository.findByUsername(user.getUsername()) == null){
			userRepository.save(user);
			for(String category: categories) {
				FavourableCategory cat = new FavourableCategory(user,category);
				catRepository.save(cat);
			}
			String[] authorsSeperated = authors.split(",");
			for(String author :authorsSeperated) {
				FavourableAuthor auth = new FavourableAuthor(user,author);
				authRepository.save(auth);
			}
	        return "loginPage";
		}
		else {
			String errorMessage= "An account already exists with this username! Please try again with another one.";
			model.addAttribute("errorMessage",errorMessage);	
			return "createAccountPage";
		}
    }
	
	
	
	@GetMapping("/homePage")
    public String showHomePage(Model model) {
		homepage = new HomePage();
		
		homepage.searchMyOffers(model, this.user , this.bookAuthRepository, this.bookRepository, this.offerRepository);
		homepage.searchMyRequests(model, user, requestRepository);
		homepage.searchMyNotifications(model, notifRepository, user);
		
		return "homePage";
    }
	
	
	@PostMapping("/makeOffer")
    public String showMakeOfferPage(Model model) {
        
        return "makeOfferPage"; 
    }
	
	
	@PostMapping("/addOffer")
    public String checkMakeOfferPage(@ModelAttribute Book book,@RequestParam(value = "authors", required = false) String authors,Model model) {
		if(offerRepository.findByOfferKeyUserUsernameAndOfferKeyBookTitle(this.user.getUsername(), book.getTitle()) == null) {
			if(bookRepository.findByTitle(book.getTitle())== null ){
				bookRepository.save(book);
				String[] authorsSeperated = authors.split(",");
				for(String author :authorsSeperated) {
					BookAuthors bookAuthor = new BookAuthors(book,author);
					bookAuthRepository.save(bookAuthor);
				}
				Offer offer = new Offer(this.user , book);
				offerRepository.save(offer);
				return "redirect:/homePage";
			}
			else {
				Offer offer = new Offer(this.user , book);
				offerRepository.save(offer);
				return "redirect:/homePage";
			}
		}
		else {
			String errorMessage= "You have already an active offer for this book! Please try again with another one.";
			model.addAttribute("errorMessage",errorMessage);	
			return "makeOfferPage";
		}
		
    }
	
	@PostMapping("/removeOffer")
	public String removeOffer(@RequestParam("bookTitle") String title) {
		requestRepository.deleteByRequestKeyOfferOfferKeyUserUsernameAndRequestKeyOfferOfferKeyBookTitle(this.user.getUsername(),title);
		if(requestRepository.findByRequestKeyOfferOfferKeyUserUsernameAndRequestKeyOfferOfferKeyBookTitle(this.user.getUsername(),title).isEmpty()) {
			offerRepository.deleteByOfferKeyUserUsernameAndOfferKeyBookTitle(this.user.getUsername(),title);
		}
		else {
			System.out.println("Try again");
		}
	    
	    return "redirect:/homePage";
	}
	
	
	
	
	@PostMapping("/deleteNotification")
	public String deleteNotification(@RequestParam("message") String message) {
		notifRepository.deleteByMessageAndUsername(message,this.user.getUsername());
	 
	    return "redirect:/homePage";
	}
	
	
	

	@PostMapping("/makeRequest")
	public String makeRequest(@RequestParam("book-title") String title,@RequestParam("offerer") String offerer) {
		
		Offer offer = offerRepository.findByOfferKeyUserUsernameAndOfferKeyBookTitle(offerer,title);
		Request request = new Request(this.user,offer);
		
		requestRepository.save(request);
		
		return "redirect:/homePage";
	}
	
	@PostMapping("/acceptRequest")
	public String searchOffers(Model model ,@RequestParam("booktitle2") String title, @RequestParam("username") String username) {
		
		
		homepage.sendNotifications(model, title, username, user, requestRepository, notifRepository);
		
		homepage.showContactInfo(username, user, notifRepository, userRepository);
		
		homepage.updateOffersAndRequests(title, user, requestRepository, offerRepository);
		
	    return "redirect:/homePage";
	}
	
	@GetMapping("/search") 
	public String search(@RequestParam("title") String title,@RequestParam("author") String author,Model model,RedirectAttributes redirectAttributes) {
		ArrayList<ArrayList<String>> offersDetailsList = new ArrayList<>();
		if(author.isBlank()) {
			offersDetailsList = homepage.searchByTitle(title, user, bookRepository, bookAuthRepository, offerRepository);
		}
		else if(title.isBlank()) {
			offersDetailsList = homepage.searchByAuthors(author, user, bookRepository, bookAuthRepository, offerRepository);
		}
		else {
			offersDetailsList = homepage.searchByTitleAndAuthors(title, author, user, bookRepository, bookAuthRepository, offerRepository);
		}
		redirectAttributes.addFlashAttribute("bookOffersDetails", offersDetailsList);
		return "redirect:/homePage";
	}
	
	@GetMapping("/searchByCategory")
	public String searchByCategory(Model model,RedirectAttributes redirectAttributes) {
		ArrayList<ArrayList<String>> offersDetailsList = homepage.searchByCategory(user, catRepository, offerRepository, bookAuthRepository, bookRepository);
		redirectAttributes.addFlashAttribute("bookOffersDetails", offersDetailsList);
		
		return  "redirect:/homePage";
	}
	
	@GetMapping("/searchByAuthor")
	public String searchByAuthor(Model model, RedirectAttributes redirectAttributes) {
		ArrayList<ArrayList<String>> offersDetailsList = homepage.searchByAuthors(user, authRepository, offerRepository, bookAuthRepository, bookRepository);
		redirectAttributes.addFlashAttribute("bookOffersDetails", offersDetailsList);
		return  "redirect:/homePage";
	}
	
	 
	@GetMapping("/searchByBoth")
	public String searchByBoth(Model model, RedirectAttributes redirectAttributes) {
		
		ArrayList<ArrayList<String>> offersDetailsList = new ArrayList<>();
		ArrayList<ArrayList<String>> offersDetailsListCategory = new ArrayList<>();
		ArrayList<ArrayList<String>> offersDetailsListAuthor = new ArrayList<>();
		offersDetailsListCategory = homepage.searchByCategory(user, catRepository, offerRepository, bookAuthRepository, bookRepository);
		offersDetailsListAuthor = homepage.searchByAuthors(user, authRepository, offerRepository, bookAuthRepository, bookRepository);
		for(ArrayList<String> offerDetail:offersDetailsListCategory) {
			if(offersDetailsListAuthor.contains(offerDetail)) {
				offersDetailsList.add(offerDetail);
			}
		}
		redirectAttributes.addFlashAttribute("bookOffersDetails", offersDetailsList);
		return  "redirect:/homePage";
	}
}