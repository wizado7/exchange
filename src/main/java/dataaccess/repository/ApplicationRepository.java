package dataaccess.repository;

import config.ConnectionManager;
import dataaccess.entity.Application;
import dataaccess.entity.User;
import dataaccess.entity.Vacancy;
import dataaccess.interfaces.ApplicationDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationRepository implements ApplicationDAO {
    private static Connection connection;


    private static ApplicationRepository instanceApp;
    public static ApplicationRepository getInstance() throws SQLException {
        connection = ConnectionManager.getConnection();
        if (instanceApp == null) {
            instanceApp = new ApplicationRepository();
        }
        else {
            return instanceApp;
        }
        return null;
    }

    @Override
    public void create(Application application) {
        String sql = "INSERT INTO exchange.applications (user_id, vacancy_id, status, submission_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, application.getUser().getId());
            stmt.setLong(2, application.getVacancy().getId());
            stmt.setString(3, application.getStatus());
            stmt.setTimestamp(4, Timestamp.valueOf(application.getSubmissionDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createApplication(long userId, long vacancyId) {
        String query = """
        INSERT INTO exchange.applications (user_id, vacancy_id, status, submission_date) 
        VALUES (?, ?, 'Pending', CURRENT_TIMESTAMP)
    """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setLong(2, vacancyId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating application", e);
        }
    }


    @Override
    public Application findById(Long id) {
        String sql = "SELECT * FROM exchange.applications WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = UserRepository.getInstance().findById(rs.getLong("user_id"));
                Vacancy vacancy = VacancyRepository.getInstance().findById(rs.getLong("vacancy_id"));
                return new Application(
                        rs.getLong("id"),
                        user,
                        vacancy,
                        rs.getString("status"),
                        rs.getTimestamp("submission_date").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Application> findAll() {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM exchange.applications";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User user = UserRepository.getInstance().findById(rs.getLong("user_id"));
                Vacancy vacancy = VacancyRepository.getInstance().findById(rs.getLong("vacancy_id"));
                applications.add(new Application(
                        rs.getLong("id"),
                        user,
                        vacancy,
                        rs.getString("status"),
                        rs.getTimestamp("submission_date").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }

    @Override
    public void update(Application application) {
        String sql = "UPDATE exchange.applications SET user_id = ?, vacancy_id = ?, status = ?, submission_date = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, application.getUser().getId());
            stmt.setLong(2, application.getVacancy().getId());
            stmt.setString(3, application.getStatus());
            stmt.setTimestamp(4, Timestamp.valueOf(application.getSubmissionDate()));
            stmt.setLong(5, application.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM exchange.applications WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean applyForVacancy(long userId, long vacancyId) {
        String query = "INSERT INTO exchange.applications (user_id, vacancy_id, status, submission_date) VALUES (?, ?, 'Pending', NOW())";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setLong(2, vacancyId);
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Application findByUserAndVacancy(long userId, long vacancyId) {
        String query = "SELECT * FROM exchange.applications WHERE user_id = ? AND vacancy_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setLong(2, vacancyId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Application application = new Application();
                application.setId(resultSet.getLong("id"));
                application.setUserId(resultSet.getLong("user_id"));
                application.setVacancyId(resultSet.getLong("vacancy_id"));
                application.setStatus(resultSet.getString("status"));
                application.setSubmissionDate(resultSet.getTimestamp("submission_date").toLocalDateTime());
                return application;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Application> findByVacancyId(long vacancyId) throws SQLException {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM exchange.applications WHERE vacancy_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, vacancyId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Application application = new Application();
                    application.setId(rs.getLong("id"));
                    application.setUserId(rs.getLong("user_id"));
                    application.setVacancyId(rs.getLong("vacancy_id"));
                    application.setStatus(rs.getString("status"));
                    application.setSubmissionDate(rs.getTimestamp("submission_date").toLocalDateTime());
                    applications.add(application);
                }
            }
        }
        return applications;
    }

}
