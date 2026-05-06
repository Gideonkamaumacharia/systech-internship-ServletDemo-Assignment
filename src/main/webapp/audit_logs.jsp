<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>System Audit Logs</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f7f6; padding: 20px; }
        .log-container { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th { background-color: #2c3e50; color: white; text-align: left; padding: 12px; }
        td { padding: 12px; border-bottom: 1px solid #eee; }
        tr:hover { background-color: #f9f9f9; }
        .badge { padding: 4px 8px; border-radius: 4px; font-size: 0.85em; font-weight: bold; }
        .action-create { background: #d4edda; color: #155724; }
        .action-delete { background: #f8d7da; color: #721c24; }
        .user-tag { color: #3498db; font-weight: bold; }
        .timestamp { color: #7f8c8d; font-size: 0.9em; }
    </style>
</head>
<body>

<div class="log-container">
    <h2>System Audit Logs</h2>
    <p>Monitoring all administrative actions and database changes.</p>
    <hr>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Time</th>
            <th>User</th>
            <th>Action</th>
            <th>Details</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="log" items="${dataList}">
            <tr>
                <td>${log.id}</td>
                <td class="timestamp">
                        <%-- Formatting the Date object --%>
                    <fmt:formatDate value="${log.timeStamp}" pattern="yyyy-MM-dd HH:mm:ss" />
                </td>
                <td>
                    <c:choose>
                        <c:when test="${not empty log.user}">
                            <span class="user-tag">@${log.user.username}</span>
                        </c:when>
                        <c:otherwise>
                            <span style="color: #ccc;">System</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                        <span class="badge ${log.actionPerformed.contains('CREATE') ? 'action-create' : ''}">
                                ${log.actionPerformed}
                        </span>
                </td>
                <td>${log.details}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <br>
    <a href="./home">Back to Dashboard</a>
</div>

</body>
</html>