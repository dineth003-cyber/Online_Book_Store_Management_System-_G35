package org.example.customer_app.model;

public abstract class Book {
    private String bookId;
    private String title;
    private String genre;
    private double price;
    private Publisher publisher;

    // Default Constructor
    public Book() {}

    // Parameterized Constructor (Fixed publisher)
    public Book(String bookId, String title, String genre, double price, Publisher publisher) {
        this.bookId = bookId;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.publisher = publisher;
    }

    // Polymorphism: අදාළ පොතේ වර්ගය අනුව Shipping Cost එක වෙනස් වීම සඳහා
    public abstract double calculateShippingCost();

    // Getters and Setters
    public String getBookId() { return bookId; }
    public void setBookId(String bookId) { this.bookId = bookId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public Publisher getPublisher() { return publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }
}