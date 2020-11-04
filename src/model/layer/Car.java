package model.layer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Car {

    private int vin;
    private String make;
    private String model;
    private String color;
    private HashMap<User, Double> offers = new HashMap<>();
    private List<Payment> payments = new ArrayList<Payment>();

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


    public boolean addOffer(int amount){
        return false;
    }




    public void allPayments(){

    }




    public int getVin() {
        return vin;
    }





    @Override
    public String toString() {
        return "model.layer.Car{" +
                "vin=" + vin +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", offers=" + offers +
                ", payments=" + payments +
                '}';
    }
}
