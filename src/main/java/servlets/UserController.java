package servlets;

import config.ConnectionManager;
import dataaccess.entity.User;
import dataaccess.interfaces.CompanyDAO;
import dataaccess.interfaces.UserDAO;
import dataaccess.repository.CompanyRepository;
import dataaccess.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/users")
public class UserController extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            this.userDAO = UserRepository.getInstance();
        } catch (SQLException e) {
            throw new ServletException("Ошибка подключения к базе данных, e");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null || action.isEmpty()) {
            req.setAttribute("users", userDAO.findAll());
            req.getRequestDispatcher("/users.jsp").forward(req, resp);
        } else if (action.equals("view")) {
            long id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("user", userDAO.findById(id));
            req.getRequestDispatcher("/user-details.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String contactDetails = req.getParameter("contactDetails");
        String skills = req.getParameter("skills");
        String desiredPosition = req.getParameter("desiredPosition");

        User newUser = new User();
        newUser.setName(name);
        newUser.setContactDetails(contactDetails);
        newUser.setSkills(skills);
        newUser.setDesiredPosition(desiredPosition);
        userDAO.create(newUser);
        resp.sendRedirect("/users");
    }
}