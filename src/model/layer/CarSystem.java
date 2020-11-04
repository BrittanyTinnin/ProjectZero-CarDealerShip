package model.layer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CarSystem implements Serializable {

    private static List<Car> lot = new ArrayList<Car>();

    public CarSystem() {
    }

    public List<Payment> calculateMonthlyPayments(BigDecimal carPrice){
        return null;
    }

    public boolean rejectOffers(){
        return false;
    }
}
