<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Register Vehicle — ParkEase</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav>
  <a href="${pageContext.request.contextPath}/index.jsp" class="nav-brand">&#9646; <span>Park</span>Ease</a>
  <a href="${pageContext.request.contextPath}/vehicle/list">My Vehicles</a>
  <a href="${pageContext.request.contextPath}/transaction/history">History</a>
  <div class="nav-right">
    <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
    <a href="${pageContext.request.contextPath}/user/logout" class="btn btn-outline btn-sm">Logout</a>
  </div>
</nav>

<div class="container">
  <div class="page-header flex-between">
    <div>
      <h1>Register Vehicle</h1>
      <p>Add a new vehicle to your account</p>
    </div>
    <a href="${pageContext.request.contextPath}/vehicle/list" class="btn btn-outline">Back to List</a>
  </div>

  <div class="card w-half">
    <div class="card-title">Vehicle Details</div>
    
    <c:if test="${not empty error}">
      <div class="alert alert-error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/vehicle/add" method="post">
      <div class="form-group">
        <label for="plateNumber">Plate Number</label>
        <input type="text" id="plateNumber" name="plateNumber" required placeholder="ABC-1234">
      </div>
      <div class="form-group">
        <label for="type">Vehicle Type</label>
        <select id="type" name="type" required>
          <option value="CAR">Car</option>
          <option value="MOTORCYCLE">Motorcycle</option>
          <option value="VAN">Van</option>
          <option value="TRUCK">Truck</option>
        </select>
      </div>
      <div class="form-group">
        <label for="model">Model</label>
        <input type="text" id="model" name="model" placeholder="e.g. Toyota Corolla" required>
      </div>
      <div class="form-group">
        <label for="color">Color</label>
        <input type="text" id="color" name="color" placeholder="e.g. Red" required>
      </div>
      <div class="mt-3">
        <button type="submit" class="btn btn-primary">Register Vehicle</button>
      </div>
    </form>
  </div>
</div>
</body>
</html>
