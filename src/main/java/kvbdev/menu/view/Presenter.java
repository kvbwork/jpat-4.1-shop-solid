package kvbdev.menu.view;

@FunctionalInterface
public interface Presenter<T> {
    String toString(T obj);

}
