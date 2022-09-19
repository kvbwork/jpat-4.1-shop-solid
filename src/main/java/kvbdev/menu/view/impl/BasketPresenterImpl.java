package kvbdev.menu.view.impl;

import kvbdev.menu.view.Presenter;
import kvbdev.model.Basket;
import kvbdev.model.Product;

import java.util.Comparator;
import java.util.stream.Collectors;

public class BasketPresenterImpl implements Presenter<Basket> {
    protected String ITEM_LINE_FORMAT = "%d\t%-80.80s %d шт x %d руб = %d руб";

    public BasketPresenterImpl() {
    }

    @Override
    public String toString(Basket basket) {
        StringBuilder sb = new StringBuilder();
        sb.append(basket.entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> e.getKey().getId()))
                .map(e -> {
                    Product product = e.getKey();
                    long count = e.getValue();
                    long total = product.getPrice() * count;
                    return String.format(ITEM_LINE_FORMAT,
                            product.getId(), product.getName(), count, product.getPrice(), total);
                }).collect(Collectors.joining("\n"))
        );

        sb.append("\n").append("Сумма: ").append(basket.getTotal()).append(" руб");
        return sb.toString();
    }
}