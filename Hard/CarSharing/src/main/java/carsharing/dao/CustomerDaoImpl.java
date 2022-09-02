package carsharing.dao;

import carsharing.CarSharingSystem;
import carsharing.database.H2Database;
import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao{
    Connection connection = H2Database.createConnection(CarSharingSystem.getDatabaseName());
    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMER ORDER BY ID ASC";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Customer customer = new Customer(resultSet.getInt(1), resultSet.getString(2));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(customerList);
        return customerList;
    }

    @Override
    public Customer getCustomer(int id) {
        String sql = "SELECT * FROM CUSTOMER WHERE ID = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Customer customer = new Customer(resultSet.getInt(1), resultSet.getString(2));
                return customer;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateCustomer() {

    }

    @Override
    public void deleteCustomer() {

    }

    @Override
    public void addCustomer(String customerName) {
        String sql =  "INSERT INTO CUSTOMER(ID,NAME,RENTED_CAR_ID) VALUES(?,?,?);";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setNull(1, Types.NULL);
            preparedStatement.setString(2, customerName);
            preparedStatement.setNull(3, Types.NULL);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getRentedCarId(int customerId) {
        String sql = "SELECT * FROM CUSTOMER WHERE ID = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return resultSet.getInt(3);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    @Override
    public void returnCar(int customerId) {
        String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setNull(1, Types.NULL);
            preparedStatement.setInt(2, customerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void rentCar(int customerId, int id) {
        String sql = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE ID = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, customerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
