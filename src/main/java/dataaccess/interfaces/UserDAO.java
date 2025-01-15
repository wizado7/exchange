package dataaccess.interfaces;

import dataaccess.entity.Application;
import dataaccess.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    void create(User user);

    User findByName(String name);

    User findById(Long id);
    List<User> findAll();
    void update(User user);
    void deleteById(Long id);

    User findOrCreate(String name, String contactDetails);

    List<User> findUsersByApplications(List<Application> applications) throws SQLException;


}