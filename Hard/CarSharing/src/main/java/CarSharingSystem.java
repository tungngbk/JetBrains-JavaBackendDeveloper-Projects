import carsharing.dao.*;
import carsharing.database.H2Database;
import carsharing.entity.Car;
import carsharing.entity.Company;
import carsharing.entity.Customer;

import java.util.List;
import java.util.Scanner;

public class CarSharingSystem {
    private final Scanner scanner;
    private H2Database h2Database;
    private static String databaseName;
    private boolean dbCreated;
    private boolean exitSystem;
    private CompanyDao companyDao;
    private CarDao carDao;
    private CustomerDao customerDao;

    public CarSharingSystem(String dbName){
        this.scanner = new Scanner(System.in);
        this.h2Database = new H2Database();
        this.databaseName = dbName;
        this.companyDao = new CompanyDaoImpl();
        this.carDao = new CarDaoImpl();
        this.customerDao = new CustomerDaoImpl();
        this.dbCreated = false;
        this.exitSystem = false;
    }

    public void run(){
        if(!dbCreated){
            h2Database.createDatabase(databaseName);
            dbCreated = true;
        }
        System.out.println("1. Log in as a manager");
        System.out.println("2. Log in as a customer");
        System.out.println("3. Create a customer");
        System.out.println("0. Exit");
        int choice = scanner.nextInt();
        switch (choice){
            case 1:
                managerLogIn();
                break;
            case 2:
                customerLogIn();
                break;
            case 3:
                createCustomer();
                break;
            case 0:
                exitSystem = true;
                break;
        }
    }

    private void createCustomer() {
        System.out.println("Enter the customer name:");
        Scanner scanner1 = new Scanner(System.in);
        String customerName = scanner1.nextLine();
        customerDao.addCustomer(customerName);
        System.out.println("The customer was added!");
    }

    private void customerLogIn() {
        listCustomer();
    }

    private void listCustomer() {
        List<Customer> customerList = customerDao.getAllCustomers();
        if(customerList.size() == 0){
            System.out.println("The customer list is empty!");
            return;
        }
        System.out.println("Customer list:");
        for(int i = 0; i < customerList.size(); i++){
            System.out.printf("%d. %s\n", i+1, customerList.get(i).getName());
        }
        System.out.println("0. Back");
        Scanner scanner1 = new Scanner(System.in);
        int choice = scanner1.nextInt();
        if(choice == 0) return;
        Customer customer = customerDao.getCustomer(customerList.get(choice-1).getId());
//        if(customer != null){
//            System.out.printf("'%s' company:\n", customer.getName());
//        }
        customerOption(customer);
    }

    private void customerOption(Customer customer) {
        int customerId = customer.getId();
        boolean back = false;
        while (!back){
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");
            Scanner scanner1 = new Scanner(System.in);
            int choice = scanner1.nextInt();
            switch (choice){
                case 1:
                    rentCar(customerId);
                    break;
                case 2:
                    returnCar(customerId);
                    break;
                case 3:
                    rentedCar(customerId);
                    break;
                case 0:
                    back = true;
                    break;
            }
        }
    }

    private void rentCar(int customerId) {
        int carId = customerDao.getRentedCarId(customerId);
        if(carId != 0){
            System.out.println("You've already rented a car!");
            return;
        }
        List<Company> companyList = companyDao.getAllCompanies();
        if(companyList.size() == 0){
            System.out.println("The company list is empty!");
            return;
        }
        System.out.println("Choose the company:");
        for(int i = 0; i < companyList.size(); i++){
            System.out.printf("%d. %s\n", i+1, companyList.get(i).getName());
        }
        System.out.println("0. Back");
        Scanner scanner1 = new Scanner(System.in);
        int choice = scanner1.nextInt();
        if(choice == 0) return;
        Company company = companyDao.getCompany(companyList.get(choice-1).getId());
//        if(company != null){
//            System.out.printf("'%s' company:\n", company.getName());
//        }

        List<Car> carList = carDao.getAllCarsById(company.getId());
        if(carList.size() == 0){
            System.out.printf("No available cars in the '%s' company\n", company.getName());
            return;
        }
        System.out.println("Choose a car:");
        // List non-rented car
        List<Car> nonRentedCarList = carDao.getAllNonRentedCars(company.getId());
        for(int i = 0; i < nonRentedCarList.size(); i++){
            System.out.printf("%d. %s\n", i+1, nonRentedCarList.get(i).getName());
        }
        System.out.println("0. Back");
        Scanner scanner2 = new Scanner(System.in);
        int choice1 = scanner2.nextInt();
        if(choice1 == 0) return;
        Car car = carDao.getCar(carList.get(choice-1).getId());
        customerDao.rentCar(customerId, car.getId());
        System.out.printf("You rented '%s'\n", car.getName());
    }

    private void returnCar(int customerId) {
        int carId = customerDao.getRentedCarId(customerId);
        if(carId == 0){
            System.out.println("You didn't rent a car!");
            return;
        }
        customerDao.returnCar(customerId);
        System.out.println("You've returned a rented car!");
    }

    private void rentedCar(int customerId) {
        int carId = customerDao.getRentedCarId(customerId);
        if(carId == 0){
            System.out.println("You didn't rent a car!");
            return;
        }
        Car car = carDao.getCar(carId);
        System.out.println("Your rented car:");
        System.out.println(car.getName());
        System.out.println("Company:");
        Company company = companyDao.getCompany(car.getCompanyId());
        System.out.println(company.getName());
    }

    private void managerLogIn() {
        boolean back = false;
        while (!back){
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    listCompany();
                    break;
                case 2:
                    createCompany();
                    break;
                case 0:
                    back = true;
                    break;
            }
        }

    }

    private void createCompany() {
        System.out.println("Enter the company name:");
        Scanner scanner1 = new Scanner(System.in);
        String companyName = scanner1.nextLine();
        companyDao.addCompany(companyName);
        System.out.println("The company was created!");
    }

    private void listCompany() {
        List<Company> companyList = companyDao.getAllCompanies();
        if(companyList.size() == 0){
            System.out.println("The company list is empty!");
            return;
        }
        System.out.println("Choose the company:");
        for(int i = 0; i < companyList.size(); i++){
            System.out.printf("%d. %s\n", i+1, companyList.get(i).getName());
        }
        System.out.println("0. Back");
        Scanner scanner1 = new Scanner(System.in);
        int choice = scanner1.nextInt();
        if(choice == 0) return;
        Company company = companyDao.getCompany(companyList.get(choice-1).getId());
        if(company != null){
            System.out.printf("'%s' company:\n", company.getName());
        }
        carOption(company);
    }

    private void carOption(Company company) {
        boolean back = false;
        while (!back){
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            Scanner scanner1 = new Scanner(System.in);
            int choice = scanner1.nextInt();
            switch (choice){
                case 1:
                    listCar(company.getId());
                    break;
                case 2:
                    createCar(company.getId());
                    break;
                case 0:
                    back = true;
                    break;
            }
        }

    }

    private void listCar(int companyId) {
        List<Car> carList = carDao.getAllCarsById(companyId);
        if(carList.size() == 0){
            System.out.println("The car list is empty!");
            return;
        }
        System.out.println("Car list:");
        for(int i = 0; i < carList.size(); i++){
            System.out.printf("%d. %s\n", i+1, carList.get(i).getName());
        }
    }

    private void createCar(int companyId) {
        System.out.println("Enter the car name:");
        Scanner scanner1 = new Scanner(System.in);
        String carName = scanner1.nextLine();
        carDao.addCar(carName, companyId);
        System.out.println("The car was added!");
    }

    public static String getDatabaseName() {
        return databaseName;
    }

    public boolean isExitSystem() {
        return exitSystem;
    }
}
