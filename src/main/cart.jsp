<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.customer_app.Cart, org.example.customer_app.CartItem, java.util.List" %>
<%
    Cart cart = (Cart) session.getAttribute("cart");
    List<CartItem> items = (cart != null) ? cart.getItems() : new java.util.ArrayList<>();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart – PageTurn Bookstore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background-color: #f8f5f0; }
        .navbar { background-color: #3d2b1f; }
        .btn-primary { background-color: #c0392b; border-color: #c0392b; }
        .cart-card { border:none; border-radius:12px; box-shadow:0 2px 8px rgba(0,0,0,0.08); }
        .qty-input { width:65px; text-align:center; }
    </style>
</head>
<body>
<nav class="navbar navbar-dark px-4 py-3">
    <a class="navbar-brand fw-bold fs-4" href="index.jsp">📚 PageTurn</a>
    <div class="d-flex gap-3">
        <a href="catalog.jsp" class="text-white text-decoration-none">Catalog</a>
        <a href="cart.jsp"    class="text-white text-decoration-none"><i class="bi bi-cart3"></i> Cart</a>
        <a href="orderHistory.jsp" class="text-white text-decoration-none">My Orders</a>
    </div>
</nav>
<div class="container py-5">
    <h2 class="fw-bold mb-4">🛒 Your Shopping Cart</h2>
    <% if (items.isEmpty()) { %>
        <div class="text-center py-5">
            <i class="bi bi-cart-x" style="font-size:4rem;color:#ccc;"></i>
            <h5 class="mt-3 text-muted">Your cart is empty</h5>
            <a href="catalog.jsp" class="btn btn-primary mt-3">Browse Books</a>
        </div>
    <% } else { %>
    <div class="row g-4">
        <div class="col-lg-8">
            <div class="cart-card p-4 bg-white">
                <table class="table align-middle">
                    <thead class="table-light">
                        <tr><th>Book</th><th>Price</th><th>Qty</th><th>Subtotal</th><th></th></tr>
                    </thead>
                    <tbody>
                    <% for (CartItem item : items) { %>
                        <tr>
                            <td><strong><%= item.getTitle() %></strong></td>
                            <td>Rs.<%= String.format("%.2f", item.getPrice()) %></td>
                            <td>
                                <form action="CartServlet" method="post" class="d-flex gap-1">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="bookId" value="<%= item.getBookId() %>">
                                    <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1" class="form-control qty-input">
                                    <button type="submit" class="btn btn-sm btn-outline-secondary"><i class="bi bi-arrow-clockwise"></i></button>
                                </form>
                            </td>
                            <td class="fw-bold">Rs.<%= String.format("%.2f", item.getSubtotal()) %></td>
                            <td>
                                <form action="CartServlet" method="post">
                                    <input type="hidden" name="action" value="remove">
                                    <input type="hidden" name="bookId" value="<%= item.getBookId() %>">
                                    <button type="submit" class="btn btn-sm btn-outline-danger"><i class="bi bi-trash"></i></button>
                                </form>
                            </td>
                        </tr>
                    <% } %>
                    </tbody>
                </table>
                <form action="CartServlet" method="post" class="text-end">
                    <input type="hidden" name="action" value="clear">
                    <button type="submit" class="btn btn-outline-danger btn-sm"><i class="bi bi-trash3"></i> Clear Cart</button>
                </form>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="bg-white p-4 rounded-3 shadow-sm">
                <h5 class="fw-bold mb-3">Order Summary</h5>
                <div class="d-flex justify-content-between mb-2">
                    <span class="text-muted">Items (<%= cart.getTotalItems() %>)</span>
                    <span>Rs.<%= String.format("%.2f", cart.getTotalPrice()) %></span>
                </div>
                <div class="d-flex justify-content-between mb-2">
                    <span class="text-muted">Shipping</span><span class="text-success">Free</span>
                </div>
                <hr>
                <div class="d-flex justify-content-between fw-bold fs-5 mb-4">
                    <span>Total</span>
                    <span>Rs.<%= String.format("%.2f", cart.getTotalPrice()) %></span>
                </div>
                <a href="checkout.jsp" class="btn btn-primary w-100 py-2">Proceed to Checkout</a>
                <a href="catalog.jsp"  class="btn btn-outline-secondary w-100 mt-2">Continue Shopping</a>
            </div>
        </div>
    </div>
    <% } %>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
