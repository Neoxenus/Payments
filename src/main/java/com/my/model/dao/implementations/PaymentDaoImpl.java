package com.my.model.dao.implementations;

import com.my.model.dao.ConnectionPool;
import com.my.model.dao.PaymentDao;
import com.my.model.dao.constatns.queries.AccountQueries;
import com.my.model.dao.constatns.queries.PaymentQueries;
import com.my.model.dao.constatns.queries.UserQueries;
import com.my.model.dao.exceptions.DBException;
import com.my.model.dao.mappers.PaymentMapper;
import com.my.model.dao.mappers.UserMapper;
import com.my.model.entities.Account;
import com.my.model.entities.Payment;
import com.my.model.entities.User;
import com.my.model.entities.enums.PaymentStatus;
import com.my.model.services.PaymentService;
import com.my.model.services.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDaoImpl implements PaymentDao {
    private static final Logger logger = LogManager.getLogger(PaymentDaoImpl.class);
    private final PaymentMapper paymentMapper;

    private final Connection connection;
    public PaymentDaoImpl(){
        connection = ConnectionPool.getConnection();
        paymentMapper = new PaymentMapper();
    }
    @Override
    public List<Payment> findByAccountId(int accountId) {
        List<Payment> result = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(PaymentQueries.FIND_BY_ACCOUNT_ID)) {
            ps.setInt(1, accountId);
            ps.setInt(2, accountId);
            //logger.info("before execute query, account id: " + accountId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(paymentMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("Failed to find payments" + e);
            throw new DBException("Failed to find payments", e);
        }
        return result;
    }

    @Override
    public int getNumberByUserId(int userId) {
        int numberOfPayments = 0;

        try(PreparedStatement ps = connection.prepareStatement(PaymentQueries.GET_NUMBER_BY_USER_ID)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                numberOfPayments = rs.getInt(PaymentQueries.FIELD_NUMBER);
            }
        } catch (SQLException e) {
            logger.error("Cannot get number of payments " + e);
            throw new DBException("Cannot get number of payments ", e);
        }
        return numberOfPayments;
    }

    @Override
    public List<Payment> findByUserId(int userId, int page, String sortType) {
        String query = PaymentQueries.FIND_BY_USER_ID +
        switch (sortType){
            case "oldToNew" -> PaymentQueries.BY_DATE_ASC;
            case "newToOld" -> PaymentQueries.BY_DATE_DESC;
            default -> PaymentQueries.BY_NUMBER;
        }
        + PaymentQueries.LIMIT;

        List<Payment> result = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, (page - 1) * PaymentService.PAGINATION_PAYMENTS_SIZE);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                result.add(paymentMapper.extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.error("Failed to find payments" + e);
            throw new DBException("Failed to find payments", e);
        }
        return result;
    }

    @Override
    public void add(Payment entity) {
        if (entity == null){
            return;
        }
        try{
            PreparedStatement statement = connection.prepareStatement(PaymentQueries.INSERT);
            statement.setDouble(1, entity.getAmount());
            statement.setString(2, entity.getAssignment());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getTime()));
            statement.setString(4, entity.getStatus().name());
            statement.setInt(5, entity.getSenderAccountId());
            statement.setInt(6, entity.getReceiverAccountId());
            statement.executeUpdate();
            logger.info("The payment has been successfully inserted to the db");
        }catch (SQLException e){
            logger.error("An error occurred while inserting a payment into the db", e);
            throw new DBException("An error occurred while inserting a payment into the db", e);
        }
    }


    @Override
    public Payment findById(Integer id) {

        try (PreparedStatement ps = connection.prepareStatement(PaymentQueries.FIND_BY_ID)) {
            ps.setInt(1, id);
            //logger.info("before execute query, account id: " + accountId);

            ResultSet rs = ps.executeQuery();

            Payment payment = null;
            if (rs.next()) {
                 payment = paymentMapper.extractFromResultSet(rs);
            }
            return payment;
        } catch (SQLException e) {
            logger.error("Failed to find payments" + e);
            throw new DBException("Failed to find payments", e);
        }
    }

    @Override
    public List<Payment> findAll() {
        return null;
    }

    @Override
    public void update(Payment entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(PaymentQueries.UPDATE);
            statement.setDouble(1, entity.getAmount());
            statement.setString(2, entity.getAssignment());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getTime()));
            statement.setString(4, entity.getStatus().name());
            statement.setInt(5, entity.getSenderAccountId());
            statement.setInt(6, entity.getReceiverAccountId());
            statement.setInt(7, entity.getId());
            statement.executeUpdate();
            logger.info("Successfully updated payment " + entity);

        } catch (SQLException e) {
            logger.error("Failed to update payment" + e);
            throw new DBException("Failed to update payment", e);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            PreparedStatement statement = connection.prepareStatement(PaymentQueries.DELETE);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("An error occurred while deleting a payment from the db", e);
            throw new DBException("An error occurred while deleting a payment from the db", e);
        }
    }

    @Override
    public void deleteEntity(Payment entity) {

    }

    @Override
    public void close() {

    }


}
