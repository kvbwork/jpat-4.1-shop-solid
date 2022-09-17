package kvbdev.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class InMemoryRepositoryImpl<T> implements Repository<T>{

    protected Map<Long, T> map;

    public InMemoryRepositoryImpl() {
        map = new HashMap<>();
    }

    @Override
    public Optional<T> findById(Object id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Iterable<T> findAll() {
        return map.values();
    }

//    @Override
//    public T save(T obj);
        //return map.put(obj.getId(), obj);
}
