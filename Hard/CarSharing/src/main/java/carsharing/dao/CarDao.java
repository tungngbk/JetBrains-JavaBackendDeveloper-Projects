package carsharing.dao;

import carsharing.entity.Car;
import carsharing.entity.Company;

import java.util.List;

public interface CarDao {
    public List<Car> getAllCarsById(int id);
    public Car getCar(int id);
    public void updateCar();
    public void deleteCar();
    public void addCar(String carName, int companyId);

    public List<Car> getAllNonRentedCars(int id);
}
