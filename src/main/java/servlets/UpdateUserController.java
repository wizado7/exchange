package servlets;

import dataaccess.entity.User;
import dataaccess.interfaces.UserDAO;
import dataaccess.repository.UserRepository;
import config.ConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/edit/user")
public class UpdateUserController extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = ConnectionManager.getConnection();
            this.userDAO = UserRepository.getInstance();
        } catch (SQLException e) {
            throw new ServletException("Unable to connect to database", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdParam = req.getParameter("userId");

        if (userIdParam == null || userIdParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required");
            return;
        }

        try {
            long userId = Long.parseLong(userIdParam);

            User user = userDAO.findById(userId);

            if (user == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
                return;
            }

            req.setAttribute("user", user);
            req.getRequestDispatcher("/edit-user.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdParam = req.getParameter("id");
        String name = req.getParameter("name");
        String contactDetails = req.getParameter("contactDetails");
        String skills = req.getParameter("skills");
        String desiredPosition = req.getParameter("desiredPosition");
        String employmentStatus = req.getParameter("employmentStatus");

        if (userIdParam == null || userIdParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "User ID is required");
            return;
        }

        try {
            long userId = Long.parseLong(userIdParam);

            User user = new User();
            user.setId(userId);
            user.setName(name);
            user.setContactDetails(contactDetails);
            user.setSkills(skills);
            user.setDesiredPosition(desiredPosition);
            user.setEmploymentStatus(employmentStatus);

            userDAO.update(user);

            resp.sendRedirect("/users");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid User ID");
        }
    }
}
