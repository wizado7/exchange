<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Users</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/style.css" />
    <style>
        .add_form {
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
<h1>Users</h1>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>
                <a href="/company/user-details?id=${user.id}" class="action-button">View Details</a>
                <a class="action-button" href="/edit/user?userId=${user.id}">Edit</a>
                <br><br>
                <form action="/user/delete" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="${user.id}">
                    <button class="button_delete" type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form class="add_form" action="users" method="post">
    <div>
        <label for="name">Name</label>
        <input type="text" id="name" name="name" placeholder="Name" required>
    </div>
    <div>
        <label for="contactDetails">Contact Details</label>
        <input type="text" id="contactDetails" name="contactDetails" placeholder="Contact Details" required>
    </div>
    <div>
        <label for="skills">Skills</label>
        <input type="text" id="skills" name="skills" placeholder="Skills" required>
    </div>
    <div>
        <label for="desiredPosition">Desired Position</label>
        <input type="text" id="desiredPosition" name="desiredPosition" placeholder="Desired Position" required>
    </div>
    <button type="submit">Add User</button>
</form>
<a href="/" class="action-button">Back</a>
</body>
</html>
