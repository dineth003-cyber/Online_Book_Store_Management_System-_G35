package org.example.customer_app;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// OOP: ENCAPSULATION - file I/O is hidden; callers use clean methods
public class OrderManager {

    private static final String ORDERS_FILE = "orders.txt";

    // ── CREATE ────────────────────────────────────────────────────────────────
    public Order placeOrder(Cart cart, DiscountStrategy discountStrategy,
                            PaymentProcessor paymentProcessor, String shippingAddress) {
        if (cart.isEmpty()) {
            System.out.println("Cannot place order: cart is empty.");
            return null;
        }

        double discount = discountStrategy.calculateDiscount(cart.getTotalPrice());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : cart.getItems()) {
            orderItems.add(new OrderItem(ci.getBookId(), ci.getTitle(),
                                         ci.getPrice(), ci.getQuantity()));
        }

        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Order order = new Order(orderId, cart.getCustomerId(), orderItems,
                                discount, paymentProcessor.getPaymentType(), shippingAddress);

        boolean paid = paymentProcessor.processPayment(order.getTotalAmount());
        if (!paid) {
            System.out.println("Payment failed. Order not placed.");
            return null;
        }

        order.setStatus(OrderStatus.PROCESSING);
        saveOrderToFile(order);
        cart.clearCart();

        System.out.println("Order placed: " + orderId);
        return order;
    }

    // ── READ ──────────────────────────────────────────────────────────────────
    public List<Order> getAllOrders() {
        return loadOrdersFromFile();
    }

    public Order getOrderById(String orderId) {
        for (Order order : loadOrdersFromFile()) {
            if (order.getOrderId().equals(orderId)) return order;
        }
        return null;
    }

    public List<Order> getOrdersByCustomer(String customerId) {
        List<Order> result = new ArrayList<>();
        for (Order order : loadOrdersFromFile()) {
            if (order.getCustomerId().equals(customerId)) result.add(order);
        }
        return result;
    }

    // ── UPDATE ────────────────────────────────────────────────────────────────
    public boolean updateOrderStatus(String orderId, OrderStatus newStatus) {
        List<Order> orders = loadOrdersFromFile();
        boolean found = false;
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                order.setStatus(newStatus);
                found = true;
                break;
            }
        }
        if (found) saveAllOrdersToFile(orders);
        return found;
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    public boolean cancelOrder(String orderId) {
        List<Order> orders = loadOrdersFromFile();
        boolean found = false;
        for (Order order : orders) {
            if (order.getOrderId().equals(orderId)) {
                if (order.getStatus() == OrderStatus.SHIPPED ||
                    order.getStatus() == OrderStatus.DELIVERED) {
                    System.out.println("Cannot cancel: order already " + order.getStatus());
                    return false;
                }
                order.setStatus(OrderStatus.CANCELLED);
                found = true;
                break;
            }
        }
        if (found) saveAllOrdersToFile(orders);
        return found;
    }

    // ── File I/O (private) ────────────────────────────────────────────────────
    private void saveOrderToFile(Order order) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE, true))) {
            writer.write(order.toFileString());
        } catch (IOException e) {
            System.out.println("Error saving order: " + e.getMessage());
        }
    }

    private void saveAllOrdersToFile(List<Order> orders) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE, false))) {
            for (Order order : orders) writer.write(order.toFileString());
        } catch (IOException e) {
            System.out.println("Error writing orders: " + e.getMessage());
        }
    }

    private List<Order> loadOrdersFromFile() {
        List<Order> orders = new ArrayList<>();
        File file = new File(ORDERS_FILE);
        if (!file.exists()) return orders;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String orderId = null, customerId = null, paymentMethod = null,
                   shippingAddress = null, statusStr = null;
            double discount = 0;
            List<OrderItem> items = new ArrayList<>();
            boolean readingItems = false;

            while ((line = reader.readLine()) != null) {
                if      (line.startsWith("ORDER_ID:"))    orderId         = line.substring(9);
                else if (line.startsWith("CUSTOMER_ID:")) customerId      = line.substring(12);
                else if (line.startsWith("STATUS:"))      statusStr       = line.substring(7);
                else if (line.startsWith("PAYMENT:"))     paymentMethod   = line.substring(8);
                else if (line.startsWith("ADDRESS:"))     shippingAddress = line.substring(8);
                else if (line.startsWith("DISCOUNT:"))    discount        = Double.parseDouble(line.substring(9));
                else if (line.equals("ITEMS_START"))      readingItems    = true;
                else if (line.equals("ITEMS_END"))        readingItems    = false;
                else if (readingItems && !line.isBlank()) items.add(OrderItem.fromFileString(line));
                else if (line.equals("---")) {
                    if (orderId != null) {
                        Order o = new Order(orderId, customerId, items,
                                            discount, paymentMethod, shippingAddress);
                        o.setStatus(OrderStatus.valueOf(statusStr));
                        orders.add(o);
                    }
                    orderId = customerId = paymentMethod = shippingAddress = statusStr = null;
                    discount = 0;
                    items = new ArrayList<>();
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading orders: " + e.getMessage());
        }
        return orders;
    }
}
