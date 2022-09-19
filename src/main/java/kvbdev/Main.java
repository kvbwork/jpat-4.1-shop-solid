package kvbdev;

import kvbdev.menu.impl.*;
import kvbdev.menu.view.Presenter;
import kvbdev.menu.view.impl.DeliveryPresenterImpl;
import kvbdev.menu.view.impl.OrderPresenterImpl;
import kvbdev.menu.view.impl.ShoppingCartPresenterImpl;
import kvbdev.model.Delivery;
import kvbdev.model.Order;
import kvbdev.model.Product;
import kvbdev.model.ShoppingCart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws Exception {

        List<Product> productList = List.of(
                new Product(1L, "Корзина металлическая для хранения овощей и фруктов, 26,5*24,5*17 см MARMITON", 700L),
                new Product(2L, "Одеяло Соната Овечья шерсть, всесезонное, 140 х 205 см, бежевый", 835L),
                new Product(3L, "Столовый сервиз Luminarc Diwali H5869/P2920/P2947/P2961, 6 персон, 19 предм.", 2900L),
                new Product(4L, "Скатерть CobraJet гибкое стекло, 70х50 см, толщина 1 мм, прозрачный", 890L),
                new Product(5L, "Чайник электрический SCARLETT SC-EK27G93 с подсветкой, объем 1.7 л", 1479L)
        );

        List<Order> ordersList = new ArrayList<>();

        ShoppingCart shoppingCart = new ShoppingCart();

        Presenter<Optional<Delivery>> deliveryPresenter = new DeliveryPresenterImpl();
        Presenter<ShoppingCart> shoppingCartPresenter = new ShoppingCartPresenterImpl();
        Presenter<Order> orderPresenter = new OrderPresenterImpl(shoppingCartPresenter, deliveryPresenter);

        InteractivePagesHandler session = new InteractivePagesHandler(System.in, System.out);

        ActionListPage mainPage = new ActionListPage("Главное меню");

        ProductListPage productPage = new ProductListPage("Список товаров", productList, p -> {
            shoppingCart.add(p);
            session.println("Добавлено в корзину: " + p.getName() + ". Всего: " + shoppingCart.getCount(p).orElse(0L) + " шт.");
        });

        ShoppingCartPage shoppingCartPage = new ShoppingCartPage("Корзина", shoppingCart, shoppingCartPresenter);

        OrderListPage ordersPage = new OrderListPage("Статус заказов", ordersList, order -> {
            System.out.println("showOrder");
            ShowOrderPage showOrderPage = new ShowOrderPage(order, orderPresenter);
            showOrderPage.add("x", "Выход", () -> session.setPage(mainPage));
            session.setPage(showOrderPage);
        });

        MakeOrderPage makeOrderPage = new MakeOrderPage("Оформление заказа", shoppingCart, orderPresenter,
                optOrder -> optOrder.ifPresentOrElse(
                        order -> {
                            order.setId(Long.valueOf(ordersList.size()));
                            ordersList.add(order);
                            session.println("Заказ сохранен.");
                            shoppingCart.clear();
                            session.setPage(ordersPage);
                        },
                        () -> session.setPage(shoppingCartPage))
        );

        mainPage.add("1", "Список товаров", () -> session.setPage(productPage));
        mainPage.add("2", "Корзина", () -> session.setPage(shoppingCartPage));
        mainPage.add("3", "Статус заказов", () -> session.setPage(ordersPage));
        mainPage.add("x", "Завершение работы", () -> System.exit(0));

        productPage.add("x", "Выход", () -> session.setPage(mainPage));

        shoppingCartPage.add("=", "Оформить заказ", () -> session.setPage(makeOrderPage));
        shoppingCartPage.add("-", "Удалить строку", () -> shoppingCartPage.removeItemAction(session));
        shoppingCartPage.add("x", "Выход", () -> session.setPage(mainPage));

        ordersPage.add("x", "Выход", () -> session.setPage(mainPage));

        session.setPage(mainPage);
        session.mainLoop();

    }

}
