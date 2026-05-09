<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Vehicle Categories</title>

    <style>

        body {
            font-family: 'Segoe UI', Arial, sans-serif;

            /* UPDATED: professional blue gradient background */
            background: linear-gradient(135deg, #0f172a, #1e3c72);

            padding: 20px;
            color: #e2e8f0;
        }

        .container {
            max-width: 1000px;
            margin: 0 auto;
        }

        .header-flex {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid rgba(255,255,255,0.15);
            padding-bottom: 15px;
            margin-bottom: 25px;
        }

        h2 {
            color: #ffffff;
        }

        /* GRID */
        .grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 20px;
        }

        /* CARD */
        .category-card {

            /* glass effect instead of flat white */
            background: rgba(255, 255, 255, 0.06);
            backdrop-filter: blur(10px);

            border-radius: 14px;
            padding: 20px;

            border: 1px solid rgba(255,255,255,0.12);

            box-shadow: 0 10px 30px rgba(0,0,0,0.25);

            transition: 0.3s ease;
        }

        .category-card:hover {
            transform: translateY(-5px);
            border-color: rgba(52, 152, 219, 0.5);
        }

        .category-name {
            font-size: 1.4em;
            color: #ffffff;
            margin-bottom: 10px;
        }

        .description {
            color: #cbd5e1;
            font-style: italic;
            min-height: 40px;
        }

        /* STATS */
        .stats {
            margin-top: 15px;
            padding-top: 15px;
            border-top: 1px solid rgba(255,255,255,0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
            color: #e2e8f0;
        }

        /* BADGE (accent blue, controlled usage) */
        .badge {
            background: rgba(56, 189, 248, 0.15);
            color: #38bdf8;
            padding: 4px 12px;
            border-radius: 20px;
            font-weight: bold;
            border: 1px solid rgba(56, 189, 248, 0.3);
        }

        /* BUTTON */
        .btn-add {
            background: linear-gradient(to right, #38bdf8, #1e3c72);
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 10px;
            font-weight: 600;
            transition: 0.3s ease;
        }

        .btn-add:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.3);
        }

        .btn-view {
            color: #38bdf8;
            text-decoration: none;
            font-size: 0.9em;
            font-weight: bold;
        }

        .btn-view:hover {
            text-decoration: underline;
        }

        /* SMALL LIST */
        .recent {
            margin-top: 10px;
            font-size: 0.85em;
            color: #94a3b8;
        }

        /* BACK LINK */
        .back {
            margin-top: 30px;
            color: #94a3b8;
            text-decoration: none;
            display: inline-block;
        }

        .back:hover {
            color: #ffffff;
        }

    </style>

</head>

<body>

<div class="container">

    <div class="header-flex">
        <h2>${Category.class.getAnnotation(app.framework.ShowroomTable.class).label()}</h2>
        <a href="./category" class="btn-add">+ Add New Category</a>
    </div>

    <div class="grid">

        <c:forEach var="category" items="${dataList}">

            <div class="category-card">

                <h3 class="category-name">${category.name}</h3>

                <p class="description">${category.description}</p>

                <div class="stats">

                    <span>
                        <strong>Total Inventory:</strong>
                        <span class="badge">${fn:length(category.cars)}</span>
                    </span>

                    <a href="#" class="btn-view">Details →</a>

                </div>

                <c:if test="${not empty category.cars}">
                    <div class="recent">
                        Recent:
                        <c:forEach var="car" items="${category.cars}" end="2">
                            ${car.carModel},
                        </c:forEach>...
                    </div>
                </c:if>

            </div>

        </c:forEach>

    </div>

    <a href="./category" class="back">&larr; Back to Dashboard</a>

</div>

</body>
</html>