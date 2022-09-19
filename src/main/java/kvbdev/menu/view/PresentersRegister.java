package kvbdev.menu.view;

public interface PresentersRegister {
    <T> Presenter<T> getPresenterFor(Class<T> clazz);

    <T> void registerPresenterFor(Class<T> clazz, Presenter<T> presenter);
}
