<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.customer_app.Cart, org.example.customer_app.CartItem" %>
<%
    Cart cart = (Cart) session.getAttribute("cart");
    if (cart == null || cart.isEmpty()) { response.sendRedirect("cart.jsp"); return; }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Checkout – PageTurn Bookstore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background-color: #f8f5f0; }
        .navbar { background-color: #3d2b1f; }
        .btn-primary { background-color: #c0392b; border-color: #c0392b; }
        .section-card { background:#fff; border-radius:12px; box-shadow:0 2px 8px rgba(0,0,0,0.08); padding:1.5rem; margin-bottom:1.5rem; }
        #cardFields { display:none; }
    </style>
</head>
<body>
<nav class="navbar navbar-dark px-4 py-3">
    <a class="navbar-brand fw-bold fs-4" href="index.jsp">📚 PageTurn</a>
</nav>
<div class="container py-5">
    <h2 class="fw-bold mb-4">💳 Checkout</h2>
    <form action="CheckoutServlet" method="post">
    <div class="row g-4">
        <div class="col-lg-8">
            <div class="section-card">
                <h5 class="fw-bold mb-3"><i class="bi bi-geo-alt"></i> Shipping Address</h5>
                <div class="mb-3"><label class="form-label">Full Name</label>
                    <input type="text" name="fullName" class="form-control" required></div>
                <div class="mb-3"><label class="form-label">Street Address</label>
                    <input type="text" name="street" class="form-control" required></div>
                <div class="row">
                    <div class="col-md-6 mb-3"><label class="form-label">City</label>
                        <input type="text" name="city" class="form-control" required></div>
                    <div class="col-md-6 mb-3"><label class="form-label">Postal Code</label>
                        <input type="text" name="postalCode" class="form-control" required></div>
                </div>
            </div>
            <div class="section-card">
                <h5 class="fw-bold mb-3"><i class="bi bi-tag"></i> Discount</h5>
                <div class="mb-3"><label class="form-label">Discount Type</label>
                    <select name="discountType" class="form-select">
                        <option value="none">No Discount</option>
                        <option value="holiday">Holiday Sale (15% off)</option>
                        <option value="loyalty">Loyalty Points</option>
                    </select></div>
                <div class="mb-2"><label class="form-label">Loyalty Points to Redeem</label>
                    <input type="number" name="loyaltyPoints" class="form-control" value="0" min="0">
                    <small class="text-muted">1 point = Rs.0.50</small></div>
            </div>
            <div class="section-card">
                <h5 class="fw-bold mb-3"><i class="bi bi-wallet2"></i> Payment</h5>
                <div class="d-flex gap-4 mb-4">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="paymentMethod"
                               value="card" onclick="toggleCard(true)" checked>
                        <label class="form-check-label">💳 Credit/Debit Card</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="paymentMethod"
                               value="cod" onclick="toggleCard(false)">
                        <label class="form-check-label">💵 Cash on Delivery</label>
                    </div>
                </div>
                <div id="cardFields">
                    <div class="mb-3"><label class="form-label">Card Number</label>
                        <input type="text" name="cardNumber" class="form-control" maxlength="16"></div>
                    <div class="mb-3"><label class="form-label">Cardholder Name</label>
                        <input type="text" name="cardHolder" class="form-control"></div>
                    <div class="row">
                        <div class="col-md-6 mb-3"><label class="form-label">Expiry</label>
                            <input type="text" name="expiry" class="form-control" placeholder="MM/YY"></div>
                        <div class="col-md-6 mb-3"><label class="form-label">CVV</label>
                            <input type="password" name="cvv" class="form-control" maxlength="3"></div>
                    </div>
                </div>
                <div id="codNotice" style="display:none">
                    <div class="alert alert-info">Pay in cash when your order arrives.</div>
                </div>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="section-card">
                <h5 class="fw-bold mb-3">Order Summary</h5>
                <% for (CartItem item : cart.getItems()) { %>
                <div class="d-flex justify-content-between mb-2 small">
                    <span><%= item.getTitle() %> × <%= item.getQuantity() %></span>
                    <span>Rs.<%= String.format("%.2f", item.getSubtotal()) %></span>
                </div>
                <% } %>
                <hr>
                <div class="d-flex justify-content-between fw-bold fs-5 mb-4">
                    <span>Total</span>
                    <span>Rs.<%= String.format("%.2f", cart.getTotalPrice()) %></span>
                </div>
                <button type="submit" class="btn btn-primary w-100 py-2">
                    <i class="bi bi-bag-check"></i> Place Order
                </button>
                <a href="cart.jsp" class="btn btn-outline-secondary w-100 mt-2">Back to Cart</a>
            </div>
        </div>
    </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
function toggleCard(show) {
    document.getElementById('cardFields').style.display  = show ? 'block' : 'none';
    document.getElementById('codNotice').style.display   = show ? 'none'  : 'block';
}
toggleCard(true);
</script>
</body>
</html>
