package model.layer;

import dao.layer.Dao;

import java.io.*;
import java.util.*;

public class Employee extends User<Employee> implements Dao<Employee> {
    private static final long serialVersionUID = 6180622194582426412L;
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
            System.out.println("User does not exist.");
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
                System.out.println("\nChoose from your menu:\n");
                System.out.println(
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
                        System.out.println("Invalid entry, retry.");
                        break;

                }
                storeCarData();
            } catch (InputMismatchException e) {
                System.out.println("Invalid entry, try again.\n");
                scanner.nextLine();
            }
        }
    }

    public void addCar() {
        System.out.println("Car Make:");
        String make = scanner.next();
        System.out.println("Car Model:");
        String model = scanner.next();
        System.out.println("Car Color:");
        String color = scanner.next();

        Random rand = new Random();
        int vin = rand.nextInt(1500 + 1);

        double price = (rand.nextDouble() * 1000) + 1000;


        Car car = new Car(vin, make, model, color);
        getLot().add(car);
        System.out.println(getLot());


    }

    private void viewLot(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("carlot.txt"));
            setLot((List<Car>)ois.readObject());
            ois.close();
            for(Car car : getLot()){
                System.out.println(car);
            }
        } catch(IOException | ClassNotFoundException e){
            System.out.println("Customers file empty.\n");
        }
    }







    public boolean acceptOffer() {
        return false;
    }

    public boolean rejectOffer() {
        return false;
    }


    public boolean removeCarByVin() {

        System.out.println("What is the car vin number:");
        int vin = scanner.nextInt();

        for (int i = 0; i < getLot().size(); i++) {

            Car car = getLot().get(i);

            if (car.getVin() > 0) {
                getLot().remove(car);
                System.out.println("Car with vin " + car.getVin() + " was removed.");
                return true;
            }

        }

        System.out.println("model.layer.Car does not exist.");
        return false;
    }


    public void viewPayments() {

    }



    public void retrieveCarData(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("carlot.txt"));
            setLot((List<Car>)ois.readObject());
            ois.close();
        } catch(IOException | ClassNotFoundException e){
            System.out.println("Car lot file empty.\n");
        }
    }


    public void storeCarData(){
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
            System.out.println("Employee file read successfully.\n");
            for(Employee e:employees){
                System.out.println(e);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Employees file empty.\n");
        }
    }






    @Override
    public boolean storeUserData(List<Employee> users) {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("employees.txt"));
            oos.writeObject(users);
            oos.close();
            System.out.println("Employee file updated.\n");
            return true;
        }catch (IOException e){
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
    public String toString(){
        return "Employee{" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                '}';
    }
}
