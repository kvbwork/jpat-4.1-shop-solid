package kvbdev.menu;

import kvbdev.model.Product;

import java.util.function.Consumer;

public class ProductListPage2 extends AbstractItemListPage<Product> {
    private static final String ITEM_LINE_FORMAT = "%-80.80s %6d руб";

    public ProductListPage2(String pageTitle, Consumer<Product> itemAction) {
        super(
                pageTitle,
                p -> String.valueOf(p.getId()),
                p -> String.format(ITEM_LINE_FORMAT, p.getName(), p.getPrice()),
                itemAction
        );
    }

}
