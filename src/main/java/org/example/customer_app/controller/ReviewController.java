package org.example.customer_app.controller;

import org.example.customer_app.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReviewController {

    private ReviewService service = new ReviewService();

    // view page
    @GetMapping("/reviews")
    public String view(Model model) {
        model.addAttribute("reviews", service.getReviews());
        return "reviews";
    }

    // form page
    @GetMapping("/review-form")
    public String form() {
        return "review-form";
    }

    // add review
    @PostMapping("/add-review")
    public String add(@RequestParam String bookId,
                      @RequestParam String user,
                      @RequestParam int rating,
                      @RequestParam String comment) {

        service.addReview(bookId, user, rating, comment);
        return "redirect:/reviews";
    }

    // delete
    @GetMapping("/delete-review")
    public String delete(@RequestParam String user) {
        service.deleteReview(user);
        return "redirect:/reviews";
    }
}