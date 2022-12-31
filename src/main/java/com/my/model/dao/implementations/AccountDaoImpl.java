package com.my.model.dao.implementations;

import com.my.controller.Servlet;
import com.my.model.dao.AccountDao;
import com.my.model.dao.ConnectionPool;
import com.my.model.dao.exceptions.DBException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AccountDaoImpl implements AccountDao {
    private static final Logger logger = LogManager.getLogger(AccountDaoImpl.class);

    private final Connection connection;
    public AccountDaoImpl(){
        connection = ConnectionPool.getConnection();
    }
    @Override
    public void add(AccountDao entity) {
        if (entity == null){
            logger.warn("AccountDao is null, nothing to insert to db");
            return;
        }
//        try{
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO account (name) VALUES(?)");
//            //statement.setString(1, entity.getName());
//            statement.executeUpdate();
//            //department.setId(getDepartment(department.getName()).getId());
//            return;
//        }catch (SQLException e){
//            throw new DBException("Failed to insert account to db",e);
//        }
    }

    @Override
    public AccountDao findById(Long id) {
        return null;
    }

    @Override
    public List<AccountDao> findAll() {
        return null;
    }

    @Override
    public void update(AccountDao entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteEntity(AccountDao entity) {

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
