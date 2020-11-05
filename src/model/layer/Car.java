package model.layer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Car implements Serializable {

    private static final long serialVersionUID = 3991040284210077501L;
    private final int vin;
    private final String make;
    private final String model;
    private final String color;
    private User owner;
    private HashMap<User, Double> offers;
    private List<Payment> payments;

    public Car(int vin, String make, String model, String color){
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.color=color;
        this.offers = null;
        this.payments = null;
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


    public HashMap<User, Double> getOffers() {
        return offers;
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
