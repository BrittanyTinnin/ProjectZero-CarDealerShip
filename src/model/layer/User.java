package model.layer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class User<T> implements Serializable{

    private static final long serialVersionUID = 5169835193377790295L;
    private String firstName;
    private String lastName;
    private List<Car> lot = new ArrayList<>();


    public User(){}

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLot(List<Car> lot){
        this.lot = lot;
    }

    public List<Car> getLot(){
        return lot;
    }





    public abstract boolean register(String firstName, String lastName);

    public abstract T login(String firstName, String lastName);


}
