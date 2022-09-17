package kvbdev;

import kvbdev.menu.*;
import kvbdev.menu.view.BasketView;
import kvbdev.model.*;

public class Main {

    public static void main(String[] args) throws Exception {

        Basket basket = new Basket();
        BasketView basketView = new BasketView(basket);

        ConsoleInteractiveMenu menu = new ConsoleInteractiveMenu();

        ProductListPage productPage = new ProductListPage("Товары", basket);
        MenuPageWithBodyImpl basketPage = new MenuPageWithBodyImpl("Корзина", basketView);
        BasketRemovePage basketRemovePage = new BasketRemovePage("Корзина / Удаление", basket, basketView);
        MenuPageImpl newOrderPage = new MenuPageImpl("Оформить заказ"); // TODO
        MenuPageImpl ordersPage = new MenuPageImpl("История заказов");
        MenuPageImpl mainPage = new MenuPageImpl("Главное меню");

        productPage.add(new Product(1L, "Корзина металлическая для хранения овощей и фруктов, 26,5*24,5*17 см MARMITON", 700L));
        productPage.add(new Product(2L, "Одеяло Соната Овечья шерсть, всесезонное, 140 х 205 см, бежевый", 835L));
        productPage.add(new Product(3L, "Столовый сервиз Luminarc Diwali H5869/P2920/P2947/P2961, 6 персон, 19 предм.", 2900L));
        productPage.add(new Product(4L, "Скатерть CobraJet гибкое стекло, 70х50 см, толщина 1 мм, прозрачный", 890L));
        productPage.add(new Product(5L, "Чайник электрический SCARLETT SC-EK27G93 с подсветкой, объем 1.7 л", 1479L));
        productPage.add("x", "Выход", () -> menu.setPage(mainPage));

        basketPage.add("=", "Оформить заказ", () -> menu.setPage(newOrderPage));
        basketPage.add("-", "Удалить из корзины", () -> menu.setPage(basketRemovePage));
        basketPage.add("x", "Выход", () -> menu.setPage(mainPage));

        basketRemovePage.add("x", "Выход", () -> menu.setPage(basketPage));

        mainPage.add("1", "Список товаров", () -> menu.setPage(productPage));
        mainPage.add("2", "Корзина", () -> menu.setPage(basketPage));
        mainPage.add("3", "Статус заказов", () -> menu.setPage(ordersPage));
        mainPage.add("x", "Завершение работы", () -> System.exit(0));

        menu.setPage(mainPage);
        menu.start();
    }

}
