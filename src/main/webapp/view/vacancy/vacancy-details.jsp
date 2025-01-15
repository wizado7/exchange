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
<h1>${vacancy.title}</h1>
<p><strong>Description:</strong> ${vacancy.description}</p>
<p><strong>Required Skills:</strong> ${vacancy.requiredSkills}</p>
<p><strong>Status:</strong> ${vacancy.status}</p>

<h2>Apply for this Vacancy</h2>
<form action="/vacancy-details" method="post">
    <input type="hidden" name="vacancyId" value="${vacancy.id}">
    <div class="form-container">
        <label for="name">Your Name</label>
        <input type="text" id="name" name="name" placeholder="Enter your name" required>
    </div>
    <div class="form-container">
        <label for="contactDetails">Contact Details</label>
        <input type="text" id="contactDetails" name="contactDetails" placeholder="Enter your contact details" required>
    </div>
    <button type="submit" class="submit-button">Submit Application</button>
</form>
<a href="/vacancies" class="action-button">Back to Vacancies</a>
</body>
</html>
