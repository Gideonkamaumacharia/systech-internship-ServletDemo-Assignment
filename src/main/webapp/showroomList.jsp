<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Showroom Inventory Management</title>

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

        h2 {
            text-align: center;
            color: #ffffff;
            margin-bottom: 20px;
        }

        .card-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            justify-content: center;
        }

        /* CARD */
        .showroom-card {

            width: 320px;

            background: rgba(15, 23, 42, 0.85);
            backdrop-filter: blur(12px);

            border: 1px solid rgba(255,255,255,0.08);
            border-radius: 20px;

            padding: 20px;

            box-shadow: 0 20px 50px rgba(0,0,0,0.45);

            transition: 0.3s ease;
        }

        .showroom-card:hover {
            transform: translateY(-5px);
            border-color: rgba(56,189,248,0.4);
        }

        h3 {
            color: #ffffff;
            margin-bottom: 10px;
        }

        p {
            color: #cbd5e1;
            font-size: 0.9rem;
            margin: 6px 0;
        }

        /* STATUS BADGES */
        .status-badge {
            padding: 5px 10px;
            border-radius: 10px;
            font-size: 0.75rem;
            font-weight: 600;
            display: inline-block;
        }

        .full {
            background: rgba(239, 68, 68, 0.15);
            color: #ef4444;
            border: 1px solid rgba(239, 68, 68, 0.3);
        }

        .warning {
            background: rgba(245, 158, 11, 0.15);
            color: #f59e0b;
            border: 1px solid rgba(245, 158, 11, 0.3);
        }

        .available {
            background: rgba(34, 197, 94, 0.15);
            color: #22c55e;
            border: 1px solid rgba(34, 197, 94, 0.3);
        }

        /* LINKS */
        a {
            color: #38bdf8;
            text-decoration: none;
            font-weight: 500;
        }

        a:hover {
            text-decoration: underline;
        }

        /* DIVIDER */
        hr {
            border: none;
            border-top: 1px solid rgba(255,255,255,0.08);
            margin: 15px 0;
        }

        /* ALERT */
        .alert {
            margin-top: 10px;
            padding: 10px;
            border-radius: 10px;
            font-size: 0.85rem;
            background: rgba(56,189,248,0.08);
            border: 1px solid rgba(56,189,248,0.2);
            color: #cbd5e1;
        }

        /* FOOTER LINK */
        .bottom-link {
            display: block;
            text-align: center;
            margin-top: 30px;
            color: #94a3b8;
        }

        .bottom-link:hover {
            color: #ffffff;
        }

    </style>

</head>

<body>

<h2>${Showroom.class.getAnnotation(app.framework.ShowroomTable.class).label()}</h2>

<div class="card-container">

    <c:forEach var="showroom" items="${dataList}">

        <div class="showroom-card">

            <h3>${showroom.locationName}</h3>

            <p>
                <strong>Manager:</strong>
                <c:choose>
                    <c:when test="${not empty showroom.manager}">
                        ${showroom.manager.username}
                    </c:when>
                    <c:otherwise>
                        <span class="status-badge warning">Unassigned</span>
                    </c:otherwise>
                </c:choose>
            </p>

            <p>
                <strong>Current Inventory:</strong>
                    ${fn:length(showroom.cars)} Cars
            </p>

            <div>

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

            <c:if test="${empty showroom.cars}">
                <p style="color:#94a3b8; font-style: italic;">
                    No vehicles currently parked here.
                </p>
            </c:if>

            <c:if test="${not empty param.showroom_Id}">
                <div class="alert">
                    Showing cars for Showroom ID: ${param.showroom_Id}
                    | <a href="./list">Show All Cars</a>
                </div>
            </c:if>

            <p style="margin-top:10px;">
                <a href="${pageContext.request.contextPath}/list?showroomId=${showroom.id}">
                    View Fleet →
                </a>
            </p>

        </div>

    </c:forEach>

</div>

<a href="./showroom" class="bottom-link">+ Add New Branch</a>
<a href="home" class="add-link">Back to Dashboard</a>
</body>
</html>