<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Navigation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(to bottom, #ebeeff, #cde3ff);
        }

        .navbar {
            background-color: #0056b3;
            overflow: hidden;
            padding: 10px 0;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .navbar a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
            font-size: 18px;
            font-weight: bold;
            transition: background-color 0.3s ease, transform 0.3s ease;
        }

        .navbar a:hover {
            background-color: #0056b3;
            transform: scale(1.05);
        }

        .navbar a.active {
            background-color: #0056b3;
        }

        .content {
            margin: 20px;
            text-align: center;
            color: #0056b3;
        }

        .content h1 {
            font-size: 36px;
            font-weight: bold;
            text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.2);
        }

        .content p {
            font-size: 18px;
            margin-top: 10px;
            line-height: 1.6;
        }
    </style>

</head>
<body>
<div class="navbar">
    <a href="/companies" class="active">Companies</a>
    <a href="/users">Users</a>
    <a href="/vacancies">Vacancies</a>
</div>
<div class="content">
    <h1>Welcome to the Job Exchange Platform</h1>
    <p>Select a section from the navigation menu above to get started.</p>
</div>
</body>
</html>
