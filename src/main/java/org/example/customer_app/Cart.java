package org.example.customer_app;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String customerId;
    private List<CartItem> items;

    public Cart(String customerId) {
        this.customerId = customerId;
        this.items      = new ArrayList<>();
    }

    public void addItem(CartItem newItem) {
        for (CartItem item : items) {
            if (item.getBookId().equals(newItem.getBookId())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }
        items.add(newItem);
    }

    public void removeItem(String bookId) {
        items.removeIf(item -> item.getBookId().equals(bookId));
    }

    public void updateQuantity(String bookId, int newQuantity) {
        if (newQuantity <= 0) { removeItem(bookId); return; }
        for (CartItem item : items) {
            if (item.getBookId().equals(bookId)) {
                item.setQuantity(newQuantity);
                return;
            }
        }
    }

    public void clearCart() { items.clear(); }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) total += item.getSubtotal();
        return total;
    }

    public int getTotalItems() {
        int count = 0;
        for (CartItem item : items) count += item.getQuantity();
        return count;
    }

    public boolean isEmpty() { return items.isEmpty(); }
    public String getCustomerId() { return customerId; }
    public List<CartItem> getItems() { return new ArrayList<>(items); }
}
