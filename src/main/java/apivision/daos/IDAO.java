package apivision.daos;

import java.util.List;
import java.util.Optional;

public interface IDAO<T> {
    List<T> getAll();
    T getById(int id);
    void save(T entity);
    void update(T entity);
    void delete(int id);
}
