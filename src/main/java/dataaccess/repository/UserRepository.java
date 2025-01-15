package dataaccess.repository;

import config.ConnectionManager;
import dataaccess.entity.Application;
import dataaccess.entity.User;
import dataaccess.interfaces.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements UserDAO {

    private static Connection connection;


    private static UserRepository instanceUser;
    public static UserRepository getInstance() throws SQLException {
        connection = ConnectionManager.getConnection();
        if (instanceUser == null) {
            instanceUser = new UserRepository();
        }
        else {
            return instanceUser;
        }
        throw new RuntimeException("");
    }

    @Override
    public void create(User user) {
        String sql = "INSERT INTO exchange.users (name, contact_details, skills, desired_position, employment_status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getContactDetails());
            stmt.setString(3, user.getSkills());
            stmt.setString(4, user.getDesiredPosition());
            stmt.setString(5, user.getEmploymentStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findByName(String name) {
        String query = "SELECT * FROM exchange.users WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setContactDetails(resultSet.getString("contact_details"));
                user.setSkills(resultSet.getString("skills"));
                user.setDesiredPosition(resultSet.getString("desired_position"));
                user.setEmploymentStatus(resultSet.getString("employment_status"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM exchange.users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("contact_details"),
                        rs.getString("skills"),
                        rs.getString("desired_position"),
                        rs.getString("employment_status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM exchange.users";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                users.add(new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("contact_details"),
                        rs.getString("skills"),
                        rs.getString("desired_position"),
                        rs.getString("employment_status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE exchange.users SET name = ?, contact_details = ?, skills = ?, desired_position = ?, employment_status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getContactDetails());
            stmt.setString(3, user.getSkills());
            stmt.setString(4, user.getDesiredPosition());
            stmt.setString(5, user.getEmploymentStatus());
            stmt.setLong(6, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        String deleteApplicationsSql = "DELETE FROM exchange.applications WHERE user_id = ?";
        String deleteUserSql = "DELETE FROM exchange.users WHERE id = ?";

        try (PreparedStatement deleteApplicationsStmt = connection.prepareStatement(deleteApplicationsSql);
             PreparedStatement deleteUserStmt = connection.prepareStatement(deleteUserSql)) {

            deleteApplicationsStmt.setLong(1, id);
            deleteApplicationsStmt.executeUpdate();

            deleteUserStmt.setLong(1, id);
            deleteUserStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public User findOrCreate(String name, String contactDetails) {
        User user = null;
        String findQuery = "SELECT * FROM exchange.users WHERE name = ? AND contact_details = ?";
        String createQuery = """
        INSERT INTO exchange.users (name, contact_details, employment_status) 
        VALUES (?, ?, 'Unemployed')
    """;

        try (PreparedStatement findStatement = connection.prepareStatement(findQuery)) {
            findStatement.setString(1, name);
            findStatement.setString(2, contactDetails);

            try (ResultSet resultSet = findStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setName(resultSet.getString("name"));
                    user.setContactDetails(resultSet.getString("contact_details"));
                    user.setEmploymentStatus(resultSet.getString("employment_status"));
                    return user;
                }
            }


            try (PreparedStatement createStatement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {
                createStatement.setString(1, name);
                createStatement.setString(2, contactDetails);
                createStatement.executeUpdate();

                try (ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user = new User();
                        user.setId(generatedKeys.getLong(1));
                        user.setName(name);
                        user.setContactDetails(contactDetails);
                        user.setEmploymentStatus("Unemployed");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding or creating user", e);
        }

        return user;
    }

    public List<User> findUsersByApplications(List<Application> applications) throws SQLException {
        List<User> users = new ArrayList<>();
        if (applications.isEmpty()) {
            return users;
        }

        StringBuilder sql = new StringBuilder("SELECT * FROM Users WHERE id IN (");
        for (int i = 0; i < applications.size(); i++) {
            sql.append("?");
            if (i < applications.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < applications.size(); i++) {
                stmt.setLong(i + 1, applications.get(i).getUserId());
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getLong("id"));
                    user.setName(rs.getString("name"));
                    user.setContactDetails(rs.getString("contact_details"));
                    user.setSkills(rs.getString("skills"));
                    user.setDesiredPosition(rs.getString("desired_position"));
                    user.setEmploymentStatus(rs.getString("employment_status"));
                    users.add(user);
                }
            }
        }
        return users;
    }


}
