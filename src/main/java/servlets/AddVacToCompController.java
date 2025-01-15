package servlets;

import config.ConnectionManager;
import dataaccess.entity.Vacancy;
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

@WebServlet("/addvacancie")
public class AddVacToCompController extends HttpServlet {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String companyId = req.getParameter("companyId");
        req.setAttribute("companyId", companyId);

        req.getRequestDispatcher("/add-vacnce-to-company.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String requiredSkills = req.getParameter("requiredSkills");
        String status = req.getParameter("status");
        long companyId = Long.parseLong(req.getParameter("companyId"));

        Vacancy vacancy = new Vacancy();
        vacancy.setTitle(title);
        vacancy.setDescription(description);
        vacancy.setRequiredSkills(requiredSkills);
        vacancy.setStatus(status);
        vacancy.setCompanyId(companyId);
        System.out.println(vacancy);
        vacancyDAO.create(vacancy);


        resp.sendRedirect("/company-details?id=" + companyId);
    }
}
