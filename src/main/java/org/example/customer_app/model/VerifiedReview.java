package org.example.customer_app.model;

import org.example.customer_app.model.Review;

public class VerifiedReview extends Review {
    public VerifiedReview(String bookId, String user, int rating, String comment) {
        super(bookId, user, rating, comment);
    }
}
