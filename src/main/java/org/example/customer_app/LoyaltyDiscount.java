package org.example.customer_app;

// OOP: POLYMORPHISM - implements DiscountStrategy with points-based formula
public class LoyaltyDiscount implements DiscountStrategy {
    private static final double POINTS_TO_RUPEE_RATE = 0.5;
    private int loyaltyPoints;

    public LoyaltyDiscount(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    @Override
    public double calculateDiscount(double cartTotal) {
        double discount = loyaltyPoints * POINTS_TO_RUPEE_RATE;
        return Math.min(discount, cartTotal);
    }

    @Override
    public String getDiscountName() {
        return "Loyalty Points (" + loyaltyPoints + " pts redeemed)";
    }
}
