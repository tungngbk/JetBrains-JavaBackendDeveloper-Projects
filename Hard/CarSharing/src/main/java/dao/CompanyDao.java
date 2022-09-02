package dao;

import carsharing.entity.Company;

import java.util.List;

public interface CompanyDao {
    public List<Company> getAllCompanies();
    public Company getCompany(int id);
    public void updateCompany();
    public void deleteCompany();
    public void addCompany(String companyName);
}
