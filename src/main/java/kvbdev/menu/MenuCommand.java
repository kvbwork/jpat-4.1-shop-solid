package kvbdev.menu;

public class MenuCommand implements Runnable {
    protected final String title;
    protected final Runnable runnable;

    public MenuCommand(String title, Runnable runnable) {
        this.title = title;
        this.runnable = runnable;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public void run() {
        runnable.run();
    }
}
