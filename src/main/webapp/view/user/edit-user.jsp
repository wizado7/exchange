<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit User</title>
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
<h1>Edit User</h1>

<form class="add_form" action="/edit/user" method="post">
    <input type="hidden" name="id" value="${user.id}">

    <label for="name">Name:</label>
    <input type="text" id="name" name="name" value="${user.name}" required><br>

    <label for="contactDetails">Contact Details:</label>
    <input type="text" id="contactDetails" name="contactDetails" value="${user.contactDetails}" required><br>

    <label for="skills">Skills:</label>
    <input type="text" id="skills" name="skills" value="${user.skills}" required><br>

    <label for="desiredPosition">Desired Position:</label>
    <input type="text" id="desiredPosition" name="desiredPosition" value="${user.desiredPosition}" required><br>

    <label for="employmentStatus">Employment Status:</label>
    <input type="text" id="employmentStatus" name="employmentStatus" value="${user.employmentStatus}" required><br>

    <button type="submit" class="action-button">Update User</button>
</form>

<a href="javascript:history.back()" class="action-button">Back</a>
</body>
</html>
