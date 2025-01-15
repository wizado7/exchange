package servlets;


import config.ConnectionManager;
import dataaccess.entity.User;
import dataaccess.entity.Vacancy;
import dataaccess.interfaces.ApplicationDAO;
import dataaccess.interfaces.UserDAO;
import dataaccess.interfaces.VacancyDAO;
import dataaccess.repository.ApplicationRepository;
import dataaccess.repository.UserRepository;
import dataaccess.repository.VacancyRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/vacancy-details")
public class VacancyDetailsController extends HttpServlet {

    private VacancyDAO vacancyDAO;
    private UserDAO userDAO;
    private ApplicationDAO applicationDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = ConnectionManager.getConnection();
            this.vacancyDAO = VacancyRepository.getInstance();
            this.userDAO = UserRepository.getInstance();
            this.applicationDAO = ApplicationRepository.getInstance();
        } catch (SQLException e) {
            throw new ServletException("Ошибка подключения к базе данных, e");
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String vacancyIdParam = req.getParameter("id");
        if (vacancyIdParam == null || vacancyIdParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Vacancy ID is required");
            return;
        }

        long vacancyId;
        try {
            vacancyId = Long.parseLong(vacancyIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Vacancy ID");
            return;
        }

        Vacancy vacancy = vacancyDAO.findById(vacancyId);
        if (vacancy == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Vacancy not found");
            return;
        }

        req.setAttribute("vacancy", vacancy);

        req.getRequestDispatcher("/vacancy-details.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String contactDetails = req.getParameter("contactDetails");
        String vacancyIdParam = req.getParameter("vacancyId");

        if (name == null || contactDetails == null || vacancyIdParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name, contact details, and vacancy ID are required");
            return;
        }

        long vacancyId;
        try {
            vacancyId = Long.parseLong(vacancyIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Vacancy ID");
            return;
        }

        User user = userDAO.findByName(name);
        if (user == null) {
            user = new User();
            user.setName(name);
            user.setContactDetails(contactDetails);
            userDAO.create(user);
        }

        boolean applicationSubmitted = applicationDAO.applyForVacancy(user.getId(), vacancyId);

        if (applicationSubmitted) {
            resp.sendRedirect("/vacancy-details?id=" + vacancyId + "&success=true");
        } else {
            resp.sendRedirect("/vacancy-details?id=" + vacancyId + "&error=true");
        }
    }

}
