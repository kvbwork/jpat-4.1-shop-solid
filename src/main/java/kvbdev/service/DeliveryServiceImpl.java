package kvbdev.service;

import kvbdev.model.Delivery;

import java.util.Random;

public class DeliveryServiceImpl implements DeliveryService {
    private static DeliveryServiceImpl INSTANCE;

    protected final Random random = new Random();

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
        long cost = Math.abs(random.nextLong() % 5_000L);
        return new Delivery(address, cost);
    }
}
