package kvbdev.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Order {
    private Long id;
    private final Customer customer;
    private final Basket basket;
    private Optional<Delivery> delivery;
    private OrderStatus orderStatus;

    public Order(Long id, Customer customer, Basket basket, Optional<Delivery> delivery, OrderStatus orderStatus) {
        this.id = id;
        this.customer = customer;
        this.basket = basket;
        this.delivery = delivery;
        this.orderStatus = orderStatus;
    }

    public long getSummary() {
        long result = basket.getTotal();
        if (delivery.isPresent()) {
            result += delivery.get().getCost();
        }
        return result;
    }
}
