package model.layer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Car implements Serializable {

    private static final long serialVersionUID = 3991040284210077501L;
    private final int vin;
    private final String make;
    private final String model;
    private final String color;
    private Customer owner;
    private HashMap<Customer, BigDecimal> offers = new HashMap<>();
    private List<Payment> payments = new ArrayList<>();

    public Car(int vin, String make, String model, String color){
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.color=color;
    }

    /*
    car can be added to the lot
    car can have offers
    car has payments
     */







    public void allPayments(){

    }




    public int getVin() {
        return vin;
    }



    public User getOwner(){
        return owner;
    }


    public HashMap<Customer, BigDecimal> getOffers() {
        return offers;
    }

    public void addOffer(BigDecimal amount, Customer customer, Car car){
        System.out.println(offers);
        for(Customer c: offers.keySet()){
            if(c.getLastName().equals(customer.getLastName())){
                System.out.println("customer making replacement offer: " + c);
                offers.replace(c, amount);
                System.out.println("Offer has been replaced.");
                return;
            }
        }

        car.offers.put(customer, amount);

    }

    @Override
    public String toString() {
        return "Car{" +
                "vin=" + vin +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", offers=" + offers +
                ", payments=" + payments +
                '}';
    }
}
