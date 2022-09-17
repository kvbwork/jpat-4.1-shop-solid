package kvbdev.service;

import kvbdev.data.OrderRepository;
import kvbdev.data.ProductRepository;
import kvbdev.model.*;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CustomerShopSession {
    protected final ProductRepository productRepository;
    protected final OrderRepository orderRepository;

    protected final Customer customer;
    protected Basket basket;
    protected Optional<Delivery> delivery;

    public CustomerShopSession(Customer customer, ProductRepository productRepository, OrderRepository orderRepository) {
        this.customer = customer;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Iterable<Product> listAllProducts() {
        return productRepository.findAll();
    }

    public Iterable<Product> findProductByName(String searchString) {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .filter(product -> product.getName().contains(searchString))
                .collect(Collectors.toList());
    }

    public Customer getCustomer(){
        return customer;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setDelivery(String addressStr, long cost){
        Address address = new Address(addressStr);
        delivery = Optional.of(new Delivery(address, cost));
    }

    public Order createOrder() {
        Order order = new Order(null, customer, basket, delivery, OrderStatus.NEW);
        basket = new Basket();
        return order;
    }

    public Order saveOrder(Order order) {
        orderRepository.save(order);
        return order;
    }

    public Iterable<Order> listOrders() {
        return orderRepository.findByCustomer(customer);
    }

    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id)
                .filter(order -> Objects.equals(order.getCustomer(), customer));
    }

}
