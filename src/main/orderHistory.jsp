<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.customer_app.*,java.util.List" %>
<%
    String customerId = (String) session.getAttribute("customerId");
    if (customerId == null) { response.sendRedirect("login.jsp"); return; }
    OrderManager orderManager = new OrderManager();
    List<Order> orders = orderManager.getOrdersByCustomer(customerId);
    String viewOrderId = request.getParameter("orderId");
    Order viewOrder = (viewOrderId != null) ? orderManager.getOrderById(viewOrderId) : null;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Orders – PageTurn Bookstore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background-color: #f8f5f0; }
        .navbar { background-color: #3d2b1f; }
        .order-card { background:#fff; border:none; border-radius:12px; box-shadow:0 2px 8px rgba(0,0,0,0.08); padding:1.2rem; margin-bottom:1rem; }
        .status-PENDING    { background:#fff3cd; color:#856404; }
        .status-PROCESSING { background:#cfe2ff; color:#0a58ca; }
        .status-SHIPPED    { background:#d1ecf1; color:#0c5460; }
        .status-DELIVERED  { background:#d4edda; color:#155724; }
        .status-CANCELLED  { background:#f8d7da; color:#721c24; }
        .badge-status { font-size:.8rem; padding:4px 10px; border-radius:20px; font-weight:600; }
        .tracker { display:flex; justify-content:space-between; position:relative; margin:2rem 0; }
        .tracker::before { content:''; position:absolute; top:18px; left:0; right:0; height:3px; background:#dee2e6; z-index:0; }
        .step { text-align:center; position:relative; z-index:1; flex:1; }
        .step-icon { width:36px; height:36px; border-radius:50%; background:#dee2e6; display:flex; align-items:center; justify-content:center; margin:0 auto 6px; color:#fff; }
        .step.done   .step-icon { background:#198754; }
        .step.active .step-icon { background:#0d6efd; }
        .step-label { font-size:.75rem; color:#6c757d; }
        .step.done .step-label, .step.active .step-label { color:#212529; font-weight:600; }
    </style>
</head>
<body>
<nav class="navbar navbar-dark px-4 py-3">
    <a class="navbar-brand fw-bold fs-4" href="index.jsp">📚 PageTurn</a>
    <div class="d-flex gap-3">
        <a href="catalog.jsp"      class="text-white text-decoration-none">Catalog</a>
        <a href="cart.jsp"         class="text-white text-decoration-none"><i class="bi bi-cart3"></i> Cart</a>
        <a href="orderHistory.jsp" class="text-white text-decoration-none">My Orders</a>
    </div>
</nav>
<div class="container py-5">
<% if (viewOrder != null) { %>
    <a href="orderHistory.jsp" class="btn btn-sm btn-outline-secondary mb-4">← Back to Orders</a>
    <h4 class="fw-bold">Tracking: <span class="text-muted"><%= viewOrder.getOrderId() %></span></h4>
    <%
        String[] statuses = {"PENDING","PROCESSING","SHIPPED","DELIVERED"};
        String current = viewOrder.getStatus().name();
        int currentIdx = 0;
        for (int i = 0; i < statuses.length; i++) { if (statuses[i].equals(current)) { currentIdx = i; break; } }
    %>
    <div class="tracker">
        <% String[] labels={"Order Placed","Processing","Shipped","Delivered"};
           String[] icons={"bi-bag-check","bi-gear","bi-truck","bi-house-check"};
           for (int i=0; i<statuses.length; i++) {
               String cls = (i<currentIdx)?"done":(i==currentIdx?"active":""); %>
        <div class="step <%= cls %>">
            <div class="step-icon"><i class="bi <%= icons[i] %>"></i></div>
            <div class="step-label"><%= labels[i] %></div>
        </div>
        <% } %>
    </div>
    <% if (viewOrder.getStatus() == OrderStatus.CANCELLED) { %>
        <div class="alert alert-danger"><i class="bi bi-x-circle"></i> This order has been cancelled.</div>
    <% } %>
    <div class="order-card">
        <div class="row mb-3">
            <div class="col-md-6">
                <p><strong>Payment:</strong> <%= viewOrder.getPaymentMethod() %></p>
                <p><strong>Ship to:</strong> <%= viewOrder.getShippingAddress() %></p>
            </div>
            <div class="col-md-6 text-md-end">
                <p><strong>Status:</strong> <span class="badge-status status-<%= viewOrder.getStatus() %>"><%= viewOrder.getStatus() %></span></p>
                <p class="fs-5"><strong>Total: Rs.<%= String.format("%.2f", viewOrder.getTotalAmount()) %></strong></p>
            </div>
        </div>
        <hr>
        <table class="table table-sm">
            <thead class="table-light"><tr><th>Book</th><th>Price</th><th>Qty</th><th>Subtotal</th></tr></thead>
            <tbody>
            <% for (OrderItem oi : viewOrder.getItems()) { %>
                <tr>
                    <td><%= oi.getTitle() %></td>
                    <td>Rs.<%= String.format("%.2f", oi.getPriceAtPurchase()) %></td>
                    <td><%= oi.getQuantity() %></td>
                    <td>Rs.<%= String.format("%.2f", oi.getSubtotal()) %></td>
                </tr>
            <% } %>
            </tbody>
        </table>
        <% if (viewOrder.getStatus()==OrderStatus.PENDING || viewOrder.getStatus()==OrderStatus.PROCESSING) { %>
        <form action="OrderServlet" method="post" class="text-end mt-3"
              onsubmit="return confirm('Cancel this order?')">
            <input type="hidden" name="action"  value="cancel">
            <input type="hidden" name="orderId" value="<%= viewOrder.getOrderId() %>">
            <button type="submit" class="btn btn-outline-danger btn-sm">Cancel Order</button>
        </form>
        <% } %>
    </div>
<% } else { %>
    <h2 class="fw-bold mb-4">📦 My Orders</h2>
    <% if (orders.isEmpty()) { %>
        <div class="text-center py-5">
            <i class="bi bi-inbox" style="font-size:3rem;color:#ccc;"></i>
            <p class="mt-3 text-muted">No orders yet.</p>
            <a href="catalog.jsp" class="btn btn-primary">Start Shopping</a>
        </div>
    <% } else { for (Order o : orders) { %>
        <div class="order-card">
            <div class="d-flex justify-content-between flex-wrap gap-2">
                <div>
                    <h6 class="fw-bold mb-1"><%= o.getOrderId() %></h6>
                    <small class="text-muted"><%= o.getItems().size() %> item(s) · <%= o.getPaymentMethod() %></small>
                </div>
                <div class="text-end">
                    <span class="badge-status status-<%= o.getStatus() %>"><%= o.getStatus() %></span>
                    <div class="fw-bold mt-1">Rs.<%= String.format("%.2f", o.getTotalAmount()) %></div>
                </div>
            </div>
            <div class="mt-3">
                <a href="orderHistory.jsp?orderId=<%= o.getOrderId() %>"
                   class="btn btn-sm btn-outline-primary"><i class="bi bi-geo-alt"></i> Track Order</a>
            </div>
        </div>
    <% } } %>
<% } %>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
