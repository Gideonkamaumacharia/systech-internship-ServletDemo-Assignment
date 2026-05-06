<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Vehicle Categories</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; background-color: #f8f9fa; padding: 20px; }
        .container { max-width: 1000px; margin: 0 auto; }
        .header-flex { display: flex; justify-content: space-between; align-items: center; border-bottom: 2px solid #34495e; padding-bottom: 10px; margin-bottom: 20px; }
        .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 20px; }
        .category-card { background: white; border-radius: 8px; padding: 20px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); border-left: 5px solid #3498db; }
        .category-name { font-size: 1.4em; color: #2c3e50; margin: 0 0 10px 0; }
        .description { color: #7f8c8d; font-style: italic; min-height: 40px; }
        .stats { margin-top: 15px; padding-top: 15px; border-top: 1px solid #eee; display: flex; justify-content: space-between; align-items: center; }
        .badge { background: #3498db; color: white; padding: 4px 12px; border-radius: 20px; font-weight: bold; }
        .btn-add { background: #27ae60; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; }
        .btn-view { color: #3498db; text-decoration: none; font-size: 0.9em; font-weight: bold; }
    </style>
</head>
<body>

<div class="container">
    <div class="header-flex">
        <h2>${Category.class.getAnnotation(app.framework.ShowroomTable.class).label()}</h2>
        <a href="./register_category" class="btn-add">+ Add New Category</a>
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
                        <%-- Note: You could implement a /car_list?categoryId=${category.id} filter later --%>
                    <a href="#" class="btn-view">Details &rarr;</a>
                </div>

                    <%-- Show a small list of car models if inventory exists --%>
                <c:if test="${not empty category.cars}">
                    <div style="margin-top: 10px; font-size: 0.85em; color: #95a5a6;">
                        Recent:
                        <c:forEach var="car" items="${category.cars}" end="2">
                            ${car.carModel},
                        </c:forEach>...
                    </div>
                </c:if>
            </div>
        </c:forEach>
    </div>

    <div style="margin-top: 30px;">
        <a href="./category" style="color: #7f8c8d;">&larr; Back to Dashboard</a>
    </div>
</div>

</body>
</html>