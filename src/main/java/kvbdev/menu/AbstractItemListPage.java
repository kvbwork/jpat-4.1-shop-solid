package kvbdev.menu;

import kvbdev.menu.impl.ActionListPage;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractItemListPage<T> extends ActionListPage {
    protected final SortedMap<String, Action> itemActionsMap;
    protected final Iterable<T> itemSource;
    protected final Function<T, String> keyExtractor;
    protected final Function<T, String> titleExtractor;
    protected final Consumer<T> itemAction;

    protected AbstractItemListPage(
            String pageTitle,
            Iterable<T> itemSource,
            Function<T, String> keyExtractor,
            Function<T, String> titleExtractor,
            Consumer<T> itemAction
    ) {
        super(pageTitle);
        this.itemActionsMap = new TreeMap<>();
        this.itemSource = itemSource;
        this.keyExtractor = keyExtractor;
        this.titleExtractor = titleExtractor;
        this.itemAction = itemAction;
    }

    protected boolean runItemAction(String itemId) {
        Action cmd = itemActionsMap.get(itemId);
        if (cmd == null) return false;
        cmd.run();
        return true;
    }

    protected void addItem(T item) {
        itemActionsMap.put(keyExtractor.apply(item), new Action(titleExtractor.apply(item), () -> itemAction.accept(item)));
    }

    protected void updateView() {
        itemActionsMap.clear();
        itemSource.forEach(this::addItem);
    }

    @Override
    public String getView() {
        updateView();
        return getMapView(itemActionsMap) + "\n" + getMapView(actions);
    }

    @Override
    public void handle(InteractiveChannel channel) {
        while (true) {
            String input = channel.readLine();
            if (runItemAction(input)) continue;
            if (runAction(input)) break;
            channel.println("Команда не распознана, повторите ввод.");
        }
    }
}
