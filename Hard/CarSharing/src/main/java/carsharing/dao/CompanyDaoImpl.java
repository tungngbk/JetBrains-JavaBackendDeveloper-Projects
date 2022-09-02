package carsharing.dao;

import carsharing.CarSharingSystem;
import carsharing.database.H2Database;
import carsharing.entity.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao{
    Connection connection = H2Database.createConnection(CarSharingSystem.getDatabaseName());

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companyList = new ArrayList<>();
        String sql = "SELECT * FROM COMPANY ORDER BY ID ASC";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Company company = new Company(resultSet.getString(2), resultSet.getInt(1));
                companyList.add(company);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(companyList);
        return companyList;
    }

    @Override
    public Company getCompany(int id) {
        String sql = "SELECT * FROM COMPANY WHERE ID = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Company company = new Company(resultSet.getString(2), resultSet.getInt(1));
                return company;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateCompany() {

    }

    @Override
    public void deleteCompany() {

    }

    @Override
    public void addCompany(String companyName) {
        String sql =  "INSERT INTO COMPANY(ID,NAME) VALUES(?,?);";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setNull(1, Types.NULL);
            preparedStatement.setString(2, companyName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
