package dataaccess.repository;

import config.ConnectionManager;
import dataaccess.entity.Company;
import dataaccess.entity.Vacancy;
import dataaccess.interfaces.VacancyDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VacancyRepository implements VacancyDAO {
    private static Connection connection;
    private static VacancyRepository instanceVacancy;

    public static VacancyRepository getInstance() throws SQLException {
        connection = ConnectionManager.getConnection();
        if (instanceVacancy == null) {
            instanceVacancy = new VacancyRepository();
        }
        else {
            return instanceVacancy;
        }

        throw new SQLException("");
    }

    @Override
    public void create(Vacancy vacancy) {
        String sql = "INSERT INTO exchange.vacancies (title, company_id, description, required_skills, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vacancy.getTitle());
            stmt.setLong(2, vacancy.getCompanyId());
            stmt.setString(3, vacancy.getDescription());
            stmt.setString(4, vacancy.getRequiredSkills());
            stmt.setString(5, vacancy.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating vacancy", e);
        }
    }


    @Override
    public Vacancy findById(long id) {
        String sql = "SELECT * FROM exchange.vacancies WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Company company = CompanyRepository.getInstance().findById(rs.getLong("company_id"));
                return new Vacancy(
                        rs.getLong("id"),
                        rs.getString("title"),
                        company,
                        rs.getString("description"),
                        rs.getString("required_skills"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Vacancy> findAll() {
        List<Vacancy> vacancies = new ArrayList<>();
        String sql = "SELECT * FROM exchange.vacancies";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Company company = CompanyRepository.getInstance().findById(rs.getLong("company_id"));
                vacancies.add(new Vacancy(
                        rs.getLong("id"),
                        rs.getString("title"),
                        company,
                        rs.getString("description"),
                        rs.getString("required_skills"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vacancies;
    }

    @Override
    public void update(Vacancy vacancy) {
        String sql = "UPDATE exchange.vacancies SET title = ?, description = ?, required_skills = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vacancy.getTitle());
            stmt.setString(2, vacancy.getDescription());
            stmt.setString(3, vacancy.getRequiredSkills());
            stmt.setString(4, vacancy.getStatus());
            stmt.setLong(5, vacancy.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM exchange.vacancies WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Vacancy> findByCompanyId(long companyId) {
        List<Vacancy> vacancies = new ArrayList<>();
        String query = "SELECT * FROM exchange.vacancies WHERE company_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, companyId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setId(resultSet.getLong("id"));
                    vacancy.setTitle(resultSet.getString("title"));
                    vacancy.setCompanyId(resultSet.getLong("company_id"));
                    vacancy.setDescription(resultSet.getString("description"));
                    vacancy.setRequiredSkills(resultSet.getString("required_skills"));
                    vacancy.setStatus(resultSet.getString("status"));
                    vacancies.add(vacancy);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching vacancies by company ID", e);
        }

        return vacancies;
    }

    @Override
    public List<Vacancy> findWithFilters(String title, String description, int limit, int offset) {
        String sql = "SELECT * FROM exchange.vacancies WHERE " +
                "(? IS NULL OR title LIKE ?) AND " +
                "(? IS NULL OR description LIKE ?) " +
                "LIMIT ? OFFSET ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, title != null ? "%" + title + "%" : null);
            stmt.setString(3, description);
            stmt.setString(4, description != null ? "%" + description + "%" : null);
            stmt.setInt(5, limit);
            stmt.setInt(6, offset);

            ResultSet rs = stmt.executeQuery();
            List<Vacancy> vacancies = new ArrayList<>();
            while (rs.next()) {
                Vacancy vacancy = new Vacancy();
                vacancy.setId(rs.getLong("id"));
                vacancy.setTitle(rs.getString("title"));
                vacancy.setDescription(rs.getString("description"));
                vacancy.setRequiredSkills(rs.getString("required_skills"));
                vacancy.setStatus(rs.getString("status"));
                vacancies.add(vacancy);
            }
            return vacancies;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public int countWithFilters(String title, String description) {
        String sql = "SELECT COUNT(*) FROM exchange.vacancies WHERE " +
                "(? IS NULL OR title LIKE ?) AND " +
                "(? IS NULL OR description LIKE ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, title != null ? "%" + title + "%" : null);
            stmt.setString(3, description);
            stmt.setString(4, description != null ? "%" + description + "%" : null);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Vacancy> getTopVacanciesByApplications(int limit) throws SQLException {
        List<Vacancy> topVacancies = new ArrayList<>();
        String query = """
        SELECT v.id, v.title, v.description, v.required_skills, v.status, COUNT(a.id) AS applications_count
        FROM exchange.vacancies v
        LEFT JOIN exchange.applications a ON v.id = a.vacancy_id
        GROUP BY v.id, v.title, v.description, v.required_skills, v.status
        ORDER BY applications_count DESC
        LIMIT ?
    """;
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setId(rs.getLong("id"));
                    vacancy.setTitle(rs.getString("title"));
                    vacancy.setDescription(rs.getString("description"));
                    vacancy.setRequiredSkills(rs.getString("required_skills"));
                    vacancy.setStatus(rs.getString("status"));
                    vacancy.setApplicationsCount(rs.getInt("applications_count")); // Установка значения
                    topVacancies.add(vacancy);
                }
            }
        }
        return topVacancies;
    }

}
