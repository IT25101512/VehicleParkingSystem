<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Vehicle Exit — ParkEase</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav>
  <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-brand">&#9646; <span>Admin</span>Portal</a>
  <a href="${pageContext.request.contextPath}/slot/view">Manage Slots</a>
  <div class="nav-right">
    <a href="${pageContext.request.contextPath}/admin/logout" class="btn btn-outline btn-sm">Logout</a>
  </div>
</nav>

<div class="container">
  <div class="page-header">
    <h1>Process Vehicle Exit</h1>
    <p>Record exit and calculate parking fee</p>
  </div>

  <div class="card w-half">
    <c:if test="${not empty error}">
      <div class="alert alert-error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/transaction/exit" method="post">
      <div class="form-group">
        <label for="transactionId">Transaction ID</label>
        <input type="text" id="transactionId" name="transactionId" placeholder="TRX-12345..." required>
        <small class="text-muted">Enter the active transaction ID.</small>
      </div>
      <div class="mt-3">
        <button type="submit" class="btn btn-danger">Process Exit &amp; Payment</button>
      </div>
    </form>
  </div>
</div>
</body>
</html>
