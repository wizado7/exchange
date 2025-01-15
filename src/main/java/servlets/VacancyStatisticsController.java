package servlets;

import dataaccess.entity.Vacancy;
import dataaccess.interfaces.VacancyDAO;
import dataaccess.repository.VacancyRepository;
import config.ConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/vacancies/statistics")
public class VacancyStatisticsController extends HttpServlet {
    private VacancyDAO vacancyDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = ConnectionManager.getConnection();
            this.vacancyDAO = VacancyRepository.getInstance();
        } catch (SQLException e) {
            throw new ServletException("Unable to connect to database", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Vacancy> topVacancies = vacancyDAO.getTopVacanciesByApplications(3);
            req.setAttribute("topVacancies", topVacancies);
            req.getRequestDispatcher("/vacancy-statistics.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error fetching top vacancies", e);
        }
    }
}
