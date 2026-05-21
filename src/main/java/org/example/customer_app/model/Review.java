package org.example.customer_app.model;

public class Review {

    private String bookId;
    private String user;
    private int rating;
    private String comment;

    public Review(String bookId, String user, int rating, String comment) {
        this.bookId = bookId;
        this.user = user;
        this.rating = rating;
        this.comment = comment;
    }

    public String getBookId() { return bookId; }
    public String getUser() { return user; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }

    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
}
