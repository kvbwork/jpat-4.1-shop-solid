package kvbdev;

import kvbdev.menu.ActionListPage;
import kvbdev.menu.InteractivePage;
import kvbdev.menu.InteractivePagesHandler;
import kvbdev.menu.InteractivePagesRegister;
import kvbdev.menu.impl.*;
import kvbdev.view.Presenter;
import kvbdev.view.PresentersRegister;
import kvbdev.view.impl.DeliveryPresenterImpl;
import kvbdev.view.impl.OrderPresenterImpl;
import kvbdev.view.impl.ShoppingCartPresenterImpl;
import kvbdev.model.Delivery;
import kvbdev.model.Order;
import kvbdev.model.Product;
import kvbdev.model.ShoppingCart;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleShopApplication implements PresentersRegister, InteractivePagesRegister {

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
        registerPresenterFor(Delivery.class, deliveryPresenter);

        ShoppingCartPresenterImpl shoppingCartPresenter = new ShoppingCartPresenterImpl();
        registerPresenterFor(ShoppingCart.class, shoppingCartPresenter);

        OrderPresenterImpl orderPresenter = new OrderPresenterImpl(shoppingCartPresenter, deliveryPresenter);
        registerPresenterFor(Order.class, orderPresenter);
    }

    protected void configureMenu(InteractivePagesHandler session) {
        ActionListPage mainPage = new ActionListPage("?????????????? ????????");
        mainPage.add("1", "???????????? ??????????????", () -> setPage("product"));
        mainPage.add("2", "??????????????", () -> setPage("shopping_cart"));
        mainPage.add("3", "???????????????? ??????????????", () -> setPage("orders"));
        mainPage.add("x", "???????????????????? ????????????", this::stop);
        registerPage("main", mainPage);

        ProductListPage productPage = new ProductListPage("???????????? ??????????????", productRepository, p -> {
            shoppingCart.add(p);
            session.println("?????????????????? ?? ??????????????: " + p.getName() + ". ??????????: " + shoppingCart.getCount(p).orElse(0L) + " ????.");
        });
        productPage.add("x", "??????????", () -> setPage("main"));
        registerPage("product", productPage);

        ShoppingCartPage shoppingCartPage = new ShoppingCartPage("??????????????", shoppingCart, getPresenterFor(ShoppingCart.class));
        shoppingCartPage.add("=", "???????????????? ??????????", () -> setPage("make_order"));
        shoppingCartPage.add("-", "?????????????? ????????????", () -> shoppingCartPage.removeItemAction(session));
        shoppingCartPage.add("x", "??????????", () -> setPage("main"));
        registerPage("shopping_cart", shoppingCartPage);

        OrderListPage ordersPage = new OrderListPage("???????????????? ??????????????", ordersRepository, order -> {
            System.out.println("showOrder");
            ShowOrderPage showOrderPage = new ShowOrderPage(order, getPresenterFor(Order.class));
            showOrderPage.add("x", "??????????", () -> setPage("main"));
            session.setPage(showOrderPage);
        });
        ordersPage.add("x", "??????????", () -> setPage("main"));
        registerPage("orders", ordersPage);

        MakeOrderPage makeOrderPage = new MakeOrderPage("???????????????????? ????????????", shoppingCart, getPresenterFor(Order.class),
                optOrder -> optOrder.ifPresentOrElse(
                        order -> {
                            order.setId((long) ordersRepository.size());
                            ordersRepository.add(order);
                            session.println("?????????? ????????????????.");
                            shoppingCart.clear();
                            setPage("orders");
                        },
                        () -> setPage("shopping_cart"))
        );
        registerPage("make_order", makeOrderPage);

        session.setPage(mainPage);
    }

    @Override
    public <T> Presenter<T> getPresenterFor(Class<T> clazz) {
        return (Presenter<T>) presentersMap.get(clazz);
    }

    @Override
    public <T> void registerPresenterFor(Class<T> clazz, Presenter<T> presenter) {
        presentersMap.put(clazz, presenter);
    }

    @Override
    public InteractivePage getPage(String name) {
        return pagesMap.get(name);
    }

    @Override
    public void registerPage(String nameId, InteractivePage page) {
        pagesMap.put(nameId, page);
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
