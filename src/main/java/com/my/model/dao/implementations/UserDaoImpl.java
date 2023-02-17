package com.my.model.dao.implementations;

import com.my.model.dao.ConnectionPool;
import com.my.model.dao.UserDao;
import com.my.model.dao.constatns.queries.AccountQueries;
import com.my.model.dao.constatns.queries.UserQueries;
import com.my.model.dao.exceptions.DBException;
import com.my.model.dao.mappers.UserMapper;
import com.my.model.entities.Account;
import com.my.model.entities.User;
import com.my.model.services.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            statement.setString(6, entity.getIsBlocked().name());
            statement.executeUpdate();
            logger.info("The user has been successfully inserted to the db");
        }catch (SQLException e){
            logger.error("An error occurred while inserting a user into the db ", e);
            throw new DBException("An error occurred while inserting a user into the db ", e);
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
            logger.error("An error occurred while getting a user from the db ", e);
            throw new DBException("An error occurred while getting a user from the db ",e);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> result = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(UserQueries.FIND_ALL)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(userMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("Failed to find users " + e);
            throw new DBException("Failed to find users ", e);
        }
        return result;
    }

    @Override
    public void update(User entity) {
        if (entity == null){
            return;
        }
        try{
            PreparedStatement statement = connection.prepareStatement(UserQueries.UPDATE);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPhoneNumber());
            statement.setString(4, entity.getRole().name());
            statement.setString(5, entity.getPassword());
            statement.setString(6, entity.getIsBlocked().name());
            statement.setInt(7, entity.getId());
            statement.executeUpdate();
            logger.info("The user has been successfully updated");
        }catch (SQLException e){
            logger.error("An error occurred while updating a user ", e);
            throw new DBException("An error occurred while updating a user ", e);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?");
            statement.setInt(1, Math.toIntExact(id));
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("An error occurred while deleting a user from the db ", e);
            throw new DBException("An error occurred while deleting a user from the db ", e);
        }
    }

    @Override
    public void deleteEntity(User entity) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE phone_number=?");
            statement.setString(1, entity.getPhoneNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("An error occurred while deleting a user from the db ", e);
            throw new DBException("An error occurred while deleting a user from the db ", e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Failed to close connection in UserDaoImpl ");
            throw new DBException("Failed to close connection ", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        try (PreparedStatement ps = connection.prepareStatement(UserQueries.FIND_USER_BY_EMAIL)) {
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                logger.info("Found a user with the following email");
                return userMapper.extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.error("Cannot find user." + e);
            throw new DBException("Cannot find user ", e);
        }
        logger.info("No user with such email ");
        return null;
    }

    @Override
    public int getNumber() {
        int numberOfUser = 0;

        try(PreparedStatement ps = connection.prepareStatement(UserQueries.GET_NUMBER)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                numberOfUser = rs.getInt(UserQueries.FIELD_NUMBER);
            }
        } catch (SQLException e) {
            logger.error("Cannot get number of users " + e);
            throw new DBException("Cannot get number of users ", e);
        }
        return numberOfUser;
    }

    @Override
    public List<User> getUsersOnPage(int page) {
        List<User> result = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(UserQueries.FIND_ON_PAGE)) {
            ps.setInt(1, (page - 1) * UserService.PAGINATION_USERS_SIZE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(userMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("Failed to find users " + e);
            throw new DBException("Failed to find users ", e);
        }
        return result;
    }
}
