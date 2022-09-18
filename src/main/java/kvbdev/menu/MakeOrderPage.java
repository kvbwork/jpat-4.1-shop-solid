package kvbdev.menu;

import kvbdev.menu.view.Presenter;
import kvbdev.model.Basket;
import kvbdev.model.Delivery;
import kvbdev.model.Order;
import kvbdev.model.OrderBuilder;
import kvbdev.service.DeliveryService;
import kvbdev.service.DeliveryServiceImpl;

import java.util.function.Consumer;

public class MakeOrderPage extends AbstractPage {
    protected final Consumer<Order> newOrderConsumer;
    protected final Basket basket;
    protected final Presenter<Order> orderPresenter;

    public MakeOrderPage(
            String pageTitle,
            Basket basket,
            Presenter<Order> orderPresenter,
            Consumer<Order> newOrderConsumer
    ) {
        super(pageTitle);
        this.basket = basket;
        this.orderPresenter = orderPresenter;
        this.newOrderConsumer = newOrderConsumer;
    }

    @Override
    public String getView() {
        return "";
    }

    protected DeliveryService getDeliveryService(){
        return DeliveryServiceImpl.getInstance();
    }

    @Override
    public void handle(InteractiveChannel channel) {
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

        orderBuilder.basket(new Basket(basket));
        Order order = orderBuilder.build();

        channel.println(orderPresenter.toString(order));

        channel.println("Подтвердите заказ (оставьте пустым для отмены):");
        String inputStr = channel.readLine();
        if (inputStr.isEmpty()) return;

        newOrderConsumer.accept(order);
    }
}
