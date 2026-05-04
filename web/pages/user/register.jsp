<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>User Registration — ParkEase</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-wrap">
  <div class="auth-card" style="max-width:500px;">
    <div class="auth-logo">
      <h1>&#9646; ParkEase</h1>
      <p>Create a new account</p>
    </div>

    <c:if test="${not empty error}">
      <div class="alert alert-error">${error}</div>
    </c:if>

    <%-- POST to /user/register → servlet handleRegister() --%>
    <form action="${pageContext.request.contextPath}/user/register" method="post">
      <div class="form-group">
        <label for="name">Full Name</label>
        <input type="text" id="name" name="name" required placeholder="John Doe">
      </div>
      <div class="form-group">
        <label for="email">Email Address</label>
        <input type="email" id="email" name="email" required
               placeholder="john@example.com">
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <%-- minlength mirrors server-side 6-char check in servlet --%>
        <input type="password" id="password" name="password" required
               minlength="6" placeholder="Min. 6 characters">
      </div>
      <div class="form-group">
        <label for="phone">Phone Number</label>
        <input type="text" id="phone" name="phone" required
               placeholder="+1 234 567 8900">
      </div>
      <div class="form-group">
        <label for="address">Address</label>
        <textarea id="address" name="address" required
                  placeholder="Your full address" rows="2"></textarea>
      </div>
      <button type="submit" class="btn btn-primary"
              style="width:100%; justify-content:center; margin-top:1rem;">
        Create Account
      </button>
    </form>

    <div class="auth-footer">
      Already have an account?
      <a href="${pageContext.request.contextPath}/user/login">Sign In</a>
    </div>
  </div>
</div>
</body>
</html>