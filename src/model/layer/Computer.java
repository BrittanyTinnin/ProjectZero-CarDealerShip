package model.layer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Computer {
    public static Scanner scanner = new Scanner(System.in);
    private static Logger logger = LogManager.getLogger();
    private static Customer customer = new Customer();
    private static Employee employee = new Employee();


    /***********************************
     ***********DECORATION**************
     ***********************************/


    private static void car() {
        System.out.println();
        logger.error(
                "                              _.-=\"_-         _\n" +
                        "                         _.-=\"   _-          | ||\"\"\"\"\"\"\"---._______     __..\n" +
                        "             ___.===\"\"\"\"-.______-,,,,,,,,,,,,`-''----\" \"\"\"\"\"       \"\"\"\"\"  __'\n" +
                        "      __.--\"\"     __        ,'                   o \\           __        [__|\n" +
                        " __-\"\"=======.--\"\"  \"\"--.=================================.--\"\"  \"\"--.=======:\n" +
                        "]       [w] : /        \\ : |========================|    : /        \\ :  [w] :\n" +
                        "V___________:|          |: |========================|    :|          |:   _-\"\n" +
                        " V__________: \\        / :_|=======================/_____: \\        / :__-\"\n" +
                        " -----------'  \"-____-\"  `-------------------------------'  \"-____-\""
        );
    }


    /***********************************
     **************MENU*****************
     ***********************************/

    private static void menu() {

        employee.getUserData();
        customer.getUserData();
        customer.retrieveCarData();


        boolean quit = false;
        car();


        while (!quit) {
            employee.getUserData();
            customer.getUserData();
            customer.retrieveCarData();
            try {
//                System.out.println("\nChoose an option: ");
//                logger.info("Choose an option: ");
//                System.out.println(
//                        "Are you a customer or employee?\n" +
//                                "1) Customer\n" +
//                                "2) Employee\n" +
//                                "3) Quit"
//                );
                logger.info("Choose an option: \n" +
                        "Are you a customer or employee?\n" +
                        "1) Customer\n" +
                        "2) Employee\n" +
                        "3) Quit"
                );
                int x = scanner.nextInt();
                scanner.nextLine();
                switch (x) {
                    case 1:
                        customerAccess();
                        break;
                    case 2:
                        employeeAccess();
                        break;
                    case 3:
                        logger.trace("Exiting program now...");
                        quit = true;
                        break;
                    default:
                        logger.error("Invalid entry, try again.");
                        menu();
                        break;
                }
            } catch (InputMismatchException e) {
//                System.out.println("Invalid entry, try again.\n");
                logger.error("Invalid entry, try again.");
                scanner.nextLine();
            }
        }


    }


    /***********************************
     ***********EMPLOYEE METHODS********
     ***********************************/


    private static void employeeAccess() {

        try {
            logger.info("Are you a new employee?\n" +
                    "1) Yes\n" +
                    "2) No\n" +
                    "3) Back"
            );
            int choice = scanner.nextInt();


            switch (choice) {
                case 1:
                    employeeRegister();
                    break;
                case 2:
                    employeeLogin();
                    break;
                case 3:
                    menu();
                    break;
                default:
//                    System.out.println("Invalid entry, retry.");
                    logger.error("Invalid entry, try again.");
                    customerAccess();
                    break;
            }
        } catch (InputMismatchException e) {
//            System.out.println("Invalid entry, try again.");
            logger.error("Invalid entry, try again.");
            scanner.nextLine();
        }
    }


    public static void employeeRegister() {
//        System.out.println("What is your first name?");
        logger.info("What is your first name?");
        String firstName = scanner.next();
//        System.out.println("What is your last name?");
        logger.info("What is your last name?");
        String lastName = scanner.next();

        if (employee.register(firstName, lastName)) {
//            System.out.println("Employee successfully register");
            logger.info("Employee successfully register");
        } else {
//            System.out.println("Employee already exists.");
            logger.info("Employee already exists.");
        }
    }


    public static void employeeLogin() {
//        System.out.println("Enter first name:");
        logger.info("Enter first name:");
        String firstName = scanner.next();
//        System.out.println("Enter last name:");
        logger.info("Enter last name:");
        String lastName = scanner.next();
        if (employee.login(firstName, lastName) != null) {
//            System.out.println("Employee logged in.\n");
            logger.info("Employee logged in.");
            employee.employeeMenu();
        }
    }










    /***********************************
     **********CUSTOMER METHODS*********
     ***********************************/

    private static void customerAccess() {

        try {
            System.out.println("Are you a new customer?\n" +
                    "1) Yes\n" +
                    "2) No\n" +
                    "3) Back"
            );
            int choice = scanner.nextInt();


            switch (choice) {
                case 1:
//                    System.out.println("customer register");
                    logger.info("customer register");
                    customerRegister();
                    break;
                case 2:
//                    System.out.println("customer Login");
                    logger.info("customer login");
                    customerLogin();
                    break;
                case 3:
                    menu();
                    break;
                default:
//                    System.out.println("Invalid entry, retry.\n");
                    logger.error("Invalid entry, retry.");
                    customerAccess();
                    break;
            }

        } catch (InputMismatchException e) {
//            System.out.println("Invalid entry, retry.\n");
            logger.error("Invalid entry, retry.");
            scanner.nextLine();

        }

    }


    public static void customerRegister() {
//        System.out.println("What is your first name?");
        logger.info("What is your first name?");
        String firstName = scanner.next();
//        System.out.println("What is your last name?");
        logger.info("What is your last name?");
        String lastName = scanner.next();

        if (customer.register(firstName, lastName)) {
//            System.out.println("Customer successfully register");
            logger.trace("Customer successfully register");
        } else {
//            System.out.println("Customer already exists.");
            logger.trace("Customer already exists.");
        }
    }


    public static void customerLogin() {
//        System.out.println("Enter first name:");
        logger.info("Enter first name:");
        String firstName = scanner.next();
//        System.out.println("Enter last name:");
        logger.info("Enter last name:");
        String lastName = scanner.next();
        if (customer.login(firstName, lastName) != null) {
//            System.out.println("Customer logged in.\n");
            logger.trace("Customer logged in.");
            customer.customerMenu(customer.login(firstName, lastName));
        }

    }


    public static void listAll(){
        for(Employee emp: employee.employees){
            System.out.println(emp);
        }

        for(Customer cust: customer.customers){
            System.out.println(cust);
        }

        for(Car car: employee.getLot()){
            System.out.println(car);
        }
    }



    /***********************************
     ***********MAINMENU**************
     ***********************************/

    public static void main(String[] args) {




        menu();
        scanner.close();

    }
}
