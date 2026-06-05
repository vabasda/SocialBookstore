package MYY803.SocialBookstore;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import MYY803.SocialBookstore.Controllers.HomeController;
import MYY803.SocialBookstore.Controllers.HomePage;
import MYY803.SocialBookstore.Entities.Book;
import MYY803.SocialBookstore.Entities.BookAuthors;
import MYY803.SocialBookstore.Entities.FavourableAuthor;
import MYY803.SocialBookstore.Entities.FavourableCategory;
import MYY803.SocialBookstore.Entities.Offer;
import MYY803.SocialBookstore.Entities.Request;
import MYY803.SocialBookstore.Entities.User;
import MYY803.SocialBookstore.Repositories.IAuthorRepository;
import MYY803.SocialBookstore.Repositories.IBookAuthorRepository;
import MYY803.SocialBookstore.Repositories.IBookRepository;
import MYY803.SocialBookstore.Repositories.IFavourableCategoryRepository;
import MYY803.SocialBookstore.Repositories.IOfferRepository;
import MYY803.SocialBookstore.Repositories.IRequestRepository;
import MYY803.SocialBookstore.Repositories.IUserRepository;

@SpringBootTest
class SocialBookstoreApplicationTests {

	@Mock
    private IUserRepository userRepository;
	
	@Mock
    private IBookRepository bookRepository;
	
	@Mock
    private IFavourableCategoryRepository catRepository;

    @Mock
    private IBookAuthorRepository bookAuthRepository;
    
    @Mock
    private IAuthorRepository authRepository;
    
    @Mock
    private IOfferRepository offerRepository;
    
    @Mock
    private IRequestRepository requestRepository;

    @InjectMocks
    private HomeController homeController;
    
    @Mock
    private HomePage homePage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCheckLogin_UserExists() {
        Model model = mock(Model.class);
        User existingUser = new User(); // Set up a user object for testing
        existingUser.setUsername("username1");
        existingUser.setPassword("123");

        // Mock userRepository to return the existing user
        when(userRepository.findByUsername("username1")).thenReturn(existingUser);

        // Call the checkLogin method with the existing user
        String viewName = homeController.checkLogin(model, existingUser);

        // Verify that the method redirects to the homepage
        assertEquals("redirect:/homePage", viewName, "Redirect to homepage not performed");
    }
    
    @Test
    public void testCheckLogin_UserNotExists() {
        Model model = mock(Model.class);
        User existingUser = new User(); // Set up a user object for testing
        existingUser.setUsername("username1");
        existingUser.setPassword("123");

        // Mock userRepository to return the existing user
        when(userRepository.findByUsername("username2")).thenReturn(existingUser);

        // Call the checkLogin method with the existing user
        String viewName = homeController.checkLogin(model, existingUser);

        // Verify that the method redirects to the homepage
        assertEquals("loginPage", viewName, "Redirect to login page not performed");
    }
    
    @Test
    public void testCheckLogin_WrongPassword() {
        Model model = mock(Model.class);
        User existingUser = new User(); // Set up a user object for testing
        existingUser.setUsername("username1");
        existingUser.setPassword("correctPassword");

        // Mock userRepository to return the existing user
        when(userRepository.findByUsername("username1")).thenReturn(existingUser);

        // Call the checkLogin method with the existing user and wrong password
        User userWithWrongPassword = new User();
        userWithWrongPassword.setUsername("username1");
        userWithWrongPassword.setPassword("wrongPassword");
        String viewName = homeController.checkLogin(model, userWithWrongPassword);

        // Verify that the method adds an error message and returns the login page
        verify(model).addAttribute(eq("errorMessage"), eq("Wrong username or password! Please try again or create new account."));
        assertEquals("loginPage", viewName);
    }
    
    @Test
    public void testCheckCreateAccountPage_ExistingUser() {
        Model model = mock(Model.class);
        User existingUser = new User();
        existingUser.setUsername("username1");

        // Mock userRepository to return the existing user
        when(userRepository.findByUsername("username1")).thenReturn(existingUser);

        // Call the checkCreateAccountPage method with an existing user
        String viewName = homeController.checkCreateAccountPage(model, existingUser, null, null);

        // Verify that the method adds an error message and returns the createAccountPage
        verify(model).addAttribute("errorMessage", "An account already exists with this username! Please try again with another one.");
        assertEquals("createAccountPage", viewName);
    }
    
    @Test
    public void testCheckCreateAccountPage_NewUser() {
        Model model = mock(Model.class);
        User newUser = new User();
        newUser.setUsername("newUser");

        // Mock userRepository to return null, indicating that the user does not exist
        when(userRepository.findByUsername("newUser")).thenReturn(null);

        // Call the checkCreateAccountPage method with a new user
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Category1");
        String viewName = homeController.checkCreateAccountPage(model, newUser, categories , "Author1,Author2");

        // Verify that the method saves the user and returns the loginPage
        verify(userRepository).save(newUser);
        assertEquals("loginPage", viewName);
    }
    
    @Test
    public void testRedirectToHomePageWhenOfferDoesNotExist() {
    	Model model = mock(Model.class);
        Book book = new Book();
        User newUser = new User();
        newUser.setUsername("testUser");
        book.setTitle("testBookTitle");
        homeController.setUser(newUser);
        // Mock offerRepository to return null, indicating that the offer does not exist
        when(offerRepository.findByOfferKeyUserUsernameAndOfferKeyBookTitle("testUser", "testBookTitle")).thenReturn(null);
        // Mock bookRepository to return null, indicating that the book does not exist
        when(bookRepository.findByTitle("testBookTitle")).thenReturn(null);

        // Call the checkMakeOfferPage method
        String viewName = homeController.checkMakeOfferPage(book, "author1,author2", model);

        // Verify that the book is saved, authors are saved, and offer is saved, and the method redirects to the homepage
        verify(bookRepository).save(book);
        verify(bookAuthRepository, times(2)).save(any());
        verify(offerRepository).save(any());
        assertEquals("redirect:/homePage", viewName);
    }
    
    @Test
    public void testRedirectToHomePageWhenOfferDoesExist() {
    	Model model = mock(Model.class);
        Book book = new Book();
        User newUser = new User();
        Offer offer = new Offer();
        newUser.setUsername("testUser");
        book.setTitle("testBookTitle");
        
        homeController.setUser(newUser);
        when(userRepository.findByUsername("testUser")).thenReturn(newUser);
        when(offerRepository.findByOfferKeyUserUsernameAndOfferKeyBookTitle("testUser", "testBookTitle")).thenReturn(offer);
        when(bookRepository.findByTitle("testBookTitle")).thenReturn(book);

        // Call the checkMakeOfferPage method
        String viewName = homeController.checkMakeOfferPage(book, "author1,author2", model);

        // Verify that the book is saved, authors are saved, and offer is saved, and the method redirects to the homepage
        verify(model).addAttribute(eq("errorMessage"), anyString());
        assertEquals("makeOfferPage", viewName);
    }

    @Test
    public void testRemoveOffer() {
        String title = "testBookTitle";
        User user = new User();
        user.setUsername("testUser");
        homeController.setUser(user);
        
        // Call removeOffer method
        String result = homeController.removeOffer(title);

        // Verify that offerRepository.deleteByOfferKeyUserUsernameAndOfferKeyBookTitle was not called because no requests exist
        verify(offerRepository).deleteByOfferKeyUserUsernameAndOfferKeyBookTitle("testUser", title);

        // Assert that the method redirects to the homepage
        assertEquals("redirect:/homePage", result);
    }

    @Test
    public void testMakeRequest() {
        // Mock offer data
        String title = "testBookTitle";
        User offerer = new User();
        String offerer1 = "offerer";
        Offer offer = new Offer();
        homeController.setUser(offerer);
        
        when(offerRepository.findByOfferKeyUserUsernameAndOfferKeyBookTitle(offerer1, title)).thenReturn(offer);

        // Call makeRequest method
        String result = homeController.makeRequest(title, offerer1);

        // Verify that offerRepository.findByOfferKeyUserUsernameAndOfferKeyBookTitle was called with the correct arguments
        verify(offerRepository).findByOfferKeyUserUsernameAndOfferKeyBookTitle(offerer1, title);

        verify(requestRepository).save(any(Request.class));

        // Assert that the method redirects to the homepage
        assertEquals("redirect:/homePage", result);
    }

    @Test
    public void testSearchByCategory() {
        // Mock data
        User user = new User();
        user.setUsername("testUser");
        ArrayList<FavourableCategory> categories = new ArrayList<>();
        // Add mock categories if needed

        ArrayList<ArrayList<String>> offersDetailsList = new ArrayList<>();
        // Add mock offers details list if needed

        // Mock categoryRepository to return mock categories
        when(catRepository.findByCatKeyUserUsername("testUser")).thenReturn(categories);

        // Mock bookRepository to return mock list of books
        ArrayList<Book> listOfBooks = new ArrayList<>();
        // Add mock books if needed
        when(homePage.findBooksWithSpecificCategories(categories, bookRepository)).thenReturn(listOfBooks);

        // Mock offerRepository to return mock offers for each book
        ArrayList<Offer> offers = new ArrayList<>();
        // Add mock offers if needed
        when(offerRepository.findByOfferKeyBookTitle(anyString())).thenReturn(offers);

        // Call searchByCategory method
        ArrayList<ArrayList<String>> result = homePage.searchByCategory(user, catRepository, offerRepository, bookAuthRepository, bookRepository);

        // Verify that offerRepository.findByOfferKeyBookTitle was called for each book
        verify(offerRepository, times(listOfBooks.size())).findByOfferKeyBookTitle(anyString());

                assertEquals(offersDetailsList, result);
    }
    
    @Test
    public void testSearchByBoth() {
        // Mock data
        User user = new User();
        user.setUsername("testUser");
        homeController.setUser(user);

        ArrayList<ArrayList<String>> offersDetailsListCategory = new ArrayList<>();
        // Add mock offers details list for category if needed

        ArrayList<ArrayList<String>> offersDetailsListAuthor = new ArrayList<>();
        // Add mock offers details list for author if needed

        ArrayList<ArrayList<String>> combinedOffersDetailsList = new ArrayList<>();
        // Add mock combined offers details list if needed

        // Mock homepage to return mock lists of offers details
        when(homePage.searchByCategory(eq(user), any(), any(), any(), any())).thenReturn(offersDetailsListCategory);
        when(homePage.searchByAuthors(eq(user), any(), any(), any(), any())).thenReturn(offersDetailsListAuthor);

        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        // Call searchByBoth method
        homeController.searchByBoth(null, redirectAttributes);

        // Assert that the combined offers details list is added to the flash attribute
        verify(homePage).searchByCategory(eq(user), any(), any(), any(), any());
        verify(homePage).searchByAuthors(eq(user), any(), any(), any(), any());
        verify(redirectAttributes).addFlashAttribute("bookOffersDetails", combinedOffersDetailsList);
    }
    

    
}