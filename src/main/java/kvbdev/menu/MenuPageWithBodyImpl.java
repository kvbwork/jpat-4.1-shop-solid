package kvbdev.menu;

import kvbdev.menu.view.BodyView;

public class MenuPageWithBodyImpl extends MenuPageImpl{
    protected final BodyView bodyView;

    public MenuPageWithBodyImpl(String pageTitle, BodyView bodyView) {
        super(pageTitle);
        this.bodyView = bodyView;
    }

    public String getView() {
        StringBuilder sb = new StringBuilder(bodyView.getView());
        sb.append("\n").append(super.getView());
        return sb.toString();
    }

}
