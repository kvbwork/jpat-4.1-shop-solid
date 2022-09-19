package kvbdev.menu.impl;

import kvbdev.menu.AbstractItemListPage;
import kvbdev.model.Order;
import kvbdev.model.Product;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class OrderListPage extends AbstractItemListPage<Order> {
    private static String ITEM_LINE_FORMAT = "Заказ %d от %s %d строк на сумму %d руб. (%s)";
    protected final List<Order> orderList;

    public OrderListPage(String pageTitle, List<Order> orderList, Consumer<Order> itemAction) {
        super(
                pageTitle,
                o -> String.valueOf(o.getId()),
                o -> String.format(ITEM_LINE_FORMAT,
                        o.getId(),
                        o.getDateTime().format(DateTimeFormatter.ISO_DATE),
                        o.getBasket().size(),
                        o.getTotal(),
                        o.getOrderStatus()
                ),
                itemAction
        );
        this.orderList = orderList;
    }

    @Override
    public String getView() {
        updateView();
        return getMapView(items) + "\n" +
                getMapView(actions);
    }

    public void updateView(){
        items.clear();
        orderList.forEach(this::add);
    }

}
