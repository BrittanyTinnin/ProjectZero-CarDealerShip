package model.layer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Car implements Serializable {

    private static Logger logger = LogManager.getLogger();
    private static final long serialVersionUID = 3991040284210077501L;
    private final int vin;
    private final String make;
    private final String model;
    private final String color;
    private Customer owner;
    private HashMap<Customer, BigDecimal> offers = new HashMap<>();
    private List<BigDecimal> payments = new ArrayList<>(12);

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


    public void voidOffers(Car car){
        car.offers = null;
    }



    public void addToPayments(Car car, BigDecimal amount){

        for(int i=0; i<12; i++){
            car.payments.add(i, amount);
        }

    }




    public int getVin() {
        return vin;
    }



    public Customer getOwner(){
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public HashMap<Customer, BigDecimal> getOffers() {
        return offers;
    }

    public void addOffer(BigDecimal amount, Customer customer, Car car){
        System.out.println(offers);
        for(Customer c: offers.keySet()){
            if(c.getLastName().equals(customer.getLastName())){
                offers.replace(c, amount);
//                System.out.println("Offer has been replaced.");
                logger.trace("Offer has been replaced.");
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
                ", owner=" + owner +
                ", offers=" + offers +
                ", payments=" + payments +
                '}';
    }
}
