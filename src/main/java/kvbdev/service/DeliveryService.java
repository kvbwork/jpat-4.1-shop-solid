package kvbdev.service;

import kvbdev.model.Delivery;

public interface DeliveryService {
    Delivery getByAddress(String address);
}
