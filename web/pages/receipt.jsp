<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Payment Receipt — ParkEase</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container" style="display: flex; align-items: center; justify-content: center; min-height: 100vh;">
  <div class="receipt">
    <div class="receipt-header">
      <h2 style="color: var(--text); margin-bottom: 0.5rem;">&#9646; ParkEase</h2>
      <p style="color: var(--muted); font-size: 0.85rem;">Digital Parking Receipt</p>
      <div style="margin-top: 0.5rem;"><span class="badge badge-success">PAID</span></div>
    </div>

    <div class="receipt-row mt-2">
      <span class="key">Receipt ID</span>
      <span class="val">${payment.paymentId}</span>
    </div>
    <div class="receipt-row">
      <span class="key">Transaction ID</span>
      <span class="val">${payment.transactionId}</span>
    </div>
    <div class="receipt-row">
      <span class="key">Payment Method</span>
      <span class="val">${payment.paymentMethod}</span>
    </div>
    <div class="receipt-row">
      <span class="key">Date &amp; Time</span>
      <span class="val">${payment.paymentDate}</span>
    </div>

    <div class="receipt-total">
      <span class="key">Total Paid</span>
      <span class="val">$${payment.amount}</span>
    </div>

    <div class="mt-3" style="text-align: center;">
      <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-outline btn-sm">Return to Dashboard</a>
    </div>
  </div>
</div>
</body>
</html>
