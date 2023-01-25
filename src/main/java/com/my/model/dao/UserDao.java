package com.my.model.dao;

import com.my.model.entities.User;

/**
 * Interface UserDao is used to access the information about users from the database.
 *
 * @author Volodymyr Kyryliuk
 */
public interface UserDao extends GenericDao<User> {

    /**
     * The method gets information about the user by its email.
     *
     * @param email - user's number.
     */
    User findByEmail(String email);

}
