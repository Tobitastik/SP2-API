package apivision.daos;

import apivision.dtos.AdoptionDTO;

import java.util.List;

public interface IDAO<T> {
    List<AdoptionDTO> getAll();
    T getById(int id);
    void save(T entity);
    void update(T entity);
    void delete(int id);
}
