package servlets;

import config.ConnectionManager;
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

@WebServlet("/company/delete")
public class DeleteCompanyController extends HttpServlet {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Company ID is missing.");
            return;
        }

        try {
            long companyId = Long.parseLong(idParam);
            companyDAO.deleteCompanyWithVacancies(companyId);
            resp.sendRedirect("/companies");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Company ID.");
        } catch (SQLException e) {
            throw new ServletException("Error while deleting company.", e);
        }
    }
}
