package org.example.customer_app.model;

public class EBook extends Book {
    private double fileSizeMB;

    // FIXED: Constructor එකට double fileSizeMB සහ Publisher publisher එකතු කළා
    public EBook(String bookId, String title, String genre, double price, Publisher publisher, double fileSizeMB) {
        super(bookId, title, genre, price, publisher); // Book class එකේ constructor එකට publisher ව යැව්වා
        this.fileSizeMB = fileSizeMB;
    }

    @Override
    public double calculateShippingCost() {
        return 0.0; // EBook වලට shipping ගාස්තු නැත
    }

    public double getFileSizeMB() { return fileSizeMB; }
    public void setFileSizeMB(double fileSizeMB) { this.fileSizeMB = fileSizeMB; }
}