package carsharing.dao;

import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;

import java.util.List;

public interface CustomerDao {
    public List<Customer> getAllCustomers();
    public Customer getCustomer(int id);
    public void updateCustomer();
    public void deleteCustomer();
    public void addCustomer(String customerName);

    public int getRentedCarId(int customerId);

    public void returnCar(int customerId);

    public void rentCar(int customerId, int id);
}
