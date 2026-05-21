<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.customer_app.*,java.util.List,java.util.ArrayList" %>
<%
    String role = (String) session.getAttribute("role");
    if (!"admin".equals(role)) { response.sendRedirect("login.jsp"); return; }
    OrderManager orderManager = new OrderManager();
    List<Order> allOrders = orderManager.getAllOrders();
    String filterStatus = request.getParameter("status");
    List<Order> displayOrders = new ArrayList<>();
    for (Order o : allOrders) {
        if (filterStatus==null || filterStatus.isEmpty() || o.getStatus().name().equals(filterStatus))
            displayOrders.add(o);
    }
    int cntP=0,cntPr=0,cntS=0,cntD=0,cntC=0;
    for (Order o : allOrders) {
        switch(o.getStatus()){
            case PENDING:    cntP++;  break;
            case PROCESSING: cntPr++; break;
            case SHIPPED:    cntS++;  break;
            case DELIVERED:  cntD++;  break;
            case CANCELLED:  cntC++;  break;
        }
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin – Orders</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background:#1a1a2e; color:#e0e0e0; }
        .sidebar { width:220px; min-height:100vh; background:#16213e; padding:1.5rem 1rem; position:fixed; }
        .sidebar .brand { color:#e94560; font-size:1.3rem; font-weight:700; margin-bottom:2rem; }
        .sidebar a { display:block; color:#aaa; text-decoration:none; padding:8px 12px; border-radius:8px; margin-bottom:4px; }
        .sidebar a:hover, .sidebar a.active { background:#0f3460; color:#fff; }
        .main { margin-left:220px; padding:2rem; }
        .stat-card { background:#16213e; border-radius:12px; padding:1.2rem; text-align:center; }
        .stat-card .num { font-size:2rem; font-weight:700; }
        .status-PENDING    {background:#fff3cd;color:#856404;}
        .status-PROCESSING {background:#cfe2ff;color:#0a58ca;}
        .status-SHIPPED    {background:#d1ecf1;color:#0c5460;}
        .status-DELIVERED  {background:#d4edda;color:#155724;}
        .status-CANCELLED  {background:#f8d7da;color:#721c24;}
        .badge-status { font-size:.75rem; padding:3px 10px; border-radius:20px; font-weight:600; }
    </style>
</head>
<body>
<div class="sidebar">
    <div class="brand">📚 PageTurn<br><small style="font-size:.75rem;color:#888;">Admin</small></div>
    <a href="adminDashboard.jsp">🏠 Dashboard</a>
    <a href="adminOrders.jsp" class="active">📦 Orders</a>
    <a href="adminBooks.jsp">📖 Books</a>
    <a href="adminCustomers.jsp">👥 Customers</a>
    <a href="logout.jsp" style="color:#e94560;margin-top:2rem;">⬅ Logout</a>
</div>
<div class="main">
    <h3 class="fw-bold mb-4">Order Fulfillment Panel</h3>
    <div class="row g-3 mb-4">
        <div class="col"><div class="stat-card"><div class="num text-warning"><%= cntP %></div><small class="text-muted">Pending</small></div></div>
        <div class="col"><div class="stat-card"><div class="num text-primary"><%= cntPr %></div><small class="text-muted">Processing</small></div></div>
        <div class="col"><div class="stat-card"><div class="num text-info"><%= cntS %></div><small class="text-muted">Shipped</small></div></div>
        <div class="col"><div class="stat-card"><div class="num text-success"><%= cntD %></div><small class="text-muted">Delivered</small></div></div>
        <div class="col"><div class="stat-card"><div class="num text-danger"><%= cntC %></div><small class="text-muted">Cancelled</small></div></div>
    </div>
    <div class="d-flex gap-2 mb-3 flex-wrap">
        <% String[] sl={"","PENDING","PROCESSING","SHIPPED","DELIVERED","CANCELLED"};
           String[] ll={"All","Pending","Processing","Shipped","Delivered","Cancelled"};
           for(int i=0;i<sl.length;i++){
               String ac=(filterStatus==null&&sl[i].isEmpty())||(sl[i].equals(filterStatus))?"btn-light":"btn-outline-light"; %>
        <a href="adminOrders.jsp?status=<%= sl[i] %>" class="btn btn-sm <%= ac %>"><%= ll[i] %></a>
        <% } %>
    </div>
    <div class="table-responsive">
        <table class="table table-dark table-hover align-middle">
            <thead><tr><th>Order ID</th><th>Customer</th><th>Items</th><th>Total</th><th>Payment</th><th>Status</th><th>Update</th><th>Action</th></tr></thead>
            <tbody>
            <% if(displayOrders.isEmpty()){%>
                <tr><td colspan="8" class="text-center text-muted py-4">No orders found.</td></tr>
            <%} else { for(Order o : displayOrders){%>
                <tr>
                    <td><code><%= o.getOrderId() %></code></td>
                    <td><%= o.getCustomerId() %></td>
                    <td><%= o.getItems().size() %></td>
                    <td>Rs.<%= String.format("%.2f",o.getTotalAmount()) %></td>
                    <td><small><%= o.getPaymentMethod() %></small></td>
                    <td><span class="badge-status status-<%= o.getStatus() %>"><%= o.getStatus() %></span></td>
                    <td>
                        <form action="OrderServlet" method="post" class="d-flex gap-1">
                            <input type="hidden" name="action" value="updateStatus">
                            <input type="hidden" name="orderId" value="<%= o.getOrderId() %>">
                            <select name="newStatus" class="form-select form-select-sm" style="max-width:140px;">
                                <% for(OrderStatus s:OrderStatus.values()){%>
                                <option value="<%= s %>" <%= s==o.getStatus()?"selected":"" %>><%= s %></option>
                                <%}%>
                            </select>
                            <button type="submit" class="btn btn-sm btn-primary">✓</button>
                        </form>
                    </td>
                    <td>
                        <a href="orderHistory.jsp?orderId=<%= o.getOrderId() %>" class="btn btn-sm btn-outline-light"><i class="bi bi-eye"></i></a>
                        <% if(o.getStatus()==OrderStatus.PENDING||o.getStatus()==OrderStatus.PROCESSING){%>
                        <form action="OrderServlet" method="post" class="d-inline" onsubmit="return confirm('Cancel?')">
                            <input type="hidden" name="action" value="cancel">
                            <input type="hidden" name="orderId" value="<%= o.getOrderId() %>">
                            <button class="btn btn-sm btn-outline-danger"><i class="bi bi-x-circle"></i></button>
                        </form>
                        <%}%>
                    </td>
                </tr>
            <%}}%>
            </tbody>
        </table>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
