package dataaccess.interfaces;

import dataaccess.entity.Company;

import java.sql.SQLException;
import java.util.List;

public interface CompanyDAO {
    void create(Company company);
    Company findById(Long id);
    List<Company> findAll();
    void update(Company company);
    void deleteById(Long id);

    void deleteCompanyWithVacancies(long companyId) throws SQLException;

    List<Company> findAllWithVacancyCount();
}