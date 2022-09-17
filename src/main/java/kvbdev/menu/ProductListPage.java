package kvbdev.menu;

import kvbdev.model.Basket;
import kvbdev.model.Product;

public class ProductListPage extends MenuPageImpl{
    protected final String ITEM_TITLE_FORMAT = "%-80.80s %6d руб";
    protected final Basket basket;

    public ProductListPage(String pageTitle, Basket basket) {
        super(pageTitle);
        this.basket = basket;
    }

    public void add(Product product) {
        String lineText = String.format(ITEM_TITLE_FORMAT,
                product.getName(), product.getPrice());
        super.add(product.getId().toString(), lineText, () -> {
            basket.add(product);
            System.out.println("Товар добавлен в корзину: " + product.getName());
        });
    }

}
