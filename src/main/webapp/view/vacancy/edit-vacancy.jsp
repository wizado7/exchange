<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Vacancy</title>
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
<h1>Edit Vacancy</h1>
<form action="/edit/vacancy" method="post">
    <input type="hidden" name="id" value="${vacancy.id}">
    <div class="form-container">
        <label for="title">Title</label>
        <input type="text" id="title" name="title" value="${vacancy.title}" required>
    </div>
    <div class="form-container">
        <label for="description">Description</label>
        <input type="text" id="description" name="description" value="${vacancy.description}" required>
    </div>
    <div class="form-container">
        <label for="requiredSkills">Required Skills</label>
        <input type="text" id="requiredSkills" name="requiredSkills" value="${vacancy.requiredSkills}" required>
    </div>
    <div class="form-container">
        <label for="status">Status</label>
        <input type="text" id="status" name="status" value="${vacancy.status}" required>
    </div>
    <button type="submit" class="submit-button">Update Vacancy</button>
</form>
<a href="/vacancies" class="action-button">Back to Vacancies</a>
</body>
</html>
