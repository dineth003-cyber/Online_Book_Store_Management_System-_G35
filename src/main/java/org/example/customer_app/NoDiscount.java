package org.example.customer_app;

// OOP: POLYMORPHISM - zero discount, avoids null checks
public class NoDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(double cartTotal) { return 0.0; }

    @Override
    public String getDiscountName() { return "No Discount"; }
}
