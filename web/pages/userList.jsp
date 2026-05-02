<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.parking.model.User" %>
<!DOCTYPE html>
<html>
<head>
  <title>User List</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h3>All Users</h3>
  <table class="table table-bordered">
    <thead class="table-dark">
    <tr>
      <th>ID</th>
      <th>Username</th>
      <th>Email</th>
      <th>Role</th>
      <th>Active</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<User> users = (List<User>) request.getAttribute("users");
      if(users != null) {
        for(User u : users) {
    %>
    <tr>
      <td><%= u.getId() %></td>
      <td><%= u.getUsername() %></td>
      <td><%= u.getEmail() %></td>
      <td><%= u.getRole() %></td>
      <td><%= u.isActive() %></td>
      <td>
        <form action="${pageContext.request.contextPath}/user/delete" method="post">
          <input type="hidden" name="username" value="<%= u.getUsername() %>"/>
          <button type="submit" class="btn btn-danger btn-sm">Delete</button>
        </form>
      </td>
    </tr>
    <% } } %>
    </tbody>
  </table>
  <a href="${pageContext.request.contextPath}/pages/register.jsp" class="btn btn-primary">Add New User</a>
</div>
</body>
</html>