package com.my.model.services;

import com.my.model.dao.DaoFactory;
import com.my.model.dao.UserDao;
import com.my.model.dao.util.SHA512Utils;
import com.my.model.entities.User;
import com.my.model.entities.enums.Block;
import com.my.model.entities.enums.Role;
import lombok.Getter;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public class UserService {
    public static final int PAGINATION_USERS_SIZE = 3;

    @Getter
    private final UserDao userDao = DaoFactory.getInstance().createUserDao();
    public boolean addUser(String username, String phoneNumber, String email, String password){
        try {
            password = SHA512Utils.toHexString(SHA512Utils.getSHA(password));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        User user = new User(username, phoneNumber, email, password);
        User dbUser = userDao.findByEmail(email);
        if(dbUser != null)
            return false;
        userDao.add(user);
        return true;
    }
    public Optional<User> findByEmailAndPassword(String email, String password){

        try {
            password = SHA512Utils.toHexString(SHA512Utils.getSHA(password));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        User user = userDao.findByEmail(email);
        if(user != null && user.getPassword().equals(password))
            return Optional.of(user);
        return Optional.empty();
    }
//    private List<User> findAllUsers(){
//        return userDao.findAll();
//    }
    public List<User> getUsers(Integer pageNumber, String pageCommand){
        List<User> userList = userDao.findAll();
        pageNumber = getPage(pageNumber, pageCommand);
        int start = (pageNumber - 1) * PAGINATION_USERS_SIZE;
        int end = Math.min(start + PAGINATION_USERS_SIZE, userList.size());
        return userList.subList(start, end);
    }
    public Integer getPage(Integer pageNumber, String pageCommand){
        int userListSize = userDao.findAll().size();
        int maxPage = userListSize / PAGINATION_USERS_SIZE + (userListSize % PAGINATION_USERS_SIZE == 0 ? 0 : 1);
        if(pageCommand.equals("next")){
            pageNumber = Math.min((pageNumber + 1), maxPage);
        } else if(pageCommand.equals("previous")){
            pageNumber = Math.max(pageNumber - 1, 1);
        }
        return pageNumber;
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
