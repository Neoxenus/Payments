package com.my.model.dao.implementations;

import com.my.model.dao.ConnectionPool;
import com.my.model.dao.UserDao;
import com.my.model.dao.exceptions.DBException;
import com.my.model.entities.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private Connection connection;
    public UserDaoImpl(){
        connection = ConnectionPool.getConnection();
    }
    @Override
    public void add(User entity) {
        if (entity == null){
            return;
        }

        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, phonenumber, role, email, password) VALUES(?,?,?,?,?)");
            int i = 1;
            statement.setString(i++, entity.getUsername());
            statement.setString(i++, entity.getPhoneNumber());
            statement.setString(i++, entity.getRole().name());
            statement.setString(i++, entity.getEmail());
            statement.setString(i, entity.getPassword());
            statement.executeUpdate();
            logger.info("The user has been successfully inserted to the db");
        }catch (SQLException e){
            logger.error("An error occurred while inserting a user into the db", e);
            throw new DBException("An error occurred while inserting a user into the db", e);
        }
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteEntity(User entity) {

    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Failed to close connection in UserDaoImpl");
            throw new DBException("Failed to close connection", e);
        }
    }
}
