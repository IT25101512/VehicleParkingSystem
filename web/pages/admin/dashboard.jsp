<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Admin Dashboard — ParkEase</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav>
  <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-brand">
    &#9646; <span>Admin</span>Portal
  </a>
  <a href="${pageContext.request.contextPath}/admin/manage">Manage System</a>
  <a href="${pageContext.request.contextPath}/transaction/history">All Transactions</a>
  <div class="nav-right">
    <%-- ${admin} is set in request scope by exposeAdmin() in servlet --%>
    <span class="badge badge-info">Admin: ${admin.name}</span>
    <a href="${pageContext.request.contextPath}/admin/logout"
       class="btn btn-outline btn-sm">Logout</a>
  </div>
</nav>

<div class="container">
  <div class="page-header flex-between">
    <div>
      <h1>System Overview</h1>
      <p>Real-time parking analytics and operations</p>
    </div>
    <div class="flex-gap">
      <a href="${pageContext.request.contextPath}/transaction/entry"
         class="btn btn-primary">New Entry</a>
      <a href="${pageContext.request.contextPath}/transaction/exit"
         class="btn btn-outline">Process Exit</a>
    </div>
  </div>

  <%-- Stats — all dynamic from loadDashboard() --%>
  <div class="stat-grid">
    <div class="stat-card blue">
      <div class="stat-value text-mono">${totalUsers}</div>
      <div class="stat-label">Registered Users</div>
    </div>
    <div class="stat-card green">
      <div class="stat-value text-mono">${availableSlots}</div>
      <div class="stat-label">Available Slots</div>
    </div>
    <div class="stat-card orange">
      <div class="stat-value text-mono">${occupiedSlots}</div>
      <div class="stat-label">Occupied Slots</div>
    </div>
    <div class="stat-card purple">
      <div class="stat-value text-mono">
        Rs. <fmt:formatNumber value="${totalRevenue}" maxFractionDigits="2"/>
      </div>
      <div class="stat-label">Total Revenue</div>
    </div>
  </div>

  <%-- Quick actions --%>
  <div class="card">
    <div class="card-title">Quick Actions</div>
    <div class="flex-gap">
      <a href="${pageContext.request.contextPath}/admin/manage"
         class="btn btn-outline">Manage Users &amp; Slots</a>
      <a href="${pageContext.request.contextPath}/transaction/entry"
         class="btn btn-outline">Record Vehicle Entry</a>
      <a href="${pageContext.request.contextPath}/transaction/exit"
         class="btn btn-outline">Process Vehicle Exit</a>
    </div>
  </div>

  <%-- Recent transactions table --%>
  <div class="card table-wrap mt-3">
    <div class="card-title">Recent Transactions</div>
    <table>
      <thead>
        <tr>
          <th>Txn ID</th>
          <th>Vehicle ID</th>
          <th>Slot</th>
          <th>Entry Time</th>
          <th>Exit Time</th>
          <th>Status</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${empty recentTransactions}">
            <tr>
              <td colspan="6" style="text-align:center; color:var(--muted); padding:2rem;">
                No transactions yet.
              </td>
            </tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="t" items="${recentTransactions}">
              <tr>
                <td class="text-mono">${t.transactionId}</td>
                <td>${t.vehicleId}</td>
                <td><span class="badge badge-warning">${t.slotId}</span></td>
                <td>${t.entryTime}</td>
                <td>
                  <c:choose>
                    <c:when test="${not empty t.exitTime}">${t.exitTime}</c:when>
                    <c:otherwise>—</c:otherwise>
                  </c:choose>
                </td>
                <td>
                  <c:choose>
                    <c:when test="${t.status == 'ACTIVE'}">
                      <span class="badge badge-info">Active</span>
                    </c:when>
                    <c:when test="${t.status == 'COMPLETED'}">
                      <span class="badge badge-success">Completed</span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge badge-danger">${t.status}</span>
                    </c:otherwise>
                  </c:choose>
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