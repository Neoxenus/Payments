package com.my.model.services;

import com.my.model.dao.DaoFactory;
import com.my.model.dao.UserDao;
import com.my.model.entities.User;

import java.util.Optional;

public class UserService {
    private final UserDao userDao = DaoFactory.getInstance().createUserDao();
    public void addUser(User user){
        userDao.add(user);
    }
    public Optional<User> findByEmailAndPassword(String email, String password){
        return Optional.ofNullable(userDao.findByEmailAndPassword(email, password));
    }
}
