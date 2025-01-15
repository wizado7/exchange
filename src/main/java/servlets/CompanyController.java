package servlets;

import config.ConnectionManager;
import dataaccess.entity.Company;
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

@WebServlet("/companies")
public class CompanyController extends HttpServlet {
    private CompanyDAO companyDAO;
    private VacancyDAO vacancyDAO;


    @Override
    public void init() throws ServletException {
        try {
            this.companyDAO = CompanyRepository.getInstance();
            this.vacancyDAO = VacancyRepository.getInstance();
        } catch (SQLException e) {
            throw new ServletException("Ошибка подключения к базе данных", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("details".equals(action)) {
            long companyId = Long.parseLong(req.getParameter("id"));
            req.setAttribute("company", companyDAO.findById(companyId));
            req.setAttribute("vacancies", vacancyDAO.findByCompanyId(companyId));
            req.getRequestDispatcher("/company-details.jsp").forward(req, resp);
        } else {
            req.setAttribute("companies", companyDAO.findAllWithVacancyCount());
            req.getRequestDispatcher("/companies.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String contact_details = req.getParameter("contact_details");
            String description = req.getParameter("description");
            String address = req.getParameter("address");

            Company newCompany = new Company();
            newCompany.setName(name);
            newCompany.setContactDetails(contact_details);
            newCompany.setDescription(description);
            newCompany.setAddress(address);
            companyDAO.create(newCompany);
            resp.sendRedirect("/companies");
        } catch (Exception e) {
            throw new ServletException("Ошибка добавления новой компании", e);
        }
    }
}
