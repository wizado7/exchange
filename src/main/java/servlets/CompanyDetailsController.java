package servlets;


import config.ConnectionManager;
import dataaccess.entity.Company;
import dataaccess.entity.Vacancy;
import dataaccess.interfaces.CompanyDAO;
import dataaccess.interfaces.VacancyDAO;
import dataaccess.repository.CompanyRepository;
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

@WebServlet("/company-details")
public class CompanyDetailsController extends HttpServlet {

    private CompanyDAO companyDAO;
    private VacancyDAO vacancyDAO;


    @Override
    public void init() throws ServletException {
        try {
            Connection connection = ConnectionManager.getConnection();
            this.companyDAO = CompanyRepository.getInstance();
            this.vacancyDAO = VacancyRepository.getInstance();
        } catch (SQLException e) {
            throw new ServletException("Ошибка подключения к базе данных", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String companyIdParam = req.getParameter("id");
        if (companyIdParam == null || companyIdParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Company ID is required");
            return;
        }

        long companyId;
        try {
            companyId = Long.parseLong(companyIdParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Company ID");
            return;
        }

        Company company = companyDAO.findById(companyId);
        if (company == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Company not found");
            return;
        }

        List<Vacancy> vacancies = vacancyDAO.findByCompanyId(companyId);

        req.setAttribute("company", company);
        req.setAttribute("vacancies", vacancies);

        req.getRequestDispatcher("/company-details.jsp").forward(req, resp);
    }
}
