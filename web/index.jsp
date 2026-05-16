<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>ParkEase — Vehicle Parking System</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <style>
    .hero {
      min-height: calc(100vh - 60px);
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      text-align: center;
      padding: 2rem;
      position: relative;
      overflow: hidden;
    }
    .hero::before {
      content: '';
      position: absolute;
      width: 600px; height: 600px;
      background: radial-gradient(circle, rgba(79,142,247,0.08) 0%, transparent 70%);
      top: 50%; left: 50%;
      transform: translate(-50%, -50%);
      pointer-events: none;
    }
    .hero-badge {
      display: inline-flex;
      align-items: center;
      gap: 0.5rem;
      background: rgba(79,142,247,0.1);
      border: 1px solid rgba(79,142,247,0.3);
      color: var(--accent);
      padding: 0.35rem 1rem;
      border-radius: 20px;
      font-size: 0.8rem;
      font-weight: 600;
      letter-spacing: 0.06em;
      text-transform: uppercase;
      margin-bottom: 1.5rem;
    }
    .hero h1 {
      font-size: clamp(2.5rem, 6vw, 4rem);
      font-weight: 700;
      letter-spacing: -0.04em;
      line-height: 1.1;
      margin-bottom: 1rem;
    }
    .hero h1 span { color: var(--accent); }
    .hero p {
      color: var(--muted);
      font-size: 1.05rem;
      max-width: 500px;
      margin-bottom: 2.5rem;
      line-height: 1.7;
    }
    .hero-actions { display: flex; gap: 1rem; flex-wrap: wrap; justify-content: center; }
    .feature-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
      gap: 1.25rem;
      margin-top: 4rem;
      max-width: 900px;
      width: 100%;
    }
    .feature-card {
      background: var(--bg2);
      border: 1px solid var(--border);
      border-radius: 12px;
      padding: 1.5rem;
      text-align: left;
    }
    .feature-icon {
      width: 42px; height: 42px;
      background: rgba(79,142,247,0.1);
      border-radius: 10px;
      display: flex; align-items: center; justify-content: center;
      font-size: 1.3rem;
      margin-bottom: 1rem;
    }
    .feature-card h3 { font-size: 0.95rem; font-weight: 600; margin-bottom: 0.4rem; }
    .feature-card p  { font-size: 0.83rem; color: var(--muted); line-height: 1.5; }
  </style>
</head>
<body>
<nav>
  <a href="${pageContext.request.contextPath}/index.jsp" class="nav-brand">&#9646; <span>Park</span>Ease</a>
  <div class="nav-right">
    <a href="${pageContext.request.contextPath}/user/login" class="btn btn-outline btn-sm">User Login</a>
    <a href="${pageContext.request.contextPath}/admin/login" class="btn btn-primary btn-sm">Admin</a>
  </div>
</nav>

<div class="hero">
  <div class="hero-badge">&#9679; Smart Parking Management</div>
  <h1>Park Smarter,<br><span>Not Harder</span></h1>
  <p>A complete vehicle parking management system. Track slots, manage entries &amp; exits, process payments — all in one place.</p>
  <div class="hero-actions">
    <a href="${pageContext.request.contextPath}/user/register" class="btn btn-primary">Get Started</a>
    <a href="${pageContext.request.contextPath}/user/login" class="btn btn-outline">Sign In</a>
  </div>

  <div class="feature-grid">
    <div class="feature-card">
      <div class="feature-icon">&#128663;</div>
      <h3>Vehicle Management</h3>
      <p>Register and manage multiple vehicles. Search by plate number instantly.</p>
    </div>
    <div class="feature-card">
      <div class="feature-icon">&#128247;</div>
      <h3>Slot Tracking</h3>
      <p>Real-time view of available and occupied parking slots across all floors.</p>
    </div>
    <div class="feature-card">
      <div class="feature-icon">&#128179;</div>
      <h3>Auto Billing</h3>
      <p>Automatic fee calculation based on duration. Multiple payment methods supported.</p>
    </div>
    <div class="feature-card">
      <div class="feature-icon">&#128202;</div>
      <h3>Admin Dashboard</h3>
      <p>Full control over users, slots, transactions and revenue reports.</p>
    </div>
  </div>
</div>
</body>
</html>
