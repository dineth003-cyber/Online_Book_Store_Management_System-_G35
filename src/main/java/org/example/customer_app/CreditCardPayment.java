package org.example.customer_app;

// OOP: POLYMORPHISM - overrides processPayment() with card-specific logic
// OOP: ENCAPSULATION - card details are private
public class CreditCardPayment extends PaymentProcessor {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

    public CreditCardPayment(String cardNumber, String cardHolderName,
                              String expiryDate, String cvv) {
        this.cardNumber     = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate     = expiryDate;
        this.cvv            = cvv;
    }

    @Override
    public boolean processPayment(double amount) {
        if (cardNumber == null || cardNumber.length() < 16) {
            isSuccessful = false;
            return false;
        }
        transactionId = generateTransactionId();
        isSuccessful  = true;
        System.out.println("[CreditCard] Payment of Rs." + amount + " SUCCESS. TXN: " + transactionId);
        return true;
    }

    @Override
    public String getPaymentType() { return "Credit Card"; }

    public String getMaskedCardNumber() {
        if (cardNumber != null && cardNumber.length() >= 4)
            return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
        return "****";
    }
}
