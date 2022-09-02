package carsharing.dao;

import carsharing.CarSharingSystem;
import carsharing.database.H2Database;
import carsharing.entity.Car;
import carsharing.entity.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CarDaoImpl implements CarDao{
    Connection connection = H2Database.createConnection(CarSharingSystem.getDatabaseName());
    @Override
    public List<Car> getAllCarsById(int id) {
        List<Car> carList = new ArrayList<>();
        String sql = "SELECT * FROM CAR WHERE COMPANY_ID = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Car car = new Car(resultSet.getInt(1), resultSet.getString(3), resultSet.getInt(2));
                carList.add(car);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(carList);
        return carList;
    }
    @Override
    public Car getCar(int id) {
        String sql = "SELECT * FROM CAR WHERE ID = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Car car = new Car(resultSet.getInt(1), resultSet.getString(3), resultSet.getInt(2));
                return car;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateCar() {

    }

    @Override
    public void deleteCar() {

    }

    @Override
    public void addCar(String carName, int companyId) {
        String sql =  "INSERT INTO CAR(ID,COMPANY_ID,NAME) VALUES(?,?,?);";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setNull(1, Types.NULL);
            preparedStatement.setInt(2, companyId);
            preparedStatement.setString(3, carName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> getAllNonRentedCars(int id) {
        List<Car> carList = new ArrayList<>();
        String sql = "SELECT tb.car_id, tb.company_id, tb.name " +
                "from (select a.id as car_id, a.company_id, a.name, b.id as customer_id " +
                "from CAR as a " +
                "left join CUSTOMER as b " +
                "on a.id = b.rented_car_id) as tb " +
                "where tb.customer_id IS NULL";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setInt(1, id);
//            preparedStatement.setNull(2, Types.NULL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Car car = new Car(resultSet.getInt(1), resultSet.getString(3), resultSet.getInt(2));
                carList.add(car);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Collections.sort(carList);
        return carList;
    }
}
