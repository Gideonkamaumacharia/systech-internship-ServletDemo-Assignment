<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String error = request.getParameter("error");
    String msg   = request.getParameter("msg");
    String dest  = request.getParameter("dest");
    if (dest == null || dest.isEmpty()) dest = "/home";

    // Simple HTML escape helper
    java.util.function.Function<String,String> esc = s -> s == null ? "" : s
            .replace("&","&amp;").replace("<","&lt;")
            .replace(">","&gt;").replace("\"","&quot;").replace("'","&#x27;");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Login | Elite Showroom</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body {
            font-family: Inter, sans-serif;
            background: linear-gradient(135deg, #0f172a, #1e3c72);
            display:flex; align-items:center; justify-content:center;
            height:100vh; color:#e2e8f0;
        }
        .login-card {
            background: rgba(15,23,42,0.85);
            backdrop-filter: blur(12px);
            padding:40px; border-radius:20px; width:380px;
            text-align:center;
            border:1px solid rgba(255,255,255,0.08);
            box-shadow:0 20px 50px rgba(0,0,0,0.45);
        }
        h1 { color:#ffffff; margin-bottom:20px; font-size:1.8rem; }
        input {
            width:100%; padding:10px 12px; margin:10px 0;
            border-radius:10px; border:1px solid rgba(255,255,255,0.08);
            background:#1e293b; color:#fff; outline:none;
        }
        input:focus {
            border-color:#38bdf8;
            box-shadow:0 0 0 4px rgba(56,189,248,0.15);
        }
        button {
            width:100%; padding:12px; border:none; border-radius:12px;
            cursor:pointer; font-weight:600;
            background: linear-gradient(to right, #38bdf8, #6366f1);
            color:white; transition:0.3s ease;
        }
        button:hover { transform:translateY(-2px); box-shadow:0 10px 25px rgba(56,189,248,0.25); }
        .error {
            background:rgba(239,68,68,0.15); color:#ef4444;
            padding:10px; border-radius:10px; margin-bottom:12px;
            border:1px solid rgba(239,68,68,0.3); font-size:0.85rem;
        }
        .info {
            background:rgba(56,189,248,0.08); color:#38bdf8;
            padding:10px; border-radius:10px; margin-bottom:12px;
            border:1px solid rgba(56,189,248,0.2); font-size:0.85rem;
        }
        .back-link {
            display:block; width:100%; padding:11px; margin-top:15px;
            border-radius:12px; background:rgba(255,255,255,0.05);
            border:1px solid rgba(255,255,255,0.08); color:#cbd5e1;
            text-decoration:none; font-size:0.9rem; transition:0.3s ease;
        }
    </style>
</head>
<body>
<div class="login-card">
    <h1>Showroom Access</h1>

    <% if ("missing".equals(error)) { %>
    <div class="error">Please provide both username and password.</div>
    <% } else if ("invalid".equals(error)) { %>
    <div class="error">Invalid username or password. Please try again.</div>
    <% } else if ("locked".equals(error)) { %>
    <div class="error">Your account is locked. Contact the administrator.</div>
    <% } else if (msg != null && !msg.isEmpty()) { %>
    <div class="info"><%= esc.apply(msg) %></div>
    <% } %>

    <form action="<%= request.getContextPath() %>/login" method="POST">
        <input type="hidden" name="dest" value="<%= esc.apply(dest) %>">
        <input type="text"     name="uname" placeholder="Enter Username" required>
        <input type="password" name="pass"  placeholder="Enter System Password" required>
        <button type="submit">Enter Showroom</button>
    </form>

    <a href="<%= request.getContextPath() %>/home" class="back-link">
        &#8962; Return to Dashboard
    </a>
</div>
</body>
</html>