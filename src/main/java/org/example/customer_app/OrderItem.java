package org.example.customer_app;

public class OrderItem {
    private final String bookId;
    private final String title;
    private final double priceAtPurchase;
    private final int    quantity;

    public OrderItem(String bookId, String title, double priceAtPurchase, int quantity) {
        this.bookId          = bookId;
        this.title           = title;
        this.priceAtPurchase = priceAtPurchase;
        this.quantity        = quantity;
    }

    public String getBookId()          { return bookId; }
    public String getTitle()           { return title; }
    public double getPriceAtPurchase() { return priceAtPurchase; }
    public int    getQuantity()        { return quantity; }
    public double getSubtotal()        { return priceAtPurchase * quantity; }

    public String toFileString() {
        return bookId + "," + title + "," + priceAtPurchase + "," + quantity;
    }

    public static OrderItem fromFileString(String line) {
        String[] parts = line.split(",");
        return new OrderItem(parts[0], parts[1],
                Double.parseDouble(parts[2]), Integer.parseInt(parts[3]));
    }
}
