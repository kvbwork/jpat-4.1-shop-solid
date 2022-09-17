package kvbdev.menu;

import kvbdev.menu.view.BodyView;

public interface MenuPage extends BodyView {

    String getTitle();

    String getView();

    boolean exec(String str);

}
