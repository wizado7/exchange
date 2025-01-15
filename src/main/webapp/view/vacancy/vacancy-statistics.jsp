<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Top Vacancies</title>
    <link rel="stylesheet" type="text/css" href="/style.css" />
</head>
<body>
<h1>Top 3 Vacancies by Applications</h1>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Description</th>
        <th>Applications Count</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="vacancy" items="${topVacancies}">
        <tr>
            <td>${vacancy.id}</td>
            <td>${vacancy.title}</td>
            <td>${vacancy.description}</td>
            <td>${vacancy.applicationsCount}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="/vacancies" class="action-button">Back to Vacancies</a>
</body>
</html>
