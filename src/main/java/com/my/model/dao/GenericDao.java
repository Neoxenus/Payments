package com.my.model.dao;

import java.util.List;

public interface GenericDao<T> extends AutoCloseable{

    void add(T entity);
    T findById(Long id);
    List<T> findAll();
    void update(T entity);
    void delete(Long id);
    void deleteEntity(T entity);

    void close();
}
