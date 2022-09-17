package kvbdev.data;

import java.util.Optional;

public interface Repository<T> {

    Optional<T> findById(Object id);

    Iterable<T> findAll();

    T save(T obj);
}
