<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vacancies</title>
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

        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 20px 0;
            gap: 10px;
        }

        .pagination a {
            text-decoration: none;
            color: #007bff;
            font-weight: bold;
            padding: 10px 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            transition: background-color 0.3s, color 0.3s;
        }

        .pagination a:hover {
            background-color: #007bff;
            color: white;
        }

        .pagination a.active {
            background-color: #007bff;
            color: white;
            border-color: #007bff;
            pointer-events: none;
        }

        .pagination a.disabled {
            color: #ccc;
            border-color: #ddd;
            pointer-events: none;
        }


    </style>
</head>
<body>
<h1>Available Vacancies</h1>

<form class="add_form" action="/vacancies" method="get">
    <a href="/vacancies/statistics" class="action-button">View Top Vacancies</a>
    <input type="text" name="title" placeholder="Title" value="${titleFilter}">
    <input type="text" name="description" placeholder="Description" value="${descriptionFilter}">
    <button type="submit">Filter</button>
</form>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Description</th>
        <th>Details</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="vacancy" items="${vacancies}">
        <tr>
            <td>${vacancy.id}</td>
            <td>${vacancy.title}</td>
            <td>${vacancy.description}</td>
            <td><a href="vacancies?action=view&id=${vacancy.id}" class="action-button">View</a></td>
            <td>
                <a class="action-button" href="/edit/vacancy?vacancyId=${vacancy.id}">Edit</a>
                <br>
                <form action="/vacancies/delete" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="${vacancy.id}">
                    <button class="button_delete" type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div class="pagination">
    <c:if test="${currentPage > 3}">
        <a href="/vacancies?page=1&title=${titleFilter}&description=${descriptionFilter}">First</a>
        <span>...</span>
    </c:if>

    <c:if test="${currentPage > 1}">
        <a href="/vacancies?page=${currentPage - 1}&title=${titleFilter}&description=${descriptionFilter}">←</a>
    </c:if>

    <c:forEach var="i" begin="${(currentPage - 2) > 1 ? currentPage - 2 : 1}" end="${(currentPage + 2) < totalPages ? currentPage + 2 : totalPages}" step="1">
        <c:if test="${i > 0 && i <= totalPages}">
            <a href="/vacancies?page=${i}&title=${titleFilter}&description=${descriptionFilter}"
               class="${i == currentPage ? 'active' : ''}">${i}</a>
        </c:if>
    </c:forEach>

    <c:if test="${currentPage < totalPages}">
        <a href="/vacancies?page=${currentPage + 1}&title=${titleFilter}&description=${descriptionFilter}">→</a>
    </c:if>

    <c:if test="${currentPage < totalPages - 2}">
        <span>...</span>
        <a href="/vacancies?page=${totalPages}&title=${titleFilter}&description=${descriptionFilter}">Last</a>
    </c:if>
</div>



<a href="/" class="action-button">Back</a>
</body>
</html>
