package kvbdev;

import kvbdev.menu.impl.*;
import kvbdev.menu.view.Presenter;
import kvbdev.menu.view.impl.BasketPresenterImpl;
import kvbdev.menu.view.impl.DeliveryPresenterImpl;
import kvbdev.menu.view.impl.OrderPresenterImpl;
import kvbdev.model.Basket;
import kvbdev.model.Delivery;
import kvbdev.model.Order;
import kvbdev.model.Product;

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

        Basket basket = new Basket();

        Presenter<Optional<Delivery>> deliveryPresenter = new DeliveryPresenterImpl();
        Presenter<Basket> basketPresenter = new BasketPresenterImpl();
        Presenter<Order> orderPresenter = new OrderPresenterImpl(basketPresenter, deliveryPresenter);

        InteractivePagesHandler session = new InteractivePagesHandler(System.in, System.out);

        ActionListPage mainPage = new ActionListPage("Главное меню");

        ProductListPage productPage = new ProductListPage("Список товаров", p -> {
            session.println("Добавлено в корзину: " + p.getName());
            basket.add(p);
        });

        BasketPage basketPage = new BasketPage("Корзина", basket);

        OrderListPage ordersPage = new OrderListPage("Статус заказов", ordersList, order -> {
            session.println(orderPresenter.toString(order));
        });

        MakeOrderPage makeOrderPage = new MakeOrderPage("Оформление заказа", basket, orderPresenter,
                order -> {
                    order.setId(Long.valueOf(ordersList.size()));
                    ordersList.add(order);
                    session.println("Заказ сохранен.");
                    basket.clear();
                    session.setPage(ordersPage);
                });

        mainPage.add("1", "Список товаров", () -> session.setPage(productPage));
        mainPage.add("2", "Корзина", () -> session.setPage(basketPage));
        mainPage.add("3", "Статус заказов", () -> session.setPage(ordersPage));
        mainPage.add("x", "Завершение работы", () -> System.exit(0));

        productPage.addAll(productList);
        productPage.add("x", "Выход", () -> session.setPage(mainPage));

        basketPage.add("=", "Оформить заказ", () -> session.setPage(makeOrderPage));
        basketPage.add("-", "Удалить строку", () -> basketPage.removeItemAction(session));
        basketPage.add("x", "Выход", () -> session.setPage(mainPage));

        ordersPage.add("x", "Выход", () -> session.setPage(mainPage));

        session.setPage(mainPage);
        session.mainLoop();

    }

}
