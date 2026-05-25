package org.example.customer_app.model;

public class AudioBook extends Book {
    private double durationHours;

    // FIXED: Constructor එකට Publisher publisher ඇතුළත් කළා
    public AudioBook(String bookId, String title, String genre, double price, Publisher publisher, double durationHours) {
        super(bookId, title, genre, price, publisher); // Parent class එකට publisher ව යැව්වා
        this.durationHours = durationHours;
    }

    @Override
    public double calculateShippingCost() {
        return 0.0; // AudioBook වලට shipping ගාස්තු නැත
    }

    public double getDurationHours() { return durationHours; }
    public void setDurationHours(double durationHours) { this.durationHours = durationHours; }
}