<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Registered Users</title>
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
    <h1>Registered Users</h1>

    <c:if test="${empty dataList}">
        <p>There are no users yet.</p>
    </c:if>
    <table>
        <tr>
            <th>Name</th>
            <th>Password</th>
            <th>Role</th>


        </tr>
        <c:forEach var="user" items="${dataList}">
            <tr>
                <td>${user.username}</td>
                <td>${user.password}</td>

                <td>
                    <c:if test="${userBean.isAdmin(user)}">
                        <span style="background: gold; padding: 2px 5px; font-size: 10px;">ADMIN</span>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:choose>
        <c:when test="${dataList.size() gt 5}">
            <p style="color: red;">The room is crowded!</p>
        </c:when>
        <c:when test="${not empty dataList and dataList.size() lt 5}">
            <p>We have room for many users!</p>
        </c:when>
        <c:otherwise>
            <p>There are no users yet.</p>
        </c:otherwise>
    </c:choose>
    <div class="navigation">
        <a href="user" class="add-link">&larr; Register New User</a>
        <span> | </span>
        <%--<a href="user_list" class="nav-link">View Registered Users</a>--%>
    </div>
</div>
</body>
</html>