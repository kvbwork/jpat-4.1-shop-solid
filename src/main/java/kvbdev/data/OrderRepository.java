package kvbdev.data;

import kvbdev.model.Customer;
import kvbdev.model.Order;

public interface OrderRepository extends Repository<Order>{

    Iterable<Order> findByCustomer(Customer customer);

}
