package kvbdev.menu.view.impl;

import kvbdev.menu.view.Presenter;
import kvbdev.model.Delivery;

import java.util.Optional;

public class DeliveryPresenterImpl implements Presenter<Optional<Delivery>> {

    public DeliveryPresenterImpl() {
    }

    @Override
    public String toString(Optional<Delivery> optionalDelivery) {
        StringBuilder sb = new StringBuilder();
        optionalDelivery.ifPresentOrElse(
                delivery -> sb.append("Доставка:").append("\n")
                        .append("Адрес: ").append(delivery.getAddress()).append("\n")
                        .append("Контакт: ").append(delivery.getContact()).append("\n")
                        .append("Стоимость доставки: ").append(delivery.getCost()).append(" руб."),
                () -> sb.append("Самовывоз")
        );
        return sb.toString();
    }
}
