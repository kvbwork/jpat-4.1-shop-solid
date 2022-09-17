package kvbdev.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Delivery {
    private final Address address;
    private final long cost;

    public Delivery(Address address, long cost) {
        this.address = address;
        this.cost = cost;
    }

}
