package org.example.customer_app;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String          orderId;
    private String          customerId;
    private List<OrderItem> items;
    private double          totalAmount;
    private double          discountApplied;
    private OrderStatus     status;
    private String          paymentMethod;
    private String          shippingAddress;
    private LocalDateTime   orderDate;

    public Order(String orderId, String customerId, List<OrderItem> items,
                 double discountApplied, String paymentMethod, String shippingAddress) {
        this.orderId         = orderId;
        this.customerId      = customerId;
        this.items           = new ArrayList<>(items);
        this.discountApplied = discountApplied;
        this.paymentMethod   = paymentMethod;
        this.shippingAddress = shippingAddress;
        this.status          = OrderStatus.PENDING;
        this.orderDate       = LocalDateTime.now();
        this.totalAmount     = calculateTotal();
    }

    private double calculateTotal() {
        double subtotal = 0;
        for (OrderItem item : items) subtotal += item.getSubtotal();
        return Math.max(0, subtotal - discountApplied);
    }

    public String          getOrderId()         { return orderId; }
    public String          getCustomerId()      { return customerId; }
    public List<OrderItem> getItems()           { return new ArrayList<>(items); }
    public double          getTotalAmount()     { return totalAmount; }
    public double          getDiscountApplied() { return discountApplied; }
    public OrderStatus     getStatus()          { return status; }
    public String          getPaymentMethod()   { return paymentMethod; }
    public String          getShippingAddress() { return shippingAddress; }
    public LocalDateTime   getOrderDate()       { return orderDate; }
    public void setStatus(OrderStatus status)   { this.status = status; }

    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ORDER_ID:").append(orderId).append("\n");
        sb.append("CUSTOMER_ID:").append(customerId).append("\n");
        sb.append("DATE:").append(orderDate.format(DATE_FORMAT)).append("\n");
        sb.append("STATUS:").append(status).append("\n");
        sb.append("PAYMENT:").append(paymentMethod).append("\n");
        sb.append("ADDRESS:").append(shippingAddress).append("\n");
        sb.append("DISCOUNT:").append(discountApplied).append("\n");
        sb.append("TOTAL:").append(totalAmount).append("\n");
        sb.append("ITEMS_START\n");
        for (OrderItem item : items) sb.append(item.toFileString()).append("\n");
        sb.append("ITEMS_END\n");
        sb.append("---\n");
        return sb.toString();
    }
}
