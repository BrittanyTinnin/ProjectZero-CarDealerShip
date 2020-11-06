package model.layer;

import java.math.BigDecimal;

public class Offer {

    private BigDecimal amount;
    private Customer owner;

    public Offer(BigDecimal amount, Customer owner) {
        this.amount = amount;
        this.owner = owner;
    }


}
