package kvbdev.menu;

import kvbdev.menu.view.BasketView;
import kvbdev.model.Basket;

public class BasketRemovePage extends MenuPageWithBodyImpl {
    protected final Basket basket;

    public BasketRemovePage(String pageTitle, Basket basket, BasketView basketView) {
        super(pageTitle, basketView);
        this.basket = basket;
    }

    @Override
    public boolean exec(String str) {
        try {
            Long id = Long.parseLong(str);
            basket.findById(id)
                    .ifPresent(basket::remove);
        }catch(NumberFormatException ex){
            return super.exec(str);
        }
        return true;
    }
}
