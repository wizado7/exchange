<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>${company.name} - Vacancies</title>
    <link rel="stylesheet" type="text/css" href="/style.css" />
</head>
<body>
<h1>Vacancies at ${company.name}</h1>
<table>
    <thead>
    <tr>
        <th>Vacancy Title</th>
        <th>Description</th>
        <th>Required Skills</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="vacancy" items="${vacancies}">
        <tr>
            <td>${vacancy.title}</td>
            <td>${vacancy.description}</td>
            <td>${vacancy.requiredSkills}</td>
            <td>${vacancy.status}</td>
            <td>
                <a href="/company/vacancy?id=${vacancy.id}" class="action-button">View Vacancy</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="/addvacancie?companyId=${company.id}" class="action-button">Add new vacancy</a>
<a href="/companies" class="action-button">Back to Companies</a>
</body>
</html>
