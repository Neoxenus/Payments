package com.my.model.dao.implementations;

import com.my.model.dao.ConnectionPool;
import com.my.model.dao.CreditCardDao;
import com.my.model.dao.constatns.queries.CreditCardQueries;
import com.my.model.dao.exceptions.DBException;
import com.my.model.dao.mappers.CreditCardMapper;
import com.my.model.entities.CreditCard;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;

public class CreditCardDaoImpl implements CreditCardDao {
    private static final Logger logger = LogManager.getLogger(CreditCardDaoImpl.class);
    private static final CreditCardMapper creditCardMapper = new CreditCardMapper();
    private final Connection connection;
    public CreditCardDaoImpl(){
        connection = ConnectionPool.getConnection();
    }
    @Override
    public void add(CreditCard entity) {
        if (entity == null){
            logger.warn("CreditCard is null, nothing to insert to db");
            return;
        }
        try{
            PreparedStatement statement = connection.prepareStatement(CreditCardQueries.INSERT);
            statement.setString(1, entity.getNumber());
            statement.setString(2, entity.getCvv());
            statement.setString(3, entity.getExpireDate());
            statement.setInt(4, entity.getAccountId());
            statement.executeUpdate();
            logger.info("Successfully added credit card to db with number " + entity.getNumber() + " and account id " + entity.getAccountId());
        }catch (SQLException e){
            logger.error("Failed to insert credit card to db",e);
            throw new DBException("Failed to insert credit card to db",e);
        }
    }

    @Override
    public List<CreditCard> findByAccountId(int accountId) {
        List<CreditCard> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM credit_card WHERE account_id=?")) {
            ps.setInt(1, accountId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(creditCardMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("Failed to find credit cards", e);
            throw new DBException("Failed to find credit cards", e);
        }
        return result;
    }

    @Override
    public CreditCard findById(Integer id) {
        return null;
    }

    @Override
    public List<CreditCard> findAll() {
        return null;
    }

    @Override
    public void update(CreditCard entity) {

    }

    @Override
    public void delete(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM credit_card WHERE id=?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("An error occurred while deleting a credit card from the db", e);
            throw new DBException("An error occurred while deleting a credit card from the db", e);
        }
    }

    @Override
    public void deleteEntity(CreditCard entity) {

    }

    @Override
    public void close() {

    }
}
