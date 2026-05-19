<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Car Brands</title>

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

    <style>

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Inter', sans-serif;
            background: #0f172a;
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
            color: #ffffff;
            font-size: 2rem;
            margin-bottom: 20px;
        }

        /* TABLE */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            border-radius: 14px;
            overflow: hidden;
        }

        th {
            background: linear-gradient(145deg, #1e293b, #111827);
            color: #ffffff;
            text-align: left;
            padding: 14px;
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

        /* BRAND BADGE (accent usage properly controlled) */
        .badge {
            display: inline-block;
            padding: 4px 10px;
            font-size: 11px;
            font-weight: 600;
            border-radius: 8px;

            background: rgba(56,189,248,0.12);
            color: #38bdf8;
            border: 1px solid rgba(56,189,248,0.25);
        }

        /* INFO BOX */
        .info {
            margin-top: 20px;
            padding: 14px 16px;
            border-radius: 14px;
            background: rgba(255,255,255,0.05);
            color: #cbd5e1;
            font-size: 0.9rem;
            border-left: 4px solid #38bdf8;
        }

        /* NAVIGATION */
        .navigation {
            margin-top: 25px;
            display: flex;
            justify-content: space-between;
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

        /* EMPTY STATE */
        .empty {
            text-align: center;
            margin-top: 20px;
            color: #94a3b8;
        }

    </style>

</head>

<body>

<div class="container">

    <h1>Registered Car Brands</h1>

    <c:if test="${empty dataList}">
        <p class="empty">No brands have been registered yet.</p>
    </c:if>

    <c:if test="${not empty dataList}">

        <table>
            <tr>
                <th>Brand Name</th>
                <th>Country Of Origin</th>
            </tr>

            <c:forEach var="brand" items="${dataList}">
                <tr>
                    <td>
                        <span class="badge">${brand.name}</span>
                    </td>
                    <td>${brand.countryOfOrigin}</td>

                    <c:set var="item" value="${brand}" scope="request" />
                    <jsp:include page="_actionButtons.jsp" />

                </tr>
            </c:forEach>

        </table>

    </c:if>

    <div class="info">
        <c:choose>
            <c:when test="${dataList.size() gt 10}">
                Large number of brands registered.
            </c:when>

            <c:when test="${not empty dataList and dataList.size() le 10}">
                Brand list is manageable.
            </c:when>

            <c:otherwise>
                No data available.
            </c:otherwise>
        </c:choose>
    </div>

    <div class="navigation">
        <a href="brand" class="add-link">&larr; Register New Brand</a>
        <a href="home" class="add-link">Back to Dashboard</a>
    </div>

</div>

</body>
</html>