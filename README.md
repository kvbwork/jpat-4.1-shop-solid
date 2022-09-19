## Консольное приложение "Магазин"

Возможности программы:

- Вывод доступных для покупки товаров
- Составление продуктовой корзины пользователя
- Оформление заказа с учетом доставки
- Трекинг заказа в системе доставки

### Примеры избегания магических чисел

1. Класс `kvbdev.service.DeliveryServiceImpl` использует константу [MAX_VALUE](https://github.com/kvbwork/jpat-4.1-shop-solid/blob/8a1e60989060b127ed7bed19ab7b2a67b1715553/src/main/java/kvbdev/service/DeliveryServiceImpl.java#L21) для ограничения максимального значения генерируемой стоимости доставки.

2. Класс `kvbdev.model.ShoppingCart` использует константу [INITIAL_COUNT_VALUE](https://github.com/kvbwork/jpat-4.1-shop-solid/blob/8a1e60989060b127ed7bed19ab7b2a67b1715553/src/main/java/kvbdev/model/ShoppingCart.java#L24) как первоначальное значение счетчика определенного товара в корзине.

### Примеры использования принципа DRY (Don’t Repeat Yourself)

1. Пакет классов для создания интерактивных страниц консольного меню `kvbdev.menu` построен через дополнение уже существующих классов наследованием или передачей функций в параметрах.
  - Класс `kvbdev.menu.impl.ActionListPage` умеет выводить на экран список возможных команд с идентификаторами и выполнять их. Класс `kvbdev.menu.impl.ShoppingCartPage` расширяет класс `ActionListPage` и с минимальными изменениями [добавляет](https://github.com/kvbwork/jpat-4.1-shop-solid/blob/8a1e60989060b127ed7bed19ab7b2a67b1715553/src/main/java/kvbdev/menu/impl/ShoppingCartPage.java#L20) возможность выводить список товаров в корзине. Нам не приходится снова реализовывать вывод меню заданных команд.
2. При выводе на экран текстового представления `kvbdev.model.Order` используется функционал класса `kvbdev.view.impl.OrderPresenterImpl`. Класс сам использует другие преобразователи, получаемые из параметров: `ShoppingCartPresenterImpl` и `DeliveryPresenterImpl`. Так мы [избегаем повторения](https://github.com/kvbwork/jpat-4.1-shop-solid/blob/5352a3d1bb726ca83a57c6f86c2175fd1d6559cc/src/main/java/kvbdev/view/impl/OrderPresenterImpl.java#L27) реализации функций преобразования.

### Примеры применения принципов SOLID

#### Single Responsibility Principle

1. Все классы пакета `kvbdev.model` предназначены только для хранения и передачи данных. Они ничего не выводят на экран. Не вызывают системные методы. Не читают ввод. Например класс [Delivery](https://github.com/kvbwork/jpat-4.1-shop-solid/blob/5352a3d1bb726ca83a57c6f86c2175fd1d6559cc/src/main/java/kvbdev/model/Delivery.java).

2. Классы пакета `kvbdev.view.impl` предназначены только для создания текстового представления соответствующих POJO моделей (`Order`, `Delivery`, `ShoppingCart`). Они не обрабатывают пользовательский ввод. Ничего не выводят на экран. Не изменяют переданные им объекты. Например класс [DeliveryPresenterImpl](https://github.com/kvbwork/jpat-4.1-shop-solid/blob/5352a3d1bb726ca83a57c6f86c2175fd1d6559cc/src/main/java/kvbdev/view/impl/DeliveryPresenterImpl.java).

#### Open Closed Principle

1. Класс `kvbdev.menu.ActionListPage` выводит обрабатываемые команды в виде меню. Его наследник `kvbdev.menu.ShowOrderPage` переопределяет метод [.getView()](https://github.com/kvbwork/jpat-4.1-shop-solid/blob/5352a3d1bb726ca83a57c6f86c2175fd1d6559cc/src/main/java/kvbdev/menu/impl/ShowOrderPage.java#L18) и выводит текстовое представление `Order` до пунктов меню с командой `Выход`.

2. Класс `kvbdev.ConsoleShopApplication` спроектирован так, чтобы предоставить его наследникам возможность расширения функционала без изменения исходного класса. Например переопределение метода [.configureMenu()](https://github.com/kvbwork/jpat-4.1-shop-solid/blob/5352a3d1bb726ca83a57c6f86c2175fd1d6559cc/src/main/java/kvbdev/ConsoleShopApplication.java#L57)  позволит создать свой набор интерактивных экранов. А переопределение метода [.configurePresenters()](https://github.com/kvbwork/jpat-4.1-shop-solid/blob/5352a3d1bb726ca83a57c6f86c2175fd1d6559cc/src/main/java/kvbdev/ConsoleShopApplication.java#L46) позволит дополнить набор преобразователей для новых объектов.

#### Liskov Substitution Principle

1. Есть цепочка наследования классов и интерфейсов:
```
 InteractivePage      - базовый интерфейс
        |
   AbstractPage       - хранит базовые данные
        |
  ActionListPage      - выводит список команд и реагирует на них
        |
AbstractItemListPage  - выводит список элементов и команд. Реагирует на команды.
        |
  ProductListPage     - выводит список товаров из источника + весь функционал выше
```
Класс `kvbdev.menu.InteractivePagesHandler` занимается сменой страниц, передачей им пользовательского ввода и передачи результата. Ему для работы достаточно знать, что они потомки `InteractivePage`. Так выполняется принцип, когда [потомки могут замещать предка](https://github.com/kvbwork/jpat-4.1-shop-solid/blob/5352a3d1bb726ca83a57c6f86c2175fd1d6559cc/src/main/java/kvbdev/menu/InteractivePagesHandler.java#L23).

#### Interface Segregation Principle

Класс `kvbdev.ConsoleShopApplication` реализует интерфейсы [PresentersRegister](https://github.com/kvbwork/jpat-4.1-shop-solid/blob/1fe4ec26c66bcfa7582114d6eb95ebfc67cf2a35/src/main/java/kvbdev/view/PresentersRegister.java) и [InteractivePagesRegister](https://github.com/kvbwork/jpat-4.1-shop-solid/blob/1fe4ec26c66bcfa7582114d6eb95ebfc67cf2a35/src/main/java/kvbdev/menu/InteractivePagesRegister.java). Они спроектированы минималистично на случай если функцию регистра объектов будет выполнять внешний класс. В таком случае при реализации одного регистра, не придется имплементировать методы относящиеся ко второму.

#### Dependency Inversion Principle

Объекту класса `kvbdev.view.impl.OrderPresenterImpl` в работе требуется представить в текстовом виде объекты `ShoppingCart` и `Delivery`.

Он не создает экземпляры `ShoppingCartPresenterImpl` и `DeliveryPresenterImpl` самостоятельно, чтобы не зависеть от конкретных классов и реализации. `DeliveryPresenterImpl` получает необходимые ему объекты через параметры конструктора.

Готовые объекты внедряются через [использование интерфейса](https://github.com/kvbwork/jpat-4.1-shop-solid/blob/5352a3d1bb726ca83a57c6f86c2175fd1d6559cc/src/main/java/kvbdev/view/impl/OrderPresenterImpl.java#L15) `Presenter<T>`. Поэтому внедрить можно любую реализацию подходящую по типу.