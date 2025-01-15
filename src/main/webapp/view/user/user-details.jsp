<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Details</title>
    <link rel="stylesheet" type="text/css" href="/style.css" />
</head>
<body>
<h1>User Details</h1>

<c:if test="${not empty user}">
    <table>
        <tr>
            <th>ID</th>
            <td>${user.id}</td>
        </tr>
        <tr>
            <th>Name</th>
            <td>${user.name}</td>
        </tr>
        <tr>
            <th>Contact Details</th>
            <td>${user.contactDetails}</td>
        </tr>
        <tr>
            <th>Skills</th>
            <td>${user.skills}</td>
        </tr>
        <tr>
            <th>Desired Position</th>
            <td>${user.desiredPosition}</td>
        </tr>
        <tr>
            <th>Employment Status</th>
            <td>${user.employmentStatus}</td>
        </tr>
    </table>
</c:if>


<a href="javascript:history.back()" class="action-button">Back</a>
</body>
</html>
