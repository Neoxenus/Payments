package com.my.model.dao;

import com.my.model.entities.User;

import java.util.List;

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
     * @return user with such email.
     */
    User findByEmail(String email);
    int getNumber();
    List<User> getUsersOnPage(int page);
}
