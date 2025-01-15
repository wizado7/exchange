<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Company</title>
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
<h1>Edit Company</h1>

<form class="add_form" action="/edit/company" method="post">
    <input type="hidden" name="id" value="${company.id}">

    <div class="form-container">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="${company.name}" required>
    </div>

    <div class="form-container">
        <label for="contactDetails">Contact Details:</label>
        <input type="text" id="contactDetails" name="contactDetails" value="${company.contactDetails}" required>
    </div>

    <div class="form-container">
        <label for="description">Description:</label>
        <textarea id="description" name="description" required>${company.description}</textarea>
    </div>

    <div class="form-container">
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" value="${company.address}" required>
    </div>

    <button type="submit" class="submit-button">Save Changes</button>
    <br><br>
    <a href="/companies" class="action-button">Cancel</a>
</form>
</body>
</html>
