<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Admin Login — ParkEase</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <style>
    .admin-badge {
      background: rgba(124, 58, 237, 0.1);
      color: var(--accent2);
      padding: 0.2rem 0.6rem;
      border-radius: 4px;
      font-size: 0.7rem;
      font-weight: 700;
      text-transform: uppercase;
      letter-spacing: 0.1em;
      margin-bottom: 1rem;
      display: inline-block;
    }
  </style>
</head>
<body>
<div class="auth-wrap">
  <div class="auth-card">
    <div class="auth-logo">
      <div class="admin-badge">Admin Portal</div>
      <h1>&#9646; ParkEase</h1>
      <p>Secure Administrator Login</p>
    </div>

    <c:if test="${not empty error}">
      <div class="alert alert-error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/admin/login" method="post">
      <div class="form-group">
        <label for="username">Admin Username</label>
        <input type="text" id="username" name="username" required>
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>
      </div>
      <button type="submit" class="btn btn-primary" style="width: 100%; justify-content: center; margin-top: 1rem; background: var(--accent2);">Enter Portal</button>
    </form>
    <div class="auth-footer">
      <a href="${pageContext.request.contextPath}/index.jsp">&larr; Back to Main Site</a>
    </div>
  </div>
</div>
</body>
</html>
