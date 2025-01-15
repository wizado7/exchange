package dataaccess.interfaces;

import dataaccess.entity.Application;

import java.sql.SQLException;
import java.util.List;

public interface ApplicationDAO {
    void create(Application application);

    void createApplication(long userId, long vacancyId);

    Application findById(Long id);
    List<Application> findAll();
    void update(Application application);
    void deleteById(Long id);

    Application findByUserAndVacancy(long userId, long vacancyId);

    boolean applyForVacancy(long userId, long vacancyId);

    List<Application> findByVacancyId(long vacancyId) throws SQLException;
}