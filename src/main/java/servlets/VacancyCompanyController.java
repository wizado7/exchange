package servlets;

import config.ConnectionManager;
import dataaccess.entity.Application;
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
import java.util.List;

@WebServlet("/company/vacancy")
public class VacancyCompanyController extends HttpServlet {
    private VacancyDAO vacancyDAO;
    private ApplicationDAO applicationDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = ConnectionManager.getConnection();
            this.vacancyDAO = VacancyRepository.getInstance();
            this.applicationDAO = ApplicationRepository.getInstance();
            this.userDAO = UserRepository.getInstance();
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String vacancyIdParam = req.getParameter("id");

        if (vacancyIdParam == null || vacancyIdParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Vacancy ID is required");
            return;
        }

        try {
            long vacancyId = Long.parseLong(vacancyIdParam);

            Vacancy vacancy = vacancyDAO.findById(vacancyId);
            if (vacancy == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Vacancy not found");
                return;
            }

            List<Application> applications = applicationDAO.findByVacancyId(vacancyId);

            List<User> users = userDAO.findUsersByApplications(applications);

            req.setAttribute("vacancy", vacancy);
            req.setAttribute("applications", applications);
            req.setAttribute("users", users);

            req.getRequestDispatcher("/vacancy-company-details.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Vacancy ID");
        } catch (SQLException e) {
            throw new ServletException("Error retrieving vacancy details", e);
        }
    }
}
