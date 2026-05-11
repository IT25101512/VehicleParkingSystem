<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>My Vehicles — ParkEase</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav>
  <a href="${pageContext.request.contextPath}/index.jsp" class="nav-brand">&#9646; <span>Park</span>Ease</a>
  <a href="${pageContext.request.contextPath}/vehicle/list" style="color: var(--text);">My Vehicles</a>
  <a href="${pageContext.request.contextPath}/transaction/history">History</a>
  <div class="nav-right">
    <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
    <a href="${pageContext.request.contextPath}/user/logout" class="btn btn-outline btn-sm">Logout</a>
  </div>
</nav>

<div class="container">
  <div class="page-header flex-between">
    <div>
      <h1>My Vehicles</h1>
      <p>Vehicles registered to your account</p>
    </div>
    <a href="${pageContext.request.contextPath}/vehicle/add" class="btn btn-primary">+ Add Vehicle</a>
  </div>

  <c:if test="${not empty success}">
    <div class="alert alert-success">${success}</div>
  </c:if>

  <div class="card table-wrap">
    <table>
      <thead>
        <tr>
          <th>Plate Number</th>
          <th>Type</th>
          <th>Model</th>
          <th>Color</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${empty vehicles}">
            <tr>
              <td colspan="5" style="text-align: center; color: var(--muted); padding: 2rem;">No vehicles registered yet.</td>
            </tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="v" items="${vehicles}">
              <tr>
                <td class="text-mono" style="font-weight: 600;">${v.plateNumber}</td>
                <td><span class="badge badge-info">${v.type}</span></td>
                <td>${v.model}</td>
                <td>${v.color}</td>
                <td>
                  <a href="${pageContext.request.contextPath}/vehicle/delete?id=${v.vehicleId}" class="btn btn-outline btn-sm" onclick="return confirm('Are you sure you want to delete this vehicle?');" style="color: var(--danger); border-color: var(--danger);">Delete</a>
                </td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </tbody>
    </table>
  </div>
</div>
</body>
</html>
