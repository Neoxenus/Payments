package com.my.model.dao.implementations;

import com.my.model.dao.AccountDao;
import com.my.model.dao.ConnectionPool;
import com.my.model.dao.constatns.queries.AccountQueries;
import com.my.model.dao.exceptions.DBException;
import com.my.model.dao.mappers.AccountMapper;
import com.my.model.entities.Account;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {
    private static final Logger logger = LogManager.getLogger(AccountDaoImpl.class);
    private static final AccountMapper accountMapper = new AccountMapper();
    private final Connection connection;
    public AccountDaoImpl(){
        connection = ConnectionPool.getConnection();
    }
    @Override
    public void add(Account entity) {
        if (entity == null){
            logger.warn("AccountDao is null, nothing to insert to db");
            return;
        }
        try{

            PreparedStatement statement = connection.prepareStatement(AccountQueries.INSERT);
            statement.setString(1, entity.getNumber());
            statement.setString(2, entity.getAccountName());
            statement.setString(3, entity.getIBAN());
            statement.setDate(4, new java.sql.Date(entity.getDateOfRegistration().getTime()));
            statement.setDouble(5, entity.getBalanceAmount());
            statement.setBoolean(6, entity.getIsBlocked());
            statement.setInt(7, entity.getUserId());
            statement.executeUpdate();
            logger.info("Successfully added account to db with number " + entity.getNumber() + " and user id " + entity.getUserId());
        }catch (SQLException e){
            logger.error("Failed to insert account to db",e);
            throw new DBException("Failed to insert account to db",e);
        }
    }
    @Override
    public List<Account> findByUserId(int userId) {
        List<Account> result = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(AccountQueries.FIND_BY_USER_ID)) {
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(accountMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("Failed to find accounts" + e);
            throw new DBException("Failed to find accounts", e);
        }
        return result;
    }
    @Override
    public Account findById(Integer id) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(AccountQueries.FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return accountMapper.extractFromResultSet(resultSet);
            }
            return null;
        }catch (SQLException e){
            logger.error("An error occurred while getting an account from the db", e);
            throw new DBException("An error occurred while getting an account from the db",e);
        }
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public void update(Account entity) {
        //worker = getTeam(worker.getName());
        try {
            PreparedStatement statement = connection.prepareStatement(AccountQueries.UPDATE);
            statement.setString(1, entity.getNumber());
            statement.setString(2, entity.getAccountName());
            statement.setString(3, entity.getIBAN());
            statement.setDate(4, new java.sql.Date(entity.getDateOfRegistration().getTime()));
            statement.setDouble(5, entity.getBalanceAmount());
            statement.setBoolean(6, entity.getIsBlocked());
            statement.setInt(7, entity.getUserId());
            statement.setInt(8, entity.getId());
            statement.executeUpdate();
            logger.info("Successfully updated account db with number " + entity.getNumber() + " and user id " + entity.getUserId());

        } catch (SQLException e) {
            logger.error("Failed to update account" + e);
            throw new DBException("Failed to update account", e);
        }
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void deleteEntity(Account entity) {

    }


    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DBException("Failed to close connection", e);
        }
    }


}
