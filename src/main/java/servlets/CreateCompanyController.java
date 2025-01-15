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

@WebServlet("/add-company")
public class CreateCompanyController extends HttpServlet {
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
        req.getRequestDispatcher("/create-company.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String contactDetails = req.getParameter("contactDetails");
        String description = req.getParameter("description");
        String address = req.getParameter("address");

        Company company = new Company();
        company.setName(name);
        company.setContactDetails(contactDetails);
        company.setDescription(description);
        company.setAddress(address);

        companyDAO.create(company);

        resp.sendRedirect("/companies");
    }
}
