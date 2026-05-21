package org.example.customer_app;

// OOP: POLYMORPHISM - implements DiscountStrategy with percentage formula
public class HolidayDiscount implements DiscountStrategy {
    private double discountPercent;

    public HolidayDiscount(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    @Override
    public double calculateDiscount(double cartTotal) {
        return cartTotal * (discountPercent / 100.0);
    }

    @Override
    public String getDiscountName() {
        return "Holiday Sale (" + discountPercent + "% off)";
    }
}
