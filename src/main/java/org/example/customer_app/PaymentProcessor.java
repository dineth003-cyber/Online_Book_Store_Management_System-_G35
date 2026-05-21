package org.example.customer_app;

// OOP: ABSTRACTION - hides complex payment logic behind simple methods
// OOP: POLYMORPHISM - each subclass overrides processPayment() differently
public abstract class PaymentProcessor {
    protected String  transactionId;
    protected boolean isSuccessful;

    public abstract boolean processPayment(double amount);
    public abstract String  getPaymentType();

    protected String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis();
    }

    public String  getTransactionId() { return transactionId; }
    public boolean isSuccessful()     { return isSuccessful; }
}
