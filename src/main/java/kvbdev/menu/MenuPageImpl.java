package kvbdev.menu;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuPageImpl implements MenuPage {
    protected final String pageTitle;
    protected final Map<String, MenuCommand> map;

    public MenuPageImpl(String pageTitle) {
        this.pageTitle = pageTitle;
        this.map = new LinkedHashMap<>();
    }

    public boolean exec(String str) {
        MenuCommand cmd = map.get(str);
        if (cmd == null) return false;
        cmd.run();
        return true;
    }

    public void add(String commandStr, String itemTitle, Runnable runnable) {
        map.put(commandStr, new MenuCommand(itemTitle, runnable));
    }

    public String getView() {
        return map.entrySet()
                .stream()
                .map(e -> e.getKey() + "\t" + e.getValue().getTitle())
                .collect(Collectors.joining("\n"));
    }

    public String getTitle() {
        return pageTitle;
    }

}
