package kvbdev.model;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Basket {
    protected final Map<Product, Long> map;

    public Basket() {
        map = new HashMap<>();
    }

    public Basket(Basket sourceBasket){
        this();
        map.putAll(sourceBasket.map);
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

    public Optional<Long> getCount(Product product){
        return Optional.ofNullable(map.get(product));
    }

    public void setCount(Product product, long count){
        map.put(product, count);
    }

    public void remove(Product product) {
        map.remove(product);
    }

    public void clear(){
        map.clear();
    }

    public int size(){
        return map.size();
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
