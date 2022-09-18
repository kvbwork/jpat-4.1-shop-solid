package kvbdev.menu;

import kvbdev.model.Basket;
import kvbdev.model.Product;

import java.util.Arrays;
import java.util.Comparator;

public class BasketPage2 extends AbstractItemListPage<Product> {
    private static String ITEM_LINE_FORMAT = "\t%-80.80s %d шт x %d руб = %d руб";
    protected final Basket basket;

    public BasketPage2(String pageTitle, Basket basket) {
        super(
                pageTitle,
                p -> String.valueOf(p.getId()),
                p -> {
                    Long count = basket.getCount(p).orElse(0L);
                    Long total = p.getPrice() * count;
                    return String.format(ITEM_LINE_FORMAT, p.getName(), count, p.getPrice(), total);
                },
                p -> {}
        );
        this.basket = basket;
    }

    @Override
    protected boolean runItemAction(String itemId) {
        return false;
    }

    @Override
    public String getView() {
        updateView();
        return getMapView(items) + "\n" +
                "Итог: " + basket.getTotal() + "\n" +
                getMapView(actions);
    }

    public void updateView(){
        items.clear();
        basket.entrySet()
                .stream()
                .sorted(Comparator.comparingLong(e -> e.getKey().getId()))
                .forEach(e -> add(e.getKey()));
    }

    public void removeItemAction(InteractiveChannel channel) {
        channel.println("Введите ключ записи:");
        basket.findById(Long.parseLong(channel.readLine()))
                .ifPresentOrElse(basket::remove,
                        () -> channel.println("Не найдено"));
    }

    @Override
    public void handle(InteractiveChannel channel) {
        super.handle(channel);
    }
}
