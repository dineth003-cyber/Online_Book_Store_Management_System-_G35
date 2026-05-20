package org.example.customer_app.controller;

import jakarta.servlet.http.HttpSession;
import org.example.customer_app.model.Book;
import org.example.customer_app.model.EBook;
import org.example.customer_app.model.PhysicalBook;
import org.example.customer_app.model.Publisher;
import org.example.customer_app.model.RegisteredUser;
import org.example.customer_app.service.BookService;
import org.example.customer_app.service.CartService;
import org.example.customer_app.service.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserManager userManager = new UserManager();
    private BookService bookService = new BookService();
    private CartService cartService = new CartService();

    // 1. Registration form එක පෙන්වීම
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegisteredUser("", "", "", "", "", ""));
        return "register";
    }

    // 2. Register වීම සහ කෙලින්ම Login පිටුවට යැවීම (Redirect)
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegisteredUser user, Model model) {
        userManager.registerUser(user);
        return "redirect:/login";
    }

    // 3. Login form එක පෙන්වීම
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // 4. Login කිරීමේ ක්‍රියාවලිය
    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        RegisteredUser user = userManager.login(email, password);

        if (user != null) {
            session.setAttribute("loggedInUser", user);
            return "redirect:/customer_dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }
    }

    // 5. Dashboard එක පෙන්වීම
    @GetMapping("/customer_dashboard")
    public String showDashboard(HttpSession session, Model model) {
        RegisteredUser user = (RegisteredUser) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("name", user.getName());
        model.addAttribute("customer", new RegisteredUser("", "", "", "", "", ""));
        model.addAttribute("customers", userManager.getAllUsers());
        return "customer_dashboard";
    }

    // 6. පාරිභෝගිකයෙකු මකා දැමීම (Delete)
    @GetMapping("/deleteCustomer/{id}")
    public String deleteCustomer(@PathVariable String id, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";

        userManager.deleteUser(id);
        return "redirect:/customer_dashboard";
    }

    // 7. Edit කිරීමට අවශ්‍ය දත්ත පෙන්වීම
    @GetMapping("/editCustomer/{id}")
    public String showEditForm(@PathVariable String id, HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";

        List<RegisteredUser> users = userManager.getAllUsers();
        RegisteredUser userToEdit = users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);

        model.addAttribute("customer", userToEdit);
        model.addAttribute("customers", users);
        model.addAttribute("name", ((RegisteredUser)session.getAttribute("loggedInUser")).getName());
        return "customer_dashboard";
    }

    // 8. Logout වීම
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/book-form")
    public String showBookForm() {
        return "book-form";
    }

    @PostMapping("/addCustomer")
    public String addCustomer(@RequestParam String id,
                              @RequestParam String name,
                              @RequestParam String email,
                              @RequestParam String phone,
                              @RequestParam String address) {
        userManager.addCustomer(id, name, email, phone, address);
        return "redirect:/customer_dashboard";
    }

    @GetMapping("/books")
    public String viewBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "book-list";
    }
    @GetMapping("/customers")
    public String customers(Model model) {
        model.addAttribute("customers", userManager.getAllCustomers());
        return "customer_dashboard";
    }


    @PostMapping("/add-to-cart-book")
    public String addBookToCart(@RequestParam String id) {
        Book book = bookService.findById(id);
        if (book != null) {
            cartService.addToCart(
                    book.getBookId(),
                    book.getTitle(),
                    1,
                    book.getPrice()
            );
        }
        return "redirect:/cart";
    }

    // FIXED: Polymorphism සහ අලුත් Constructor වලට ගැලපෙන්න සම්පූර්ණයෙන්ම සකස් කළා
    @PostMapping("/add-book")
    public String addBook(@RequestParam String id,
                          @RequestParam String title,
                          @RequestParam String genre,
                          @RequestParam double price,
                          @RequestParam String type) {

        Publisher defaultPub = new Publisher("ABC Publications");
        Book newBook;

        // HTML Form එකෙන් එන type එක අනුව අදාළ Object එක සෑදීම
        if ("ebook".equalsIgnoreCase(type)) {
            // EBook(id, title, genre, price, publisher, fileSizeMB)
            newBook = new EBook(id, title, genre, price, defaultPub, 0.0);
        } else {

            // PhysicalBook(id, title, genre, price, publisher)
            newBook = new PhysicalBook(id, title, genre, price, defaultPub, 1.0);
            // සටහන: ඔයාගේ PhysicalBook එකේ Constructor එක මේ parameters පිළිවෙළටම තියෙනවාද බලන්න

        }

        // BookService එක හරහා ලිස්ට් එකට සේව් කිරීම
        bookService.addBook(newBook);

        System.out.println("Book Added Successfully: " + title + " (" + type + ")");

        return "redirect:/books";
    }
}