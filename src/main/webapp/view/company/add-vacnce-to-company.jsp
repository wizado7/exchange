<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Vacancy</title>
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
<h1>Add New Vacancy</h1>

<form class="add_form" action="/addvacancie" method="post">
    <input type="hidden" name="companyId" value="${companyId}">
    <div class="form-container">
        <label for="title">Title</label>
        <input type="text" id="title" name="title" placeholder="Vacancy Title" required>
    </div>
    <div class="form-container">
        <label for="description">Description</label>
        <input type="text" id="description" name="description" placeholder="Short Description" required>
    </div>
    <div class="form-container">
        <label for="requiredSkills">Required Skills</label>
        <input type="text" id="requiredSkills" name="requiredSkills" placeholder="Skills Required" required>
    </div>
    <div class="form-container">
        <label for="status">Required Skills</label>
        <input type="text" id="status" name="status" placeholder="Status" required>
    </div>
    <button type="submit" class="submit-button">Add Vacancy</button>
</form>

<a href="/companies" class="action-button">Back to Companies</a>
</body>
</html>
