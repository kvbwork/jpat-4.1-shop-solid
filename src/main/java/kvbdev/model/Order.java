package kvbdev.model;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Optional;

@Getter
@Setter
public class Order {
    private Long id;
    private ZonedDateTime dateTime;
    private final Basket basket;
    private Optional<Delivery> delivery;
    private OrderStatus orderStatus;

    public Order(Long id, ZonedDateTime dateTime, Basket basket, Optional<Delivery> delivery, OrderStatus orderStatus) {
        this.id = id;
        this.dateTime = dateTime;
        this.basket = basket;
        this.delivery = delivery;
        this.orderStatus = orderStatus;
    }

    public Order(Basket basket, Optional<Delivery> delivery){
        this(null, ZonedDateTime.now(), basket, delivery, OrderStatus.NEW);
    }

    public long getTotal() {
        long result = basket.getTotal();
        if (delivery.isPresent()) {
            result += delivery.get().getCost();
        }
        return result;
    }
}
