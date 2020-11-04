package model.layer;

import dao.layer.Dao;
import sun.awt.image.ImageWatched;

import java.io.*;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Customer extends User implements Dao<Customer> {

    public static Scanner scanner = new Scanner(System.in);
    static List<Customer> customers = new LinkedList<Customer>();

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
            storeData(customers);
            return true;
        }
        return false;
    }


    @Override
    public User login(String firstName, String lastName) {

        if (customers.isEmpty() || findByName(firstName, lastName) < 0) {
            System.out.println("model.layer.User does not exist.");
            return null;
        }

        Customer customer = customers.get(findByName(firstName, lastName));
        return customer;
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


    public void customerMenu() {
        boolean quit = false;

        while (!quit) {

            try {


                System.out.println("Choose from your menu:\n");
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
                        garage();
                        break;
                    case 2:
                        //view cars on lot
                        viewLot();
                        break;
                    case 3:
                        //make an offer
                        makeOffer();
                        break;
                    case 4:
                        //view my remaining payments
                        carPayments();
                        break;
                    case 5:
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid entry, retry.");
                        break;

                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid entry, try again.");
                scanner.nextLine();
            }
        }

    }


    private void viewLot() {

    }

    private boolean makeOffer() {
        return false;
    }

    private void garage() {
        System.out.println("Inside customer garage");
        System.out.println(getGarage());
    }

    private void carPayments() {

    }


    @Override
    public List<Customer> getData() {

        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("customers.txt"));
            customers = (List<Customer>)ois.readObject();
            ois.close();
            System.out.println("Customer file read successfully.\n");
            for(Customer c:customers){
                System.out.println(c);
            }
        }catch(IOException | ClassNotFoundException e){
            System.out.println("Customers file empty.\n");
        }
        return null;

    }

    @Override
    public boolean storeData(List<Customer> users) {
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
    public String toString(){
        return "Customer{" +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                '}';
    }
}
