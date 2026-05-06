<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Car Brands</title>
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background: #f0f2f5;
            padding: 50px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        ```
        .container {
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 650px;
        }

        h1 {
            color: #1a1a1a;
            margin-top: 0;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th {
            background: #2c3e50;
            color: white;
            padding: 12px;
            text-align: left;
        }

        td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }

        tr:nth-child(even) {
            background-color: #f8f9fa;
        }

        .badge {
            background: #3498db;
            color: white;
            padding: 4px 8px;
            font-size: 11px;
            border-radius: 6px;
        }

        .add-link {
            display: inline-block;
            margin-top: 20px;
            color: #2c3e50;
            text-decoration: none;
            font-weight: bold;
        }

        .info {
            margin-top: 15px;
            font-size: 14px;
        }
    </style>
    ```

</head>

<body>

<div class="container">
    <h1>Registered Car Brands</h1>

    ```
    <c:if test="${empty dataList}">
        <p>No brands have been registered yet.</p>
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
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <div class="info">
        <c:choose>
            <c:when test="${dataList.size() gt 10}">
                <p style="color: red;">Large number of brands registered.</p>
            </c:when>
            <c:when test="${not empty dataList and dataList.size() le 10}">
                <p>Brand list is manageable.</p>
            </c:when>
            <c:otherwise>
                <p>No data available.</p>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Navigation -->
    <div class="navigation">
        <a href="brand" class="add-link">&larr; Register New Brand</a>
        <span> | </span>
        <a href="home" class="add-link">Back to Dashboard</a>
    </div>

</div>

</body>
</html>
