package com.my.model.services;

import com.my.model.dao.DaoFactory;
import com.my.model.dao.UserDao;
import com.my.model.entities.User;
import com.my.model.entities.enums.Block;
import com.my.model.entities.enums.Role;

import java.util.List;
import java.util.Optional;

public class UserService {
    private final UserDao userDao = DaoFactory.getInstance().createUserDao();
    public boolean addUser(String username, String phoneNumber, String email, String password){
        User user = new User(username, phoneNumber, email, password);
        User dbUser = userDao.findByEmail(email);
        if(dbUser != null)
            return false;
        userDao.add(user);
        return true;
    }
    public Optional<User> findByEmailAndPassword(String email, String password){
        User user = userDao.findByEmail(email);
        if(user != null && user.getPassword().equals(password))
            return Optional.of(user);
        return Optional.empty();
    }
    public List<User> findAllUsers(){
        return userDao.findAll();
    }
    public void setBlocked(int userId){
        User user = userDao.findById(userId);
        user.setIsBlocked(
                        user.getIsBlocked().equals(Block.ACTIVE)
                        ? Block.BLOCKED
                        : Block.ACTIVE
                        );
        userDao.update(user);
    }
    public Optional<User> findById(int userId){
        return Optional.ofNullable(userDao.findById(userId));
    }

    public void promoteUser(int userId) {
        User user = userDao.findById(userId);
        user.setRole(Role.ADMIN);
        userDao.update(user);
    }
}
