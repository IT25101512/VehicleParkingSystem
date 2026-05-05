<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Search Vehicle — ParkEase</title>
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
    <h1>Search Vehicle</h1>
    <p>Find vehicle details by license plate</p>
  </div>

  <div class="card w-half">
    <form action="${pageContext.request.contextPath}/vehicle/search" method="post" class="flex-gap">
      <div class="form-group" style="flex-grow: 1; margin-bottom: 0;">
        <input type="text" name="plate" placeholder="Enter License Plate..." required>
      </div>
      <button type="submit" class="btn btn-primary">Search</button>
    </form>
  </div>

  <c:if test="${not empty error}">
    <div class="alert alert-error">${error}</div>
  </c:if>

  <c:if test="${not empty result}">
    <div class="card">
      <div class="card-title">Vehicle Found</div>
      <div class="stat-grid" style="margin-bottom: 0;">
        <div class="stat-card">
          <div class="stat-label">Plate Number</div>
          <div class="stat-value" style="font-size: 1.5rem;">${result.plateNumber}</div>
        </div>
        <div class="stat-card">
          <div class="stat-label">Type</div>
          <div class="stat-value" style="font-size: 1.5rem;">${result.type}</div>
        </div>
        <div class="stat-card">
          <div class="stat-label">Model</div>
          <div class="stat-value" style="font-size: 1.5rem;">${result.model}</div>
        </div>
        <div class="stat-card">
          <div class="stat-label">Color</div>
          <div class="stat-value" style="font-size: 1.5rem;">${result.color}</div>
        </div>
      </div>
    </div>
  </c:if>
</div>
</body>
</html>
