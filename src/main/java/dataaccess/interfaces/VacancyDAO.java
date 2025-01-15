package dataaccess.interfaces;

import dataaccess.entity.Vacancy;

import java.sql.SQLException;
import java.util.List;

public interface VacancyDAO {
    void create(Vacancy vacancy);
    Vacancy findById(long id);
    List<Vacancy> findAll();
    void update(Vacancy vacancy);
    void delete(long id);

    List<Vacancy> findByCompanyId(long companyId);

    List<Vacancy> findWithFilters(String title, String description, int limit, int offset);

    int countWithFilters(String title, String description);

    List<Vacancy> getTopVacanciesByApplications(int limit) throws SQLException;
}