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
import java.util.List;

@WebServlet("/vacancies")
public class VacancyController extends HttpServlet {
    private static final int PAGE_SIZE = 5;
    private VacancyDAO vacancyDAO;

    @Override
    public void init() throws ServletException {
        try {
            this.vacancyDAO = VacancyRepository.getInstance();
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("view".equals(action)) {
            handleView(req, resp);
        } else {
            handleList(req, resp);
        }
    }

    private void handleView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            req.setAttribute("vacancy", vacancy);
            req.getRequestDispatcher("/vacancy-details.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Vacancy ID");
        }
    }

    private void handleList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String titleFilter = req.getParameter("title");
        String descriptionFilter = req.getParameter("description");
        String pageParam = req.getParameter("page");

        int page = 1;
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException ignored) {
                page = 1;
            }
        }

        int offset = (page - 1) * PAGE_SIZE;
        List<Vacancy> vacancies = vacancyDAO.findWithFilters(titleFilter, descriptionFilter, PAGE_SIZE, offset);
        int totalVacancies = vacancyDAO.countWithFilters(titleFilter, descriptionFilter);

        req.setAttribute("vacancies", vacancies);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", (int) Math.ceil((double) totalVacancies / PAGE_SIZE));
        req.setAttribute("titleFilter", titleFilter);
        req.setAttribute("descriptionFilter", descriptionFilter);

        req.getRequestDispatcher("/vacancies.jsp").forward(req, resp);
    }
}
