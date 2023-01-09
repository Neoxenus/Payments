package com.my.model.dao;

import java.util.List;

public interface GenericDao<T> extends AutoCloseable{

    void add(T entity);
    T findById(Integer id);
    List<T> findAll();
    void update(T entity);
    void delete(Integer id);
    void deleteEntity(T entity);

    void close();
}
