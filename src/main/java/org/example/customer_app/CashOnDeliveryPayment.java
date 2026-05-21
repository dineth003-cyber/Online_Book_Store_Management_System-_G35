package org.example.customer_app;

// OOP: POLYMORPHISM - same processPayment() call, completely different behaviour
public class CashOnDeliveryPayment extends PaymentProcessor {
    private String deliveryAddress;

    public CashOnDeliveryPayment(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public boolean processPayment(double amount) {
        if (deliveryAddress == null || deliveryAddress.isBlank()) {
            isSuccessful = false;
            return false;
        }
        transactionId = generateTransactionId();
        isSuccessful  = true;
        System.out.println("[COD] Order Rs." + amount + " confirmed. REF: " + transactionId);
        return true;
    }

    @Override
    public String getPaymentType()      { return "Cash on Delivery"; }
    public String getDeliveryAddress()  { return deliveryAddress; }
}
