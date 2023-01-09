package com.my.model.dao.implementations;

import com.my.model.dao.ConnectionPool;
import com.my.model.dao.UserDao;
import com.my.model.dao.constatns.queries.UserQueries;
import com.my.model.dao.exceptions.DBException;
import com.my.model.dao.mappers.UserMapper;
import com.my.model.entities.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private final UserMapper userMapper;

    private final Connection connection;
    public UserDaoImpl(){
        connection = ConnectionPool.getConnection();
        userMapper = new UserMapper();
    }
    @Override
    public void add(User entity) {
        if (entity == null){
            return;
        }
        try{
            PreparedStatement statement = connection.prepareStatement(UserQueries.INSERT);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPhoneNumber());
            statement.setString(4, entity.getRole().name());
            statement.setString(5, entity.getPassword());
            statement.setBoolean(6, entity.isBlocked());
            statement.executeUpdate();
            logger.info("The user has been successfully inserted to the db");
        }catch (SQLException e){
            logger.error("An error occurred while inserting a user into the db", e);
            throw new DBException("An error occurred while inserting a user into the db", e);
        }
    }

    @Override
    public User findById(Integer id) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(UserQueries.FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return userMapper.extractFromResultSet(resultSet);
            }
            return null;
        }catch (SQLException e){
            logger.error("An error occurred while getting a user from the db", e);
            throw new DBException("An error occurred while getting a user from the db",e);
        }
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?");
            statement.setInt(1, Math.toIntExact(id));
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("An error occurred while deleting a user from the db", e);
            throw new DBException("An error occurred while deleting a user from the db", e);
        }
    }

    @Override
    public void deleteEntity(User entity) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE phone_number=?");
            statement.setString(1, entity.getPhoneNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("An error occurred while deleting a user from the db", e);
            throw new DBException("An error occurred while deleting a user from the db", e);
        }
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

    @Override
    public User findByEmailAndPassword(String email, String password) {
        try (PreparedStatement ps = connection.prepareStatement(UserQueries.FIND_USER_BY_EMAIL_AND_PASSWORD)) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.info("Found a user with the following email and password");
                return userMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("Cannot find user." + e);
            throw new DBException("Cannot find user", e);
        }
        logger.info("No user with such email and password");
        return null;
    }
}
