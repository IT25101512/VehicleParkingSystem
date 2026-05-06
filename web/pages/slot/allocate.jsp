<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Allocate Slot — ParkEase</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav>
  <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-brand">&#9646; <span>Admin</span>Portal</a>
  <a href="${pageContext.request.contextPath}/slot/view" style="color: var(--text);">Manage Slots</a>
  <div class="nav-right">
    <a href="${pageContext.request.contextPath}/admin/logout" class="btn btn-outline btn-sm">Logout</a>
  </div>
</nav>

<div class="container">
  <div class="page-header">
    <h1>Allocate Slot</h1>
    <p>Manually reserve a slot or assign to a vehicle</p>
  </div>

  <div class="card w-half">
    <c:if test="${not empty error}">
      <div class="alert alert-error">${error}</div>
    </c:if>
    <c:if test="${not empty success}">
      <div class="alert alert-success">${success}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/slot/allocate" method="post">
      <div class="form-group">
        <label for="slotId">Slot ID</label>
        <input type="text" id="slotId" name="slotId" required placeholder="e.g. A-101">
      </div>
      <div class="form-group">
        <label for="licensePlate">Vehicle License Plate</label>
        <input type="text" id="licensePlate" name="licensePlate" placeholder="Optional...">
      </div>
      <div class="mt-3">
        <button type="submit" class="btn btn-primary">Confirm Allocation</button>
      </div>
    </form>
  </div>
</div>
</body>
</html>
