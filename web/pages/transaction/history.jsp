<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Transaction History — ParkEase</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav>
  <c:choose>
    <c:when test="${not empty admin}">
      <%-- Admin nav — servlet forwards admin attr into request scope --%>
      <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-brand">&#9646; <span>Admin</span>Portal</a>
      <a href="${pageContext.request.contextPath}/slot/view">Manage Slots</a>
      <div class="nav-right">
        <a href="${pageContext.request.contextPath}/admin/logout" class="btn btn-outline btn-sm">Logout</a>
      </div>
    </c:when>
    <c:otherwise>
      <%-- Regular user nav --%>
      <a href="${pageContext.request.contextPath}/index.jsp" class="nav-brand">&#9646; <span>Park</span>Ease</a>
      <a href="${pageContext.request.contextPath}/vehicle/list">My Vehicles</a>
      <a href="${pageContext.request.contextPath}/transaction/history" style="color: var(--text);">History</a>
      <div class="nav-right">
        <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
        <a href="${pageContext.request.contextPath}/user/logout" class="btn btn-outline btn-sm">Logout</a>
      </div>
    </c:otherwise>
  </c:choose>
</nav>

<div class="container">
  <div class="page-header">
    <h1>Transaction History</h1>
    <p>View past parking records and payments</p>
  </div>

  <div class="card table-wrap">
    <table>
      <thead>
        <tr>
          <th>Txn ID</th>
          <th>License Plate</th>
          <th>Slot</th>
          <th>Entry Time</th>
          <th>Exit Time</th>
          <th>Fee</th>
          <th>Status</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${empty transactions}">
            <tr>
              <td colspan="7" style="text-align:center; color:var(--muted); padding:2rem;">
                No transactions found.
              </td>
            </tr>
          </c:when>
          <c:otherwise>
            <%-- items are TransactionView objects which expose licensePlate + fee --%>
            <c:forEach var="t" items="${transactions}">
              <tr>
                <td class="text-mono">${t.transactionId}</td>
                <td class="text-mono" style="font-weight:600;">${t.licensePlate}</td>
                <td><span class="badge badge-warning">${t.slotId}</span></td>
                <td>${t.entryTime}</td>
                <td>
                  <c:choose>
                    <c:when test="${not empty t.exitTime}">${t.exitTime}</c:when>
                    <c:otherwise>—</c:otherwise>
                  </c:choose>
                </td>
                <td class="text-mono" style="color:var(--success);">
                  <%-- Valid EL: only show fee for completed transactions --%>
                  <c:choose>
                    <c:when test="${t.status == 'COMPLETED'}">
                      Rs. <fmt:formatNumber value="${t.fee}" maxFractionDigits="2"/>
                    </c:when>
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