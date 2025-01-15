<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Companies</title>
    <link rel="stylesheet" type="text/css" href="/style.css" />
</head>
<body>
<h1>Companies</h1>
<table>
    <thead>
    <tr>
        <th>Company Name</th>
        <th>Contact Details</th>
        <th>Description</th>
        <th>Address</th>
        <th>Vacancies Count</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="company" items="${companies}">
        <tr>
            <td>${company.name}</td>
            <td>${company.contactDetails}</td>
            <td>${company.description}</td>
            <td>${company.address}</td>
            <td>${company.vacancyCount}</td>
            <td>
                <a href="company-details?id=${company.id}" class="action-button">View Details</a>
                <a class="action-button" href="/edit/company?companyId=${company.id}">Edit</a>
                <br>
                <form action="/company/delete" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="${company.id}">
                    <button class="button_delete" type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="/add-company" class="action-button">Add new company</a>
<a href="/" class="action-button">Go back</a>
</body>
</html>
