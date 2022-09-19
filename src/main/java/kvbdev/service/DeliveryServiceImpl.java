package kvbdev.service;

import kvbdev.model.Delivery;

public class DeliveryServiceImpl implements DeliveryService {
    private static DeliveryServiceImpl INSTANCE;

    private DeliveryServiceImpl() {
    }

    public static DeliveryServiceImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DeliveryServiceImpl();
        }
        return INSTANCE;
    }

    @Override
    public Delivery getByAddress(String address) {
        long cost = Math.abs(address.hashCode() % 5_000);
        return new Delivery(address, cost);
    }
}
