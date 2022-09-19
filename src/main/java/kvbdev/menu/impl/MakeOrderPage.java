package kvbdev.menu.impl;

import kvbdev.menu.AbstractPage;
import kvbdev.menu.InteractiveChannel;
import kvbdev.menu.view.Presenter;
import kvbdev.model.Delivery;
import kvbdev.model.Order;
import kvbdev.model.OrderBuilder;
import kvbdev.model.ShoppingCart;
import kvbdev.service.DeliveryService;
import kvbdev.service.DeliveryServiceImpl;

import java.util.Optional;
import java.util.function.Consumer;

public class MakeOrderPage extends AbstractPage {
    protected final Consumer<Optional<Order>> newOrderConsumer;
    protected final ShoppingCart shoppingCart;
    protected final Presenter<Order> orderPresenter;

    public MakeOrderPage(
            String pageTitle,
            ShoppingCart shoppingCart,
            Presenter<Order> orderPresenter,
            Consumer<Optional<Order>> newOrderConsumer
    ) {
        super(pageTitle);
        this.shoppingCart = shoppingCart;
        this.orderPresenter = orderPresenter;
        this.newOrderConsumer = newOrderConsumer;
    }

    @Override
    public String getView() {
        return "";
    }

    protected DeliveryService getDeliveryService() {
        return DeliveryServiceImpl.getInstance();
    }

    @Override
    public void handle(InteractiveChannel channel) {
        if (shoppingCart.isEmpty()) {
            channel.println("Невозможно создать заказ. Корзина пуста.");
            return;
        }

        OrderBuilder orderBuilder = new OrderBuilder();

        channel.println("Введите адрес доставки (оставьте пустым для самовывоза):");
        String address = channel.readLine();
        if (!address.isEmpty()) {
            Delivery delivery = getDeliveryService().getByAddress(address);

            channel.println("Введите имя получателя: ");
            String contact = channel.readLine();
            delivery.setContact(contact);

            orderBuilder.delivery(delivery);
        }

        orderBuilder.shoppingCart(new ShoppingCart(shoppingCart));
        Order order = orderBuilder.build();

        channel.println(orderPresenter.toString(order));

        channel.println("Подтвердите заказ (оставьте пустым для отмены):");
        String inputStr = channel.readLine();
        if (inputStr.isEmpty()) {
            channel.println("Оформление заказа прервано.");
            newOrderConsumer.accept(Optional.empty());
        } else {
            newOrderConsumer.accept(Optional.of(order));
        }
    }
}
