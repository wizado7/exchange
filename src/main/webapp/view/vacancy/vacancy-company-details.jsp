<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>${vacancy.title} - Details</title>
    <link rel="stylesheet" type="text/css" href="/style.css" />
    <style>
        form {
            width: 50%;
            margin: 20px auto;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
    </style>
</head>
<body>
<h1>${vacancy.title} - Vacancy Details</h1>

<h2>Vacancy Information</h2>
<p><strong>Description:</strong> ${vacancy.description}</p>
<p><strong>Required Skills:</strong> ${vacancy.requiredSkills}</p>
<p><strong>Status:</strong> ${vacancy.status}</p>

<h2>Users Applied</h2>
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Contact Details</th>
        <th>Skills</th>
        <th>Desired Position</th>
        <th>Employment Status</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.name}</td>
            <td>${user.contactDetails}</td>
            <td>${user.skills}</td>
            <td>${user.desiredPosition}</td>
            <td>${user.employmentStatus}</td>
            <td>
                <a href="user-details?id=${user.id}" class="action-button">View Details</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<a href="/companies" class="action-button">Back to Companies</a>
</body>
</html>
