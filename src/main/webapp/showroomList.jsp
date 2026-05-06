<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Showroom Inventory Management</title>
    <style>
        .status-badge { padding: 5px 10px; border-radius: 4px; font-size: 0.8em; font-weight: bold; }
        .full { background-color: #ffdde1; color: #c0392b; }
        .available { background-color: #d4edda; color: #155724; }
        .warning { background-color: #fff3cd; color: #856404; }
        .card-container { display: flex; flex-wrap: wrap; gap: 20px; padding: 20px; }
        .showroom-card { border: 1px solid #ddd; border-radius: 8px; width: 300px; padding: 15px; box-shadow: 2px 2px 10px #eee; }
    </style>
</head>
<body>

<h2>${Showroom.class.getAnnotation(app.framework.ShowroomTable.class).label()}</h2>
<hr>

<div class="card-container">
    <%-- Assuming 'dataList' is passed from your BaseActionList --%>
    <c:forEach var="showroom" items="${dataList}">
        <div class="showroom-card">
            <h3>${showroom.locationName}</h3>
            <p><strong>Manager:</strong>
                <c:choose>
                    <c:when test="${not empty showroom.manager}">
                        ${showroom.manager.username}
                    </c:when>
                    <c:otherwise>
                        <span class="warning">Unassigned</span>
                    </c:otherwise>
                </c:choose>
            </p>

            <p><strong>Current Inventory:</strong>
                    ${fn:length(showroom.cars)} Cars
            </p>

                <%-- Logic using Relational and Logical Operators --%>
            <div class="status-info">
                <c:set var="usagePercent" value="${(fn:length(showroom.cars) / showroom.capacity) * 100}" />

                <c:if test="${usagePercent >= 90 and showroom.capacity gt 0}">
                    <span class="status-badge full">CRITICAL: ${usagePercent}% Full</span>
                </c:if>

                <c:if test="${usagePercent lt 90 and usagePercent ge 50}">
                    <span class="status-badge warning">BUSY: ${usagePercent}% Occupied</span>
                </c:if>

                <c:if test="${usagePercent lt 50 or showroom.capacity eq 0}">
                    <span class="status-badge available">Space Available</span>
                </c:if>
            </div>

            <hr>

                <%-- Conditional logic for empty car lists --%>
            <c:if test="${empty showroom.cars}">
                <p style="color: gray; font-style: italic;">No vehicles currently parked here.</p>
            </c:if>

            <c:if test="${not empty param.showroomId}">
                <div class="alert alert-info">
                    Showing cars for Showroom ID: ${param.showroomId}
                    | <a href="./list">Show All Cars</a>
                </div>
            </c:if>

            <a href="${pageContext.request.contextPath}/list?showroomId=${showroom.id}">View Fleet</a>
        </div>
    </c:forEach>
</div>

<br>
<a href="./showroom">Add New Branch</a>

</body>
</html>