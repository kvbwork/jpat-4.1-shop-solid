package kvbdev.model;

import java.time.ZonedDateTime;
import java.util.Optional;

public class OrderBuilder {
    private Basket basket;
    private Optional<Delivery> delivery = Optional.empty();
    private OrderStatus orderStatus = OrderStatus.NEW;

    public OrderBuilder basket(Basket basket) {
        this.basket = basket;
        return this;
    }

    public OrderBuilder delivery(Delivery delivery) {
        this.delivery = Optional.of(delivery);
        return this;
    }

    public Order build(){
        return new Order(null, ZonedDateTime.now(), basket, delivery, orderStatus);
    }

}
