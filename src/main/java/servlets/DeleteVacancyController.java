package servlets;

import config.ConnectionManager;
import dataaccess.interfaces.VacancyDAO;
import dataaccess.repository.VacancyRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/vacancies/delete")
public class DeleteVacancyController extends HttpServlet {
    private VacancyDAO vacancyDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = ConnectionManager.getConnection();
            this.vacancyDAO = VacancyRepository.getInstance();
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long vacancyId = Long.parseLong(req.getParameter("id"));

        vacancyDAO.delete(vacancyId);

        resp.sendRedirect("/vacancies");
    }
}
