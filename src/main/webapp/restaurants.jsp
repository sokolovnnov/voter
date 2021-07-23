<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>--%>
<html>
<head>
    <title>Restaurants</title>
    <%--    <link rel="stylesheet" href="css/style.css">--%>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Restaurants</h2>

    <hr/>
    <%--    <a href="meals?action=create">Add Meal</a>--%>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Restaurant title</th>
            <th></th>
            <%-- <th></th>
             <th></th>
             <th></th>--%>
        </tr>
        </thead>
        <c:forEach items="${restaurants}" var="restaurant">
            <jsp:useBean id="restaurant" type="ru.antisida.voter.model.Restaurant"/>
            <tr>
                <td>${restaurant.name}</td>
                <td><a href="restaurants?action=vote&id=${restaurant.id}">Vote!</a></td>
                    <%--                <td>${restaurant.name}</td>
                                    <td>${restaurant.name}</td>
                                    <td>${restaurant.calories}</td>
                                    <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                                    <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>--%>
            </tr>
        </c:forEach>
    </table>
    <hr>

    <c:forEach items="${menus}" var="menu">
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th>${menu.restaurant.name}</th>
                <th>Price</th>
                <th>Vote!</th>
                    <%--  <th></th>
                      <th></th>
                      <th></th>--%>
            </tr>
            </thead>

            <c:forEach items="${menu.mealList}" var="meal">
            <tr>

                    <jsp:useBean id="meal" type="ru.antisida.voter.model.Meal"/>
                    <td>${meal.name}</td>
                    <td>${meal.price}</td>

                    <%--                <td>${restaurant.name}</td>
                                    <td>${restaurant.name}</td>
                                    <td>${restaurant.calories}</td>
                                    <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                                    <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>--%>
            </tr>
            </c:forEach>
        </table>
        <br>
    </c:forEach>
    Последний голос юзера с id ${lastvote.userId} за рестоаран с id ${lastvote.id}

</section>
</body>
</html>