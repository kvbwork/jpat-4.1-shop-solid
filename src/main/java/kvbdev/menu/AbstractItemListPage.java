package kvbdev.menu;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractItemListPage<T> extends ActionListPage {
    protected final Map<String, Action> items;
    protected final Function<T, String> keyExtractor;
    protected final Function<T, String> titleExtractor;
    protected final Consumer<T> itemAction;

    protected AbstractItemListPage(String pageTitle, Function<T, String> keyExtractor, Function<T, String> titleExtractor, Consumer<T> itemAction) {
        super(pageTitle);
        this.items = new LinkedHashMap<>();
        this.keyExtractor = keyExtractor;
        this.titleExtractor = titleExtractor;
        this.itemAction = itemAction;
    }

    protected boolean runItemAction(String itemId) {
        Action cmd = items.get(itemId);
        if (cmd == null) return false;
        cmd.run();
        return true;
    }

    public void add(T item) {
        items.put(keyExtractor.apply(item), new Action(titleExtractor.apply(item), () -> itemAction.accept(item)));
    }

    public void addAll(Iterable<T> items) {
        for (T item : items) {
            add(item);
        }
    }

    @Override
    public String getView() {
        return getMapView(items) + "\n" + getMapView(actions);
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
