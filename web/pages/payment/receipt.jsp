<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Payment Receipt — ParkEase</title>
  <link href="https://fonts.googleapis.com/css2?family=Space+Mono:wght@400;700&family=DM+Sans:wght@300;400;500;600&display=swap" rel="stylesheet">
  <style>
    :root {
      --bg:#0d0f14; --surface:#141720; --surface2:#1c2030; --border:#252a3a;
      --accent:#4f7cff; --accent2:#00e5c0; --success:#4fffb0;
      --text:#e8ecf5; --muted:#5a6175;
      --mono:'Space Mono',monospace; --sans:'DM Sans',sans-serif;
    }
    *,*::before,*::after{box-sizing:border-box;margin:0;padding:0;}
    body{font-family:var(--sans);background:var(--bg);color:var(--text);min-height:100vh;
      display:flex;align-items:center;justify-content:center;padding:2rem;
      background-image:linear-gradient(rgba(79,124,255,0.03) 1px,transparent 1px),
        linear-gradient(90deg,rgba(79,124,255,0.03) 1px,transparent 1px);
      background-size:32px 32px;}
    .receipt{width:100%;max-width:440px;background:var(--surface);border:1px solid var(--border);
      border-radius:16px;overflow:hidden;animation:slideUp 0.5s cubic-bezier(0.16,1,0.3,1) both;position:relative;}
    @keyframes slideUp{from{opacity:0;transform:translateY(24px) scale(0.98);}to{opacity:1;transform:translateY(0) scale(1);}}
    .receipt::before{content:'';display:block;height:3px;background:linear-gradient(90deg,var(--accent),var(--accent2));}
    .receipt-header{padding:2rem 2rem 1.5rem;text-align:center;border-bottom:1px dashed var(--border);position:relative;}
    .brand{font-family:var(--mono);font-size:0.78rem;font-weight:700;letter-spacing:0.18em;
      color:var(--muted);text-transform:uppercase;margin-bottom:1rem;}
    .brand span{display:inline-block;width:6px;height:6px;border-radius:50%;background:var(--accent2);
      margin-right:0.4rem;vertical-align:middle;animation:pulse 2s infinite;}
    @keyframes pulse{0%,100%{opacity:1;transform:scale(1);}50%{opacity:0.4;transform:scale(0.7);}}
    .paid-stamp{display:inline-flex;align-items:center;gap:0.4rem;background:rgba(79,255,176,0.1);
      border:1px solid rgba(79,255,176,0.25);color:#4fffb0;font-family:var(--mono);
      font-size:0.72rem;font-weight:700;letter-spacing:0.12em;padding:0.35rem 0.9rem;
      border-radius:20px;margin-bottom:1rem;}
    .paid-stamp::before{content:'✓';font-size:0.8rem;}
    .receipt-header h2{font-family:var(--mono);font-size:1.5rem;font-weight:700;color:var(--text);letter-spacing:-0.02em;}
    .receipt-header p{color:var(--muted);font-size:0.82rem;margin-top:0.25rem;}
    .perf-left,.perf-right{position:absolute;bottom:-14px;width:28px;height:28px;
      background:var(--bg);border-radius:50%;z-index:2;}
    .perf-left{left:-14px;} .perf-right{right:-14px;}
    .receipt-body{padding:1.5rem 2rem;}
    .row{display:flex;justify-content:space-between;align-items:center;
      padding:0.7rem 0;border-bottom:1px solid var(--border);
      animation:fadeIn 0.4s ease both;}
    .row:last-of-type{border-bottom:none;}
    .row:nth-child(1){animation-delay:0.10s;} .row:nth-child(2){animation-delay:0.15s;}
    .row:nth-child(3){animation-delay:0.20s;} .row:nth-child(4){animation-delay:0.25s;}
    .row:nth-child(5){animation-delay:0.30s;} .row:nth-child(6){animation-delay:0.35s;}
    @keyframes fadeIn{from{opacity:0;transform:translateX(-6px);}to{opacity:1;transform:translateX(0);}}
    .row .key{font-size:0.75rem;font-weight:600;color:var(--muted);text-transform:uppercase;letter-spacing:0.07em;}
    .row .val{font-family:var(--mono);font-size:0.8rem;color:var(--text);text-align:right;}
    .plate-tag{background:var(--surface2);border:1px solid var(--border);padding:0.2rem 0.6rem;border-radius:5px;font-weight:700;}
    .method-tag{background:rgba(79,124,255,0.1);border:1px solid rgba(79,124,255,0.2);color:var(--accent);
      padding:0.2rem 0.6rem;border-radius:5px;font-size:0.72rem;font-weight:700;letter-spacing:0.05em;text-transform:uppercase;}
    .receipt-total{margin:0 2rem;padding:1.25rem 1.5rem;background:var(--surface2);border:1px solid var(--border);
      border-radius:10px;display:flex;justify-content:space-between;align-items:center;animation:fadeIn 0.4s 0.4s ease both;}
    .receipt-total .key{font-size:0.78rem;font-weight:600;color:var(--muted);text-transform:uppercase;letter-spacing:0.07em;}
    .receipt-total .amount{font-family:var(--mono);font-size:1.6rem;font-weight:700;color:#4fffb0;letter-spacing:-0.02em;}
    .receipt-total .currency{font-size:0.9rem;color:var(--muted);margin-right:0.2rem;}
    .barcode-strip{margin:1.5rem 2rem;height:36px;
      background:repeating-linear-gradient(90deg,var(--border) 0px,var(--border) 2px,transparent 2px,transparent 5px);
      border-radius:3px;opacity:0.5;animation:fadeIn 0.4s 0.45s ease both;}
    .receipt-footer{padding:0 2rem 2rem;text-align:center;animation:fadeIn 0.4s 0.5s ease both;}
    .receipt-footer p{font-size:0.75rem;color:var(--muted);margin-bottom:1.25rem;line-height:1.5;}
    .btn{display:inline-flex;align-items:center;gap:0.4rem;font-family:var(--sans);
      font-size:0.82rem;font-weight:600;padding:0.65rem 1.5rem;border-radius:8px;
      border:none;cursor:pointer;text-decoration:none;transition:opacity 0.2s,transform 0.15s;
      width:100%;justify-content:center;}
    .btn:active{transform:scale(0.97);}
    .btn-primary{background:var(--accent);color:#fff;} .btn-primary:hover{opacity:0.88;}
    .btn-outline{background:transparent;color:var(--muted);border:1px solid var(--border);margin-top:0.6rem;}
    .btn-outline:hover{color:var(--text);border-color:var(--accent);}
  </style>
</head>
<body>
<div class="receipt">
  <div class="receipt-header">
    <div class="brand"><span></span>ParkEase</div>
    <div class="paid-stamp">Payment Confirmed</div>
    <h2>Receipt</h2>
    <p>Digital Parking Receipt</p>
    <div class="perf-left"></div>
    <div class="perf-right"></div>
  </div>
  <div class="receipt-body">
    <div class="row">
      <span class="key">Receipt ID</span>
      <span class="val">${payment.paymentId}</span>
    </div>
    <div class="row">
      <span class="key">Transaction ID</span>
      <span class="val">${payment.transactionId}</span>
    </div>
    <div class="row">
      <span class="key">Payment Method</span>
      <span class="val"><span class="method-tag">${payment.method}</span></span>
    </div>
    <div class="row">
      <span class="key">Date &amp; Time</span>
      <span class="val">${payment.paymentTime}</span>
    </div>
    <c:if test="${not empty vehicle}">
      <div class="row">
        <span class="key">Vehicle</span>
        <span class="val">
          <span class="plate-tag">${vehicle.plateNumber}</span>&nbsp;${vehicle.type}
        </span>
      </div>
    </c:if>
    <c:if test="${not empty slot}">
      <div class="row">
        <span class="key">Parking Slot</span>
        <span class="val">${slot.slotNumber}</span>
      </div>
    </c:if>
  </div>
  <div class="receipt-total">
    <span class="key">Total Paid</span>
    <span class="amount"><span class="currency">Rs.</span>${payment.amount}</span>
  </div>
  <div class="barcode-strip"></div>
  <div class="receipt-footer">
    <p>Thank you for using ParkEase.<br>Please keep this receipt for your records.</p>
    <a href="${pageContext.request.contextPath}/transaction/history" class="btn btn-primary">&#128203; View All Transactions</a>
    <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-outline">&#8592; Return to Dashboard</a>
  </div>
</div>
</body>
</html>