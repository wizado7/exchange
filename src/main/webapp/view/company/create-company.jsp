<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Company</title>
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
<h1>Add New Company</h1>
<form class="add_form" action="/add-company" method="post">
    <div class="form-container">
        <label for="name">Company Name</label>
        <input type="text" id="name" name="name" placeholder="Enter company name" required>
    </div>
    <div class="form-container">
        <label for="contactDetails">Contact Details</label>
        <input type="text" id="contactDetails" name="contactDetails" placeholder="Enter contact details" required>
    </div>
    <div class="form-container">
        <label for="description">Description</label>
        <input type="text" id="description" name="description" placeholder="Enter company description" required>
    </div>
    <div class="form-container">
        <label for="address">Address</label>
        <input type="text" id="address" name="address" placeholder="Enter company address" required>
    </div>
    <button type="submit" class="submit-button">Add Company</button>
</form>
<a href="/companies" class="action-button">Back to Companies</a>
</body>
</html>
