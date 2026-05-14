<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Process Payment — ParkEase</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav>
  <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-brand">&#9646; <span>Admin</span>Portal</a>
  <div class="nav-right">
    <a href="${pageContext.request.contextPath}/admin/logout" class="btn btn-outline btn-sm">Logout</a>
  </div>
</nav>

<div class="container">
  <div class="page-header">
    <h1>Process Payment</h1>
    <p>Complete parking fee transaction</p>
  </div>

  <div class="card w-half">
    <div class="stat-card purple" style="margin-bottom: 1.5rem;">
      <div class="stat-label">Total Amount Due</div>
      <div class="stat-value">$${fee}</div>
    </div>

    <%-- POST to /payment/process — matches servlet doPost("/process") --%>
    <form action="${pageContext.request.contextPath}/payment/process" method="post">
      <input type="hidden" name="transactionId" value="${transactionId}">
      <input type="hidden" name="amount"         value="${fee}">

      <div class="form-group">
        <label for="paymentMethod">Payment Method</label>
        <%-- name="paymentMethod" must match req.getParameter("paymentMethod") in servlet --%>
        <select id="paymentMethod" name="paymentMethod" required>
          <option value="CASH">Cash</option>
          <option value="CARD">Credit Card</option>
          <option value="ONLINE">Mobile / Online</option>
        </select>
      </div>

      <div class="mt-3">
        <button type="submit" class="btn btn-success" style="width:100%; justify-content:center;">
          Complete Payment
        </button>
      </div>
    </form>
  </div>
</div>
</body>
</html>