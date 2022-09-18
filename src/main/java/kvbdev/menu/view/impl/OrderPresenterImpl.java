package kvbdev.menu.view.impl;

import kvbdev.menu.view.Presenter;
import kvbdev.model.Basket;
import kvbdev.model.Delivery;
import kvbdev.model.Order;

import java.util.Objects;
import java.util.Optional;

public class OrderPresenterImpl implements Presenter<Order> {
    protected final Presenter<Basket> basketPresenter;
    protected final Presenter<Optional<Delivery>> deliveryPresenter;

    public OrderPresenterImpl(Presenter<Basket> basketPresenter, Presenter<Optional<Delivery>> deliveryPresenter) {
        this.basketPresenter = basketPresenter;
        this.deliveryPresenter = deliveryPresenter;
    }

    @Override
    public String toString(Order order) {
        StringBuilder sb = new StringBuilder();

        sb.append("Заказ ").append(Objects.toString(order.getId(), "Новый")).append(" (").append(order.getOrderStatus()).append(")").append("\n")
                .append(order.getDateTime()).append("\n")
                .append("\n")
                .append(basketPresenter.toString(order.getBasket())).append("\n")
                .append("\n")
                .append(deliveryPresenter.toString(order.getDelivery())).append("\n")
                .append("\n")
                .append("Итог: ").append(order.getTotal()).append(" руб").append("\n");

        return sb.toString();
    }
}
