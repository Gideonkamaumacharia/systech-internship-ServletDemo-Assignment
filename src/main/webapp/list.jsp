<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Showroom Inventory</title>

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
            max-width: 1200px;
            margin: auto;
            background: rgba(15, 23, 42, 0.85);
            backdrop-filter: blur(12px);
            border: 1px solid rgba(255,255,255,0.08);
            border-radius: 24px;
            padding: 35px;
            box-shadow: 0 20px 50px rgba(0,0,0,0.45);
        }

        h1 {
            color: #ffffff;
            margin-bottom: 20px;
            font-size: 2rem;
            text-align: center;
        }

        /* TABLE */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 25px;
            overflow: hidden;
            border-radius: 14px;
        }

        th {
            background: linear-gradient(145deg, #1e293b, #111827);
            color: #ffffff;
            padding: 14px;
            text-align: left;
            font-size: 0.9rem;
            border-bottom: 1px solid rgba(255,255,255,0.08);
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

        /* BADGES */
        .badge-premium {
            background: linear-gradient(to right, #facc15, #f59e0b);
            color: #111827;
            padding: 3px 8px;
            font-size: 10px;
            font-weight: 700;
            border-radius: 8px;
            margin-left: 8px;
        }

        .badge-expensive {
            color: #f87171;
            font-weight: 700;
        }

        /* MESSAGES */
        .message {
            margin-top: 20px;
            padding: 12px 16px;
            border-radius: 12px;
            background: rgba(255,255,255,0.05);
            color: #cbd5e1;
            font-size: 0.9rem;
        }

        .message.danger {
            border-left: 4px solid #ef4444;
        }

        .message.success {
            border-left: 4px solid #22c55e;
        }

        /* NAVIGATION */
        .navigation {
            margin-top: 25px;
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
            gap: 10px;
        }

        .nav-link {
            text-decoration: none;
            color: #e2e8f0;
            padding: 12px 18px;
            background: linear-gradient(145deg, #1e293b, #111827);
            border-radius: 12px;
            border: 1px solid rgba(255,255,255,0.08);
            transition: 0.3s ease;
            font-size: 0.9rem;
        }

        .nav-link:hover {
            transform: translateY(-3px);
            border-color: rgba(56,189,248,0.4);
        }

        .add-link {
            color: #38bdf8;
            font-weight: 600;
            text-decoration: none;
            padding: 12px 18px;
        }

        .add-link:hover {
            text-decoration: underline;
        }

    </style>
</head>

<body>

<div class="container">

    <h1>Showroom Inventory</h1>

    <c:if test="${empty dataList}">
        <div class="message">
            There are no cars in the showroom yet.
        </div>
    </c:if>

    <table>
        <tr>
            <th>Model</th>
            <th>Engine Specification</th>
            <th>Performance</th>
            <th>Year</th>
            <th>Price</th>
            <th>Brand</th>
            <th>Category</th>
            <th>Showroom</th>
            <th>Status</th>
        </tr>

        <c:forEach var="car" items="${dataList}">
            <tr>
                <td>
                        ${car.carModel}

                    <c:if test="${car.carModel eq 'Range Rover Sport'}">
                        <span class="badge-premium">PREMIUM</span>
                    </c:if>
                </td>

                <td>${car.engineType}</td>
                <td>${car.performanceLabel}</td>
                <td>${car.year}</td>
                <td>${car.price}</td>
                <td>${car.brand}</td>
                <td>${car.category}</td>
                <td>${car.showroom}</td>

                <td>
                    <c:if test="${carValidator.isExpensive(car)}">
                        <span class="badge-expensive">EXPENSIVE</span>
                    </c:if>
                </td>

                <td>
                    <a href="editCar?id=${car.id}">
                        <button>Edit</button>
                    </a>

                    <form action="deleteCar" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="${car.id}" />

                        <button type="submit">
                            Delete
                        </button>
                    </form>
                </td>

            </tr>
        </c:forEach>

    </table>

    <c:choose>

        <c:when test="${dataList.size() gt 30}">
            <div class="message danger">
                The showroom is getting crowded!
            </div>
        </c:when>

        <c:when test="${not empty dataList and dataList.size() lt 5}">
            <div class="message success">
                We have room for more cars!
            </div>
        </c:when>

        <c:otherwise>
            <div class="message">
                Showroom operating at normal capacity.
            </div>
        </c:otherwise>

    </c:choose>

    <div class="navigation">
        <a href="car" class="nav-link">&larr; Register New Vehicle</a>
        <a href="home" class="add-link">Back to Dashboard</a>
    </div>

</div>

</body>
</html>