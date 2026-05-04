<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>My Profile — ParkEase</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav>
  <a href="${pageContext.request.contextPath}/index.jsp" class="nav-brand">
    &#9646; <span>Park</span>Ease
  </a>
  <a href="${pageContext.request.contextPath}/vehicle/list">My Vehicles</a>
  <a href="${pageContext.request.contextPath}/transaction/history">History</a>
  <div class="nav-right">
    <a href="${pageContext.request.contextPath}/user/profile"
       style="color:var(--text);">Profile</a>
    <a href="${pageContext.request.contextPath}/user/logout"
       class="btn btn-outline btn-sm">Logout</a>
  </div>
</nav>

<div class="container">
  <div class="page-header">
    <h1>My Profile</h1>
    <p>Manage your account settings and personal information</p>
  </div>

  <div class="card w-half">
    <div class="card-title">Profile Information</div>

    <c:if test="${not empty success}">
      <div class="alert alert-success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
      <div class="alert alert-error">${error}</div>
    </c:if>

    <%--
      ${user} is set in request scope by both showProfile() and handleUpdate()
      in the servlet, so it is always fresh — not read from session scope.
    --%>
    <form action="${pageContext.request.contextPath}/user/update" method="post">
      <div class="form-group">
        <label for="name">Full Name</label>
        <input type="text" id="name" name="name"
               value="${user.name}" required>
      </div>
      <div class="form-group">
        <label for="email">Email</label>
        <%-- Disabled: not submitted, not changeable — email is immutable --%>
        <input type="email" id="email"
               value="${user.email}" disabled>
        <small class="text-muted">Email cannot be changed.</small>
      </div>
      <div class="form-group">
        <label for="phone">Phone Number</label>
        <input type="text" id="phone" name="phone"
               value="${user.phone}" required>
      </div>
      <div class="form-group">
        <label for="address">Address</label>
        <textarea id="address" name="address" rows="3"
                  required>${user.address}</textarea>
      </div>
      <div class="mt-3">
        <button type="submit" class="btn btn-primary">Save Changes</button>
      </div>
    </form>
  </div>
</div>
</body>
</html>