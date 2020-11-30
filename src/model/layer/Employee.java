package model.layer;


import dao.layer.Dao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class Employee extends User<Employee> implements Dao<Employee> {
    private static final long serialVersionUID = 6180622194582426412L;
    private static Logger logger = LogManager.getLogger();
    public static Scanner scanner = new Scanner(System.in);
    public List<Employee> employees = new LinkedList<>();

    public Employee() {
    }


    public Employee(String firstName, String lastName) {
        super(firstName, lastName);
    }


    @Override
    public boolean register(String firstName, String lastName) {

        if (findByName(firstName, lastName) < 0) {
            Employee emp = new Employee(firstName, lastName);
            employees.add(emp);
            storeUserData(employees);
            return true;
        }
        return false;
    }


    @Override
    public Employee login(String firstName, String lastName) {
        if (employees.isEmpty() || findByName(firstName, lastName) < 0) {
//            System.out.println("User does not exist.");
            logger.error("User does not exist.");
            return null;
        }


        return employees.get(findByName(firstName, lastName));
    }

    /*
     * As an employee, I can add a car to the lot.
     * As an employee, I can accept or reject an offer for a car.
     * As an employee, I can remove a car from the lot.
     * As an employee, I can view all payments.
     */


    public int checkIfEmployeeExists(Employee emp) {
        return employees.indexOf(emp);
    }


    //findByName
    private int findByName(String firstName, String lastName) {

        for (int i = 0; i < employees.size(); i++) {
            Employee emp = employees.get(i);

            if (emp.getFirstName().equals(firstName) && emp.getLastName().equals(lastName)) {
                return i;
            }
        }

        return -1;
    }


    public void employeeMenu() {
        boolean quit = false;

        while (!quit) {
            retrieveCarData();
            try {
                System.out.println("\nChoose from your menu:\n" +
                        "1) Add a car\n" +
                                "2) Accept car offer\n" +
                                "3) Reject car offer\n" +
                                "4) Remove car\n" +
                                "5) View car payments\n" +
                                "6) View Lot\n" +
                                "7) Quit"
                );

                int choice = scanner.nextInt();

                switch (choice) {

                    case 1:
                        addCar();
                        break;
                    case 2:
                        acceptOffer();
                        break;
                    case 3:
                        rejectOffer();
                        break;
                    case 4:
                        removeCarByVin();
                        break;
                    case 5:
                        viewPayments();
                        break;
                    case 6:
                        viewLot();
                        break;
                    case 7:
                        quit = true;
                        break;
                    default:
//                        System.out.println("Invalid entry, retry.");
                        logger.error("Invalid entry, retry.");
                        break;

                }
                storeCarData();
            } catch (InputMismatchException e) {
//                System.out.println("Invalid entry, try again.\n");
                logger.error("Invalid entry, retry.");
                scanner.nextLine();
            }
        }
    }

    public void addCar() {
//        System.out.println("Car Make:");
        logger.info("Car Make:");
        String make = scanner.next();
//        System.out.println("Car Model:");
        logger.info("Car Model:");
        String model = scanner.next();
//        System.out.println("Car Color:");
        logger.info("Car Color:");
        String color = scanner.next();

        Random rand = new Random();
        int vin = rand.nextInt(1500 + 1);

        double price = (rand.nextDouble() * 1000) + 1000;


        Car car = new Car(vin, make, model, color);
        getLot().add(car);
        System.out.println(getLot());


    }

    private void viewLot() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("carlot.txt"));
            setLot((List<Car>) ois.readObject());
            ois.close();
            for (Car car : getLot()) {
                System.out.println(car);
            }
        } catch (IOException | ClassNotFoundException e) {
//            System.out.println("Car lot file empty.\n");
            logger.error("Car lot file empty.");
        }
    }


    public boolean acceptOffer() {

//        System.out.println("What is the VIN of car, to accept offer?");
        logger.info("What is the VIN of car, to accept offer?");
        int vin = scanner.nextInt();
        for (int i = 0; i < getLot().size(); i++) {
            Car car = getLot().get(i);

            if (car.getVin() == vin) {
                pickAcceptedOffer(car);
                return true;

            }

        }


        return false;
    }


    private void pickAcceptedOffer(Car car) {
//        System.out.println("Which offer would you like to choose:");
        logger.info("Which offer would you like to choose:");
        car.getOffers().forEach((k, v) -> System.out.println(k + ": " + v));
        BigDecimal offerAmt = scanner.nextBigDecimal();
        BigDecimal months = new BigDecimal(12);

        BigDecimal monthlyPayment = offerAmt.divide(months, BigDecimal.ROUND_CEILING);
        for(Map.Entry<Customer, BigDecimal> entry: car.getOffers().entrySet()){
            if(entry.getValue().equals(offerAmt)){
                car.setOwner(entry.getKey());
            }
        }
        car.addToPayments(car, monthlyPayment);
        car.voidOffers(car);

//        System.out.println("Payments added to car");
        logger.trace("Payments added to car");
    }


    public boolean rejectOffer() {
//        System.out.println("What is the VIN of car, to reject offer?");
        logger.info("What is the VIN of car, to reject offer?");
        int vin = scanner.nextInt();
        for (int i = 0; i < getLot().size(); i++) {
            Car car = getLot().get(i);

            if (car.getVin() == vin) {
                pickRejectedOffer(car);
                return true;

            }

        }

//        System.out.println("Unable to reject offer.");
        logger.error("Unable to reject offer.");
        return false;
    }


    private void pickRejectedOffer(Car car) {
//        System.out.println("Choose offer you would like to reject:");
        logger.info("Choose offer you would like to reject:");
        car.getOffers().forEach((k, v) -> System.out.println(k + ": " + v));

        BigDecimal offerAmt = scanner.nextBigDecimal();
        car.getOffers().entrySet().removeIf(entry -> offerAmt.equals(entry.getValue()));
//        System.out.println("Offer rejected successfully.");
        logger.trace("Offer rejected successfully.");
    }


    public boolean removeCarByVin() {
        if(getLot().isEmpty()){
//            System.out.println("No cars on the lot.");
            logger.info("No cars on the lot.");
            return false;
        }

        for(Car car:getLot()){
            System.out.println(car);
        }

//        System.out.println("What is the car vin number:");
        logger.info("What is the car vin number:");
        int vin = scanner.nextInt();

        for (int i = 0; i < getLot().size(); i++) {

            Car car = getLot().get(i);

            if (car.getVin() > 0) {
                getLot().remove(car);
//                System.out.println("Car with vin " + car.getVin() + " was removed.");
                logger.trace("Car with vin " + car.getVin() + " was removed.");
                return true;
            }

        }

//        System.out.println("Car does not exist.");
        logger.error("Car does not exist.");
        return false;
    }


    public void viewPayments() {

    }


    public void retrieveCarData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("carlot.txt"));
            setLot((List<Car>) ois.readObject());
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
//            System.out.println("Car lot file empty.\n");
            logger.error("Car lot file empty.");
        }
    }


    public void storeCarData() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("carlot.txt"));
            oos.writeObject(getLot());
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getUserData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("employees.txt"));
            employees = (List<Employee>) ois.readObject();
            ois.close();
            System.out.println("\nEmployee file read successfully.");
            logger.trace("Employee file read successfully.");
            for (Employee e : employees) {
                System.out.println(e);
            }

        } catch (IOException | ClassNotFoundException e) {
//            System.out.println("Employees file empty.\n");
            logger.error("Employees file empty.");
        }
    }


    @Override
    public boolean storeUserData(List<Employee> users) {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("employees.txt"));
            oos.writeObject(users);
            oos.close();
//            System.out.println("Employee file updated.\n");
            logger.trace("Employee file updated.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public Employee getUser(int userId) {
        return null;
    }


    @Override
    public void updateUser(Employee user) {

    }


    @Override
    public void deleteUser(Employee user) {

    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                '}';
    }
}
