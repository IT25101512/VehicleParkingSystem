<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Manage System — ParkEase</title>
  <link href="https://fonts.googleapis.com/css2?family=Space+Mono:wght@400;700&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
  <style>
    :root {
      --bg:        #0d0f14;
      --surface:   #141720;
      --surface2:  #1c2030;
      --border:    #252a3a;
      --accent:    #4f7cff;
      --accent2:   #00e5c0;
      --danger:    #ff4f6a;
      --warning:   #ffb84f;
      --success:   #4fffb0;
      --text:      #e8ecf5;
      --muted:     #5a6175;
      --mono:      'Space Mono', monospace;
      --sans:      'DM Sans', sans-serif;
    }

    *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

    body {
      font-family: var(--sans);
      background: var(--bg);
      color: var(--text);
      min-height: 100vh;
    }

    nav {
      display: flex;
      align-items: center;
      gap: 2rem;
      padding: 0 2rem;
      height: 60px;
      background: var(--surface);
      border-bottom: 1px solid var(--border);
      position: sticky;
      top: 0;
      z-index: 100;
    }

    .nav-brand {
      font-family: var(--mono);
      font-size: 0.9rem;
      font-weight: 700;
      color: var(--accent);
      text-decoration: none;
      letter-spacing: 0.05em;
      display: flex;
      align-items: center;
      gap: 0.4rem;
      white-space: nowrap;
    }

    .nav-brand .dot {
      width: 8px; height: 8px;
      border-radius: 50%;
      background: var(--accent2);
      animation: pulse 2s infinite;
    }

    @keyframes pulse {
      0%, 100% { opacity: 1; transform: scale(1); }
      50%       { opacity: 0.5; transform: scale(0.8); }
    }

    nav a {
      text-decoration: none;
      color: var(--muted);
      font-size: 0.85rem;
      font-weight: 500;
      transition: color 0.2s;
    }
    nav a:hover { color: var(--text); }
    nav a.active { color: var(--text); }

    .nav-right {
      margin-left: auto;
      display: flex;
      align-items: center;
      gap: 1rem;
    }

    .admin-chip {
      font-family: var(--mono);
      font-size: 0.72rem;
      background: rgba(79,124,255,0.12);
      color: var(--accent);
      border: 1px solid rgba(79,124,255,0.3);
      padding: 0.3rem 0.75rem;
      border-radius: 20px;
    }

    .container {
      max-width: 1100px;
      margin: 0 auto;
      padding: 2.5rem 2rem;
    }

    .page-header {
      margin-bottom: 2.5rem;
      animation: fadeUp 0.4s ease both;
    }

    .page-header h1 {
      font-family: var(--mono);
      font-size: 1.6rem;
      font-weight: 700;
      color: var(--text);
      letter-spacing: -0.02em;
    }

    .page-header p {
      color: var(--muted);
      font-size: 0.88rem;
      margin-top: 0.35rem;
    }

    @keyframes fadeUp {
      from { opacity: 0; transform: translateY(12px); }
      to   { opacity: 1; transform: translateY(0); }
    }

    .card {
      background: var(--surface);
      border: 1px solid var(--border);
      border-radius: 10px;
      padding: 1.75rem;
      margin-bottom: 1.5rem;
      animation: fadeUp 0.4s ease both;
    }

    .card:nth-child(2) { animation-delay: 0.05s; }
    .card:nth-child(3) { animation-delay: 0.10s; }
    .card:nth-child(4) { animation-delay: 0.15s; }
    .card:nth-child(5) { animation-delay: 0.20s; }

    .card-header {
      display: flex;
      align-items: center;
      gap: 0.6rem;
      margin-bottom: 1.25rem;
      padding-bottom: 1rem;
      border-bottom: 1px solid var(--border);
    }

    .card-icon {
      width: 32px; height: 32px;
      border-radius: 8px;
      display: flex; align-items: center; justify-content: center;
      font-size: 0.9rem;
    }
    .icon-blue  { background: rgba(79,124,255,0.15); color: var(--accent); }
    .icon-teal  { background: rgba(0,229,192,0.12);  color: var(--accent2); }
    .icon-red   { background: rgba(255,79,106,0.12); color: var(--danger); }
    .icon-amber { background: rgba(255,184,79,0.12); color: var(--warning); }

    .card-title {
      font-family: var(--mono);
      font-size: 0.82rem;
      font-weight: 700;
      color: var(--text);
      letter-spacing: 0.04em;
      text-transform: uppercase;
    }

    .card-count {
      margin-left: auto;
      font-family: var(--mono);
      font-size: 0.72rem;
      color: var(--muted);
    }

    .form-grid {
      display: grid;
      grid-template-columns: 1fr 1fr 1fr auto;
      gap: 1rem;
      align-items: end;
    }

    .form-group label {
      display: block;
      font-size: 0.75rem;
      font-weight: 600;
      color: var(--muted);
      text-transform: uppercase;
      letter-spacing: 0.06em;
      margin-bottom: 0.4rem;
    }

    .form-group input,
    .form-group select {
      width: 100%;
      background: var(--surface2);
      border: 1px solid var(--border);
      border-radius: 7px;
      color: var(--text);
      font-family: var(--sans);
      font-size: 0.875rem;
      padding: 0.6rem 0.9rem;
      outline: none;
      transition: border-color 0.2s, box-shadow 0.2s;
    }

    .form-group input::placeholder { color: var(--muted); }

    .form-group input:focus,
    .form-group select:focus {
      border-color: var(--accent);
      box-shadow: 0 0 0 3px rgba(79,124,255,0.15);
    }

    .form-group select option { background: var(--surface2); }

    .btn {
      font-family: var(--sans);
      font-size: 0.82rem;
      font-weight: 600;
      padding: 0.6rem 1.2rem;
      border-radius: 7px;
      border: none;
      cursor: pointer;
      text-decoration: none;
      display: inline-flex;
      align-items: center;
      gap: 0.4rem;
      transition: opacity 0.2s, transform 0.1s;
      white-space: nowrap;
    }
    .btn:active { transform: scale(0.97); }

    .btn-primary { background: var(--accent); color: #fff; }
    .btn-primary:hover { opacity: 0.88; }

    .btn-outline {
      background: transparent;
      color: var(--text);
      border: 1px solid var(--border);
    }
    .btn-outline:hover { border-color: var(--accent); color: var(--accent); }

    .btn-danger {
      background: rgba(255,79,106,0.12);
      color: var(--danger);
      border: 1px solid rgba(255,79,106,0.25);
      padding: 0.35rem 0.75rem;
      font-size: 0.75rem;
    }
    .btn-danger:hover { background: rgba(255,79,106,0.22); }

    .btn-logout {
      background: transparent;
      color: var(--muted);
      border: 1px solid var(--border);
      padding: 0.3rem 0.85rem;
      font-size: 0.78rem;
    }
    .btn-logout:hover { color: var(--danger); border-color: var(--danger); }

    .table-wrap { overflow-x: auto; }

    table { width: 100%; border-collapse: collapse; font-size: 0.84rem; }

    thead th {
      text-align: left;
      font-family: var(--mono);
      font-size: 0.7rem;
      font-weight: 700;
      text-transform: uppercase;
      letter-spacing: 0.08em;
      color: var(--muted);
      padding: 0 0.9rem 0.75rem;
      border-bottom: 1px solid var(--border);
    }

    tbody tr {
      border-bottom: 1px solid var(--border);
      transition: background 0.15s;
    }
    tbody tr:last-child { border-bottom: none; }
    tbody tr:hover { background: var(--surface2); }

    tbody td {
      padding: 0.8rem 0.9rem;
      color: var(--text);
      vertical-align: middle;
    }

    .mono { font-family: var(--mono); font-size: 0.78rem; color: var(--muted); }

    .plate {
      font-family: var(--mono);
      font-size: 0.82rem;
      font-weight: 700;
      color: var(--text);
      background: var(--surface2);
      border: 1px solid var(--border);
      padding: 0.2rem 0.55rem;
      border-radius: 5px;
    }

    .badge {
      font-family: var(--mono);
      font-size: 0.68rem;
      font-weight: 700;
      padding: 0.2rem 0.55rem;
      border-radius: 20px;
      letter-spacing: 0.05em;
      text-transform: uppercase;
    }
    .badge-success { background: rgba(79,255,176,0.12); color: var(--success); }
    .badge-danger  { background: rgba(255,79,106,0.12); color: var(--danger); }
    .badge-warning { background: rgba(255,184,79,0.12); color: var(--warning); }
    .badge-info    { background: rgba(79,124,255,0.12); color: var(--accent); }

    .type-tag {
      font-size: 0.75rem;
      color: var(--muted);
      display: flex;
      align-items: center;
      gap: 0.35rem;
    }

    .empty-row td {
      text-align: center;
      color: var(--muted);
      padding: 2.5rem !important;
      font-size: 0.82rem;
      font-style: italic;
    }

    .footer-nav {
      margin-top: 2rem;
      padding-top: 1.5rem;
      border-top: 1px solid var(--border);
    }
  </style>
</head>
<body>

<nav>
  <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-brand">
    <span class="dot"></span>PARKEASE
  </a>
  <a href="${pageContext.request.contextPath}/admin/manage" class="active">Manage</a>
  <a href="${pageContext.request.contextPath}/transaction/history">Transactions</a>
  <div class="nav-right">
    <span class="admin-chip">&#9679; ${admin.name}</span>
    <a href="${pageContext.request.contextPath}/admin/logout" class="btn btn-logout">Logout</a>
  </div>
</nav>

<div class="container">

  <div class="page-header">
    <h1>System Management</h1>
    <p>Manage users, vehicles, and parking slots from one place</p>
  </div>

  <%-- ── ADD SLOT FORM ── --%>
  <div class="card">
    <div class="card-header">
      <div class="card-icon icon-blue">&#43;</div>
      <span class="card-title">Add New Parking Slot</span>
    </div>
    <form action="${pageContext.request.contextPath}/admin/addSlot" method="post">
      <div class="form-grid">
        <div class="form-group">
          <label for="slotNumber">Slot Number</label>
          <input type="text" id="slotNumber" name="slotNumber"
                 required placeholder="e.g. A-101">
        </div>
        <div class="form-group">
          <label for="type">Slot Type</label>
          <select id="type" name="type" required>
            <option value="CAR">Car</option>
            <option value="BIKE">Bike</option>
            <option value="TRUCK">Truck</option>
          </select>
        </div>
        <div class="form-group">
          <label for="floor">Floor</label>
          <input type="text" id="floor" name="floor"
                 required placeholder="e.g. Ground, 1st">
        </div>
        <div class="form-group">
          <label>&nbsp;</label>
          <button type="submit" class="btn btn-primary">Add Slot</button>
        </div>
      </div>
    </form>
  </div>

  <%-- ── USERS TABLE ── --%>
  <div class="card table-wrap">
    <div class="card-header">
      <div class="card-icon icon-teal">&#128100;</div>
      <span class="card-title">Registered Users</span>
    </div>
    <table>
      <thead>
        <tr>
          <th>User ID</th><th>Name</th><th>Email</th><th>Phone</th><th>Action</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${empty users}">
            <tr class="empty-row"><td colspan="5">No users registered yet.</td></tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="u" items="${users}">
              <tr>
                <td><span class="mono">${u.userId}</span></td>
                <td>${u.name}</td>
                <td>${u.email}</td>
                <td>${u.phone}</td>
                <td>
                  <form action="${pageContext.request.contextPath}/admin/deleteUser"
                        method="post" style="display:inline;"
                        onsubmit="return confirm('Delete user ${u.name}?');">
                    <input type="hidden" name="id" value="${u.userId}">
                    <button type="submit" class="btn btn-danger">Delete</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </tbody>
    </table>
  </div>

  <%-- ── VEHICLES TABLE ── --%>
  <div class="card table-wrap">
    <div class="card-header">
      <div class="card-icon icon-amber">&#128663;</div>
      <span class="card-title">Registered Vehicles</span>
    </div>
    <table>
      <thead>
        <tr>
          <th>Vehicle ID</th><th>Plate</th><th>Type</th><th>Owner ID</th><th>Action</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${empty vehicles}">
            <tr class="empty-row"><td colspan="5">No vehicles registered yet.</td></tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="v" items="${vehicles}">
              <tr>
                <td><span class="mono">${v.vehicleId}</span></td>
                <td><span class="plate">${v.plateNumber}</span></td>
                <td>
                  <span class="type-tag">
                    <c:choose>
                      <c:when test="${v.type == 'CAR'}">&#128663;</c:when>
                      <c:when test="${v.type == 'BIKE'}">&#128690;</c:when>
                      <c:when test="${v.type == 'TRUCK'}">&#128666;</c:when>
                    </c:choose>
                    ${v.type}
                  </span>
                </td>
                <td><span class="mono">${v.ownerUserId}</span></td>
                <td>
                  <form action="${pageContext.request.contextPath}/admin/deleteVehicle"
                        method="post" style="display:inline;"
                        onsubmit="return confirm('Delete vehicle ${v.plateNumber}?');">
                    <input type="hidden" name="id" value="${v.vehicleId}">
                    <button type="submit" class="btn btn-danger">Delete</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </tbody>
    </table>
  </div>

  <%-- ── SLOTS TABLE ── --%>
  <div class="card table-wrap">
    <div class="card-header">
      <div class="card-icon icon-red">&#9646;</div>
      <span class="card-title">Parking Slots</span>
    </div>
    <table>
      <thead>
        <tr>
          <th>Slot ID</th><th>Slot No.</th><th>Type</th><th>Floor</th><th>Status</th><th>Action</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${empty slots}">
            <tr class="empty-row"><td colspan="6">No slots configured yet.</td></tr>
          </c:when>
          <c:otherwise>
            <c:forEach var="sl" items="${slots}">
              <tr>
                <td><span class="mono">${sl.slotId}</span></td>
                <td><span class="badge badge-warning">${sl.slotNumber}</span></td>
                <td>
                  <span class="type-tag">
                    <c:choose>
                      <c:when test="${sl.type == 'CAR'}">&#128663;</c:when>
                      <c:when test="${sl.type == 'BIKE'}">&#128690;</c:when>
                      <c:when test="${sl.type == 'TRUCK'}">&#128666;</c:when>
                    </c:choose>
                    ${sl.type}
                  </span>
                </td>
                <td>${sl.floor}</td>
                <td>
                  <c:choose>
                    <c:when test="${sl.status == 'AVAILABLE'}">
                      <span class="badge badge-success">Available</span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge badge-danger">Occupied</span>
                    </c:otherwise>
                  </c:choose>
                </td>
                <td>
                  <form action="${pageContext.request.contextPath}/admin/deleteSlot"
                        method="post" style="display:inline;"
                        onsubmit="return confirm('Delete slot ${sl.slotNumber}?');">
                    <input type="hidden" name="id" value="${sl.slotId}">
                    <button type="submit" class="btn btn-danger">Delete</button>
                  </form>
                </td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </tbody>
    </table>
  </div>

  <div class="footer-nav">
    <a href="${pageContext.request.contextPath}/admin/dashboard"
       class="btn btn-outline">&#8592; Back to Dashboard</a>
  </div>

</div>
</body>
</html>