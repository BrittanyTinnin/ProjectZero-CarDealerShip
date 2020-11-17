package model.layer;

import dao.layer.Dao;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class Customer extends User<Customer> implements Dao<Customer> {

    private static final long serialVersionUID = 157820544088137620L;
    public static Scanner scanner = new Scanner(System.in);
    static List<Customer> customers = new LinkedList<>();
    private List<Car> garage = new ArrayList<>();

    public Customer() {
    }

    public Customer(String firstName, String lastName) {
        super(firstName, lastName);
    }


    @Override
    public boolean register(String firstName, String lastName) {

        if (findByName(firstName, lastName) < 0) {
            Customer customer = new Customer(firstName, lastName);
            customers.add(customer);
            storeUserData(customers);
            return true;
        }
        return false;
    }


    @Override
    public Customer login(String firstName, String lastName) {

        if (customers.isEmpty() || findByName(firstName, lastName) < 0) {
            System.out.println("model.layer.User does not exist.");
            return null;
        }

        return customers.get(findByName(firstName, lastName));
    }

    /*
     * As a customer, I can view the cars on the lot.
     * As a customer, I can make an offer for a car.
     * As a customer, I can view the cars that I own.
     * As a customer, I can view my remaining payments for a car.
     */


    public int checkIfCustomerExists(Customer customer) {
        return customers.indexOf(customer);
    }

    private int findByName(String firstName, String lastName) {
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            if ((customer.getFirstName().equals(firstName)) && (customer.getLastName().equals(lastName))) {
                return i;
            }
        }
        return -1;
    }

    public Customer findCustomer(String firstName, String lastName) throws Exception {

        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            if ((customer.getFirstName().equals(firstName)) && (customer.getLastName().equals(lastName))) {
                return customer;
            }
        }

        throw new IllegalArgumentException("Customer does not exist.");
    }


    public void customerMenu(Customer cust) {
        boolean quit = false;

        while (!quit) {
            retrieveCarData();
            try {


                System.out.println("\nChoose from your menu:\n");
                System.out.println(
                        "1) View my cars\n" +
                                "2) Cars on the lot\n" +
                                "3) Make an offer\n" +
                                "4) View car payments\n" +
                                "5) Quit Menu"
                );
                int choice = scanner.nextInt();

                switch (choice) {

                    case 1:
                        //view my cars
                        garage(cust.getFirstName(), cust.getLastName());
                        break;
                    case 2:
                        //view cars on lot
                        viewLot();
                        break;
                    case 3:
                        //make an offer
                        if (makeOffer(cust)) {
                            System.out.println("Offer added.");
                        } else {
                            System.out.println("Offer not added.");
                        }
                        break;
                    case 4:
                        //view my remaining payments
//                        carPayments();
                        break;
                    case 5:
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid entry, retry.");
                        break;

                }
                storeCarData();
            } catch (InputMismatchException e) {
                System.out.println("Invalid entry, try again.");
                scanner.nextLine();
            }
        }

    }


    private void viewLot() {
        for (Car car : getLot()) {
            System.out.println(car);
        }
    }


    public void garage(String firstName, String lastName) {
        Customer customer = customers.get(findByName(firstName, lastName));


        for (Car car : garage) {

            if (car.getOwner().equals(customer)) {
                System.out.println(car);
            }

        }
    }


    private boolean makeOffer(Customer customer) {


        System.out.println("Vin # of car to make offer:");
        int vin = scanner.nextInt();
        System.out.println("Offer Amount:");
        BigDecimal amount = scanner.nextBigDecimal();

        Offer offer = new Offer(amount, customer);


        for (int i = 0; i < getLot().size(); i++) {
            if (getLot().get(i).getVin() == vin) {
                getLot().get(i).addOffer(amount, customer, getLot().get(i));
                storeCarData();
                return true;
            }

        }

        return false;
    }


//    public boolean addOffer(double amount, int carId, Customer customer){
//        System.out.println("amount: " + amount);
//        System.out.println("carId: " + carId);
//        System.out.println("customer: " + customer);
//
//        List<Car> carlist = customer.getLot();
//
//        System.out.println("outside for loop");
//        for(int i=0; i<carlist.size(); i++) {
//            System.out.println("inside for loop");
//            System.out.println("iteration vin: " + carlist.get(i).getVin());
//            if(carlist.get(i).getVin() == carId){
//                //System.out.println("iteration vin: " + carlist.get(i).getVin());
//                carlist.get(i).offers.put(customer, amount);
//                return true;
//            }
//        }
//
//
//        return false;
//    }


    public void retrieveCarData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("carlot.txt"));
            setLot((List<Car>) ois.readObject());
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Car lot file empty.\n");
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
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("customers.txt"));
            customers = (List<Customer>) ois.readObject();
            ois.close();
            System.out.println("Customer file read successfully.\n");
            for (Customer c : customers) {
                System.out.println(c);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Customers file empty.\n");
        }

    }

    @Override
    public boolean storeUserData(List<Customer> users) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("customers.txt"));
            oos.writeObject(users);
            oos.close();
            System.out.println("Customer file updated.\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return false;

    }

    @Override
    public Customer getUser(int userId) {
        return null;
    }

    @Override
    public void updateUser(Customer user) {

    }

    @Override
    public void deleteUser(Customer user) {


    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                '}';
    }
}
