package org.example.customer_app.model;

public class PhysicalBook extends Book {
    private double weightKg;

    // FIXED: Constructor එකට Publisher සහ double weightKg නිවැරදිව එකතු කළා
    public PhysicalBook(String bookId, String title, String genre, double price, Publisher publisher, double weightKg) {
        super(bookId, title, genre, price, publisher); // Parent class (Book) එකේ constructor එකට publisher ව යැව්වා
        this.weightKg = weightKg;
    }

    @Override
    public double calculateShippingCost() {
        // උදාහරණයක්: කිලෝ එකකට රු. 200ක shipping ගාස්තුවක්
        return weightKg * 200.0;
    }

    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }
}