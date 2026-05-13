<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Manage Slots — ParkEase</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav>
  <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-brand">&#9646; <span>Admin</span>Portal</a>
  <a href="${pageContext.request.contextPath}/slot/view" style="color: var(--text);">Manage Slots</a>
  <a href="${pageContext.request.contextPath}/transaction/history">All Transactions</a>
  <div class="nav-right">
    <a href="${pageContext.request.contextPath}/admin/logout" class="btn btn-outline btn-sm">Logout</a>
  </div>
</nav>

<div class="container">
  <div class="page-header flex-between">
    <div>
      <h1>Parking Slots</h1>
      <p>Live status of all parking slots in the facility</p>
    </div>
    <a href="${pageContext.request.contextPath}/slot/allocate" class="btn btn-primary">Allocate Slot</a>
  </div>

  <div class="card">
    <div class="card-title">Facility Overview</div>
    <div class="slot-grid">
      <c:choose>
        <c:when test="${empty slots}">
           <p class="text-muted">No slots configured in the system.</p>
        </c:when>
        <c:otherwise>
          <c:forEach var="slot" items="${slots}">

              <div class="slot-number">${slot.slotId}</div>
              <div class="slot-type">${slot.type}</div>
              </div>
            </div>
          </c:forEach>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>
</body>
</html>
        </div>
    </div>

</body>
</html>