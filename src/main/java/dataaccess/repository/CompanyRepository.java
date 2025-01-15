package dataaccess.repository;

import config.ConnectionManager;
import dataaccess.entity.Company;
import dataaccess.interfaces.CompanyDAO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CompanyRepository implements CompanyDAO {
    private static Connection connection;
    private static CompanyRepository instanceCompany;

    public static CompanyRepository getInstance() throws SQLException {
        connection = ConnectionManager.getConnection();
        if (instanceCompany == null) {
            instanceCompany = new CompanyRepository();
        }
        else {
            return instanceCompany;
        }
        throw new RuntimeException("");
    }

    @Override
    public void create(Company company) {
        String sql = "INSERT INTO exchange.companies (name, contact_details, description, address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, company.getName());
            stmt.setString(2, company.getContactDetails());
            stmt.setString(3, company.getDescription());
            stmt.setString(4, company.getAddress());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    company.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Company findById(Long id) {
        String sql = "SELECT * FROM exchange.companies WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Company(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("contact_details"),
                        rs.getString("description"),
                        rs.getString("address")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Company> findAll() {
        List<Company> companies = new ArrayList<>();
        String sql = "SELECT * FROM exchange.companies";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                companies.add(new Company(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("contact_details"),
                        rs.getString("description"),
                        rs.getString("address")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public void update(Company company) {
        String sql = "UPDATE exchange.companies SET name = ?, contact_details = ?, description = ?, address = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, company.getName());
            stmt.setString(2, company.getContactDetails());
            stmt.setString(3, company.getDescription());
            stmt.setString(4, company.getAddress());
            stmt.setLong(5, company.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM exchange.companies WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCompanyWithVacancies(long companyId) throws SQLException {
        String deleteApplicationsSql = "DELETE a FROM exchange.applications a " +
                "JOIN exchange.vacancies v ON a.vacancy_id = v.id " +
                "WHERE v.company_id = ?";
        String deleteVacanciesSql = "DELETE FROM exchange.vacancies WHERE company_id = ?";
        String deleteCompanySql = "DELETE FROM exchange.companies WHERE id = ?";

        try (PreparedStatement deleteAppsStmt = connection.prepareStatement(deleteApplicationsSql);
             PreparedStatement deleteVacanciesStmt = connection.prepareStatement(deleteVacanciesSql);
             PreparedStatement deleteCompanyStmt = connection.prepareStatement(deleteCompanySql)) {

            deleteAppsStmt.setLong(1, companyId);
            deleteAppsStmt.executeUpdate();

            deleteVacanciesStmt.setLong(1, companyId);
            deleteVacanciesStmt.executeUpdate();

            deleteCompanyStmt.setLong(1, companyId);
            deleteCompanyStmt.executeUpdate();
        }
    }

    @Override
    public List<Company> findAllWithVacancyCount() {
        List<Company> companies = new ArrayList<>();
        String query = """
        SELECT c.id, c.name, c.contact_details, c.description, c.address, 
               (SELECT COUNT(*) FROM exchange.vacancies v WHERE v.company_id = c.id) AS vacancy_count
        FROM exchange.companies c
    """;

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Company company = new Company();
                company.setId(resultSet.getLong("id"));
                company.setName(resultSet.getString("name"));
                company.setContactDetails(resultSet.getString("contact_details"));
                company.setDescription(resultSet.getString("description"));
                company.setAddress(resultSet.getString("address"));
                company.setVacancyCount(resultSet.getInt("vacancy_count"));
                companies.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching companies with vacancy count", e);
        }

        return companies;
}


}
