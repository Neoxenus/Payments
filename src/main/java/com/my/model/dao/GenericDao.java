package com.my.model.dao;

import java.util.List;

/**
 * Interface GenericDao is used to access the information from the database.
 * @author Volodymyr Kyryliuk
 */
public interface GenericDao<T> extends AutoCloseable{

    /**
     * The method adds information about the entity to the database.
     *
     * @param entity  - the entity that needs to be saved to the database.
     */
    void add(T entity);

    /**
     * The method gets the information about the entity from the database by id.
     *
     * @param id - entity's id.
     * @return entity with such id.
     */
    T findById(Integer id);

    /**
     * The method gets the information about all the entities from the database.
     * @return list of all entities
     */
    List<T> findAll();

    /**
     * The method updates the information about the entity from the database.
     *
     * @param entity - the entity that needs to be updated in the database.
     */
    void update(T entity);

    /**
     * The method deletes the information about the entity from the database by id.
     *
     * @param id - entity's id.
     */
    void delete(Integer id);

    /**
     * The method deletes the information about the exhibition from the database.
     *
     * @param entity - the entity that needs to be deleted from the database.
     */
    void deleteEntity(T entity);

    /**
     * The method closes the connection to the database.
     */
    void close();
}
