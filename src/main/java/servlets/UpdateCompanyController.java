package servlets;

import config.ConnectionManager;
import dataaccess.entity.Company;
import dataaccess.interfaces.CompanyDAO;
import dataaccess.repository.CompanyRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/edit/company")
public class UpdateCompanyController extends HttpServlet {
    private CompanyDAO companyDAO;

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = ConnectionManager.getConnection();
            this.companyDAO = CompanyRepository.getInstance();
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("companyId");

        if (idParam == null || idParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Company ID is missing.");
            return;
        }

        try {
            long companyId = Long.parseLong(idParam);
            Company company = companyDAO.findById(companyId);

            if (company == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Company not found.");
                return;
            }

            req.setAttribute("company", company);
            req.getRequestDispatcher("/edit-company.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Company ID.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String name = req.getParameter("name");
        String contactDetails = req.getParameter("contactDetails");
        String description = req.getParameter("description");
        String address = req.getParameter("address");

        if (idParam == null || idParam.isEmpty() || name == null || contactDetails == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "All fields are required.");
            return;
        }

        try {
            long companyId = Long.parseLong(idParam);
            Company company = new Company();
            company.setId(companyId);
            company.setName(name);
            company.setContactDetails(contactDetails);
            company.setDescription(description);
            company.setAddress(address);

            companyDAO.update(company);
            resp.sendRedirect("/companies");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Company ID.");
        }
    }
}
