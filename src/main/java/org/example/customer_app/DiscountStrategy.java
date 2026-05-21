package org.example.customer_app;

// OOP: ABSTRACTION + POLYMORPHISM - swap discount types without changing Order code
public interface DiscountStrategy {
    double calculateDiscount(double cartTotal);
    String getDiscountName();
}
