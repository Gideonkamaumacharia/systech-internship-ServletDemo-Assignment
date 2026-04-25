<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Showroom Inventory</title>
    <style>
        body { font-family: 'Inter', sans-serif; background: #f0f2f5; padding: 50px; display: flex; flex-direction: column; align-items: center; }

        .container { background: white; padding: 30px; border-radius: 12px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); width: 100%; max-width: 600px; }

        h1 { color: #1a1a1a; margin-top: 0; }

        table { width: 100%; border-collapse: collapse; margin-top: 20px; }

        th { background: #1e3c72; color: white; padding: 12px; text-align: left; }

        td { padding: 12px; border-bottom: 1px solid #ddd; }

        tr:nth-child(even) { background-color: #f8f9fa; }

        .add-link { display: inline-block; margin-top: 20px; color: #1e3c72; text-decoration: none; font-weight: bold; }
    </style>
</head>
<body>
<div class="container">
    <h1>Showroom Inventory</h1>

    <c:if test="${empty applicationScope.carList}">
        <p>There are no cars in the showroom yet.</p>
    </c:if>
    <table>
        <tr>
            <th>Model</th>
            <th>Engine Specification</th>
            <th>Performance</th>
            <th>Year</th>
            <th>Price</th>
        </tr>
        <c:forEach var="car" items="${applicationScope.carList}">
            <tr>
                <td>${car.carModel}</td>
                <c:if test="${car.carModel eq 'Range Rover Sport'}">
                    <span style="background: gold; padding: 2px 5px; font-size: 10px;">PREMIUM</span>
                </c:if>
                <td>${car.engineType}</td>
                <td>${car.performanceLabel}</td>
                <td>${car.year}</td>
                <td>${car.price}</td>
            </tr>
        </c:forEach>
    </table>
    <c:choose>
        <c:when test="${applicationScope.carList.size() gt 5}">
            <p style="color: red;">The showroom is getting crowded!</p>
        </c:when>
        <c:when test="${not empty applicationScope.carList and applicationScope.carList.size() lt 5}">
            <p>We have room for more cars!</p>
        </c:when>
        <c:otherwise>
            <p>Showroom operating at normal capacity.</p>
        </c:otherwise>
    </c:choose>
    <div class="navigation">
    <a href="inventory" class="add-link">&larr; Register New Vehicle</a>
    <span> | </span>
    <a href="list" class="nav-link">View Registered Cars</a>
    </div>
</div>
</body>
</html>