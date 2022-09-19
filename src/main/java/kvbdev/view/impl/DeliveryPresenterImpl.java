package kvbdev.view.impl;

import kvbdev.view.Presenter;
import kvbdev.model.Delivery;

import java.util.Optional;

public class DeliveryPresenterImpl implements Presenter<Delivery> {

    public DeliveryPresenterImpl() {
    }

    @Override
    public String toString(Optional<Delivery> optDelivery) {
        return optDelivery
                .map(delivery -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Доставка:").append("\n")
                            .append("Адрес: ").append(delivery.getAddress()).append("\n")
                            .append("Контакт: ").append(delivery.getContact()).append("\n")
                            .append("Стоимость доставки: ").append(delivery.getCost()).append(" руб.");
                    return sb.toString();
                }).orElse("Самовывоз");
    }
}
