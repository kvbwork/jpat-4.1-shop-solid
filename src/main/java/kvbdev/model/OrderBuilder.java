package kvbdev.model;

import java.util.Optional;

public class OrderBuilder {
    private Customer customer;
    private Basket basket;
    private Optional<Delivery> delivery = Optional.empty();
    private OrderStatus orderStatus = OrderStatus.NEW;

    public OrderBuilder customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public OrderBuilder basket(Basket basket) {
        this.basket = basket;
        return this;
    }

    public OrderBuilder delivery(Delivery delivery) {
        this.delivery = Optional.of(delivery);
        return this;
    }

    public Order build(){
        return new Order(null, customer, basket, delivery, orderStatus);
    }

}
