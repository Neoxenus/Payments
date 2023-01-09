package com.my.model.dao;

import com.my.model.entities.User;

public interface UserDao extends GenericDao<User> {
    User findByEmailAndPassword(String email, String password);

}
