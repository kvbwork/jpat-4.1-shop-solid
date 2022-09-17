package kvbdev.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Basket {
    protected final Map<Product, Long> map;

    public Basket() {
        map = new HashMap<>();
    }

    public void add(Product product) {
        map.merge(product, 1L, (oldValue, newValue) -> oldValue + newValue);
    }

    public Optional<Product> findById(Long id) {
        return map.keySet()
                .stream()
                .filter(product -> product.getId().equals(id))
                .findAny();
    }

    public void remove(Product product) {
        map.remove(product);
    }

    public Set<Entry<Product, Long>> entrySet() {
        return map.entrySet();
    }

    public long getTotal() {
        return map.entrySet()
                .stream()
                .map(e -> e.getKey().getPrice() * e.getValue())
                .collect(Collectors.summarizingLong(v -> v))
                .getSum();
    }

}
