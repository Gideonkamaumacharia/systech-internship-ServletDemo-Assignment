<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Registered Users</title>

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

    <style>

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', sans-serif;
            background: linear-gradient(135deg, #0f172a, #1e3c72);
            color: #e2e8f0;
            padding: 40px 20px;
        }

        .container {
            max-width: 900px;
            margin: auto;

            background: rgba(15, 23, 42, 0.85);
            backdrop-filter: blur(12px);

            border: 1px solid rgba(255,255,255,0.08);
            border-radius: 24px;

            padding: 35px;

            box-shadow: 0 20px 50px rgba(0,0,0,0.45);
        }

        h1 {
            text-align: center;
            font-size: 2rem;
            color: #ffffff;
            margin-bottom: 20px;
        }

        /* TABLE */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            overflow: hidden;
            border-radius: 14px;
        }

        th {
            background: linear-gradient(145deg, #1e293b, #111827);
            color: #ffffff;
            padding: 14px;
            text-align: left;
            font-size: 0.9rem;
        }

        td {
            padding: 14px;
            border-bottom: 1px solid rgba(255,255,255,0.06);
            color: #cbd5e1;
            font-size: 0.9rem;
        }

        tr:nth-child(even) {
            background: rgba(255,255,255,0.02);
        }

        tr:hover {
            background: rgba(56,189,248,0.08);
            transition: 0.2s ease;
        }

        /* ROLE BADGE */
        .badge-admin {
            background: linear-gradient(to right, #facc15, #f59e0b);
            color: #111827;
            padding: 3px 8px;
            font-size: 10px;
            font-weight: 700;
            border-radius: 8px;
        }

        /* STATUS MESSAGE */
        .message {
            margin-top: 20px;
            padding: 12px 16px;
            border-radius: 12px;
            background: rgba(255,255,255,0.05);
            color: #cbd5e1;
            font-size: 0.9rem;
            border-left: 4px solid #38bdf8;
        }

        .message.danger {
            border-left-color: #ef4444;
        }

        .message.success {
            border-left-color: #22c55e;
        }

        /* NAVIGATION */
        .navigation {
            margin-top: 25px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
            gap: 10px;
        }

        .add-link {
            text-decoration: none;
            padding: 12px 18px;
            border-radius: 12px;

            background: linear-gradient(145deg, #1e293b, #111827);
            color: #e2e8f0;

            border: 1px solid rgba(255,255,255,0.08);

            transition: 0.3s ease;
            font-size: 0.9rem;
        }

        .add-link:hover {
            transform: translateY(-3px);
            border-color: rgba(56,189,248,0.4);
        }

    </style>

</head>

<body>

<div class="container">

    <h1>Registered Users</h1>

    <c:if test="${empty dataList}">
        <div class="message danger">
            There are no users yet.
        </div>
    </c:if>

    <c:if test="${not empty dataList}">

        <table>

            <tr>
                <th>Name</th>
                <th>Role</th>
            </tr>

            <c:forEach var="user" items="${dataList}">
                <tr>
                    <td>${user.username}</td>

                    <td>
                        <c:if test="${userValidator.isAdmin(user)}">
                            <span class="badge-admin">ADMIN</span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>

        </table>

    </c:if>

    <c:choose>

        <c:when test="${dataList.size() gt 5}">
            <div class="message danger">
                The room is crowded!
            </div>
        </c:when>

        <c:when test="${not empty dataList and dataList.size() lt 5}">
            <div class="message success">
                We have room for many users!
            </div>
        </c:when>

        <c:otherwise>
            <div class="message">
                System status normal.
            </div>
        </c:otherwise>

    </c:choose>

    <div class="navigation">
        <a href="user" class="add-link">&larr; Register New User</a>
    </div>

</div>

</body>
</html>