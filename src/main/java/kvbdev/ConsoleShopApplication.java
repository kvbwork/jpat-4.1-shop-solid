package kvbdev;

import kvbdev.menu.InteractivePage;
import kvbdev.menu.impl.*;
import kvbdev.menu.view.Presenter;
import kvbdev.menu.view.impl.DeliveryPresenterImpl;
import kvbdev.menu.view.impl.OrderPresenterImpl;
import kvbdev.menu.view.impl.ShoppingCartPresenterImpl;
import kvbdev.model.Delivery;
import kvbdev.model.Order;
import kvbdev.model.Product;
import kvbdev.model.ShoppingCart;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleShopApplication {

    protected final List<Product> productRepository;
    protected final List<Order> ordersRepository;
    protected final ShoppingCart shoppingCart;
    protected final InteractivePagesHandler session;

    private Map<Class<?>, Presenter<?>> presentersMap = new HashMap<>();
    private Map<String, InteractivePage> pagesMap = new HashMap<>();

    public ConsoleShopApplication(List<Product> productRepository, List<Order> ordersRepository) {
        this.productRepository = productRepository;
        this.ordersRepository = ordersRepository;
        shoppingCart = new ShoppingCart();
        session = new InteractivePagesHandler(System.in, System.out);
        configure();
    }

    protected void configure() {
        configurePresenters();
        configureMenu(session);
    }

    protected void configurePresenters() {
        DeliveryPresenterImpl deliveryPresenter = new DeliveryPresenterImpl();
        presentersMap.put(Delivery.class, deliveryPresenter);

        ShoppingCartPresenterImpl shoppingCartPresenter = new ShoppingCartPresenterImpl();
        presentersMap.put(ShoppingCart.class, shoppingCartPresenter);

        OrderPresenterImpl orderPresenter = new OrderPresenterImpl(shoppingCartPresenter, deliveryPresenter);
        presentersMap.put(Order.class, orderPresenter);
    }

    protected void configureMenu(InteractivePagesHandler session) {
        ActionListPage mainPage = new ActionListPage("Главное меню");
        mainPage.add("1", "Список товаров", () -> setPage("product"));
        mainPage.add("2", "Корзина", () -> setPage("shopping_cart"));
        mainPage.add("3", "Просмотр заказов", () -> setPage("orders"));
        mainPage.add("x", "Завершение работы", this::stop);
        pagesMap.put("main", mainPage);

        ProductListPage productPage = new ProductListPage("Список товаров", productRepository, p -> {
            shoppingCart.add(p);
            session.println("Добавлено в корзину: " + p.getName() + ". Всего: " + shoppingCart.getCount(p).orElse(0L) + " шт.");
        });
        productPage.add("x", "Выход", () -> setPage("main"));
        pagesMap.put("product", productPage);

        ShoppingCartPage shoppingCartPage = new ShoppingCartPage("Корзина", shoppingCart, getPresenterFor(ShoppingCart.class));
        shoppingCartPage.add("=", "Оформить заказ", () -> setPage("make_order"));
        shoppingCartPage.add("-", "Удалить строку", () -> shoppingCartPage.removeItemAction(session));
        shoppingCartPage.add("x", "Выход", () -> setPage("main"));
        pagesMap.put("shopping_cart", shoppingCartPage);

        OrderListPage ordersPage = new OrderListPage("Статус заказов", ordersRepository, order -> {
            System.out.println("showOrder");
            ShowOrderPage showOrderPage = new ShowOrderPage(order, getPresenterFor(Order.class));
            showOrderPage.add("x", "Выход", () -> setPage("main"));
            session.setPage(showOrderPage);
        });
        ordersPage.add("x", "Выход", () -> setPage("main"));
        pagesMap.put("orders", ordersPage);

        MakeOrderPage makeOrderPage = new MakeOrderPage("Оформление заказа", shoppingCart, getPresenterFor(Order.class),
                optOrder -> optOrder.ifPresentOrElse(
                        order -> {
                            order.setId((long) ordersRepository.size());
                            ordersRepository.add(order);
                            session.println("Заказ сохранен.");
                            shoppingCart.clear();
                            setPage("orders");
                        },
                        () -> setPage("shopping_cart"))
        );
        pagesMap.put("make_order", makeOrderPage);

        session.setPage(mainPage);
    }

    public <T> Presenter<T> getPresenterFor(Class<T> clazz) {
        return (Presenter<T>) presentersMap.get(clazz);
    }

    public InteractivePage getPage(String name) {
        return pagesMap.get(name);
    }

    public void setPage(String name) {
        session.setPage(getPage(name));
    }

    public void start() {
        session.mainLoop();
    }

    public void stop() {
        try {
            session.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
