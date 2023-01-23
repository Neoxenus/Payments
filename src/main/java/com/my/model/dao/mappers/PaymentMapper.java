package com.my.model.dao.mappers;

import com.my.model.dao.constatns.fields.AccountFields;
import com.my.model.dao.constatns.fields.PaymentFields;
import com.my.model.entities.Payment;
import com.my.model.entities.enums.PaymentStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentMapper implements Mapper<Payment> {
    @Override
    public Payment extractFromResultSet(ResultSet resultSet) throws SQLException {
        return Payment.builder()
                .id(resultSet.getInt(PaymentFields.ID))
                .amount(resultSet.getDouble(PaymentFields.AMOUNT))
                .assignment(resultSet.getString(PaymentFields.ASSIGNMENT))
                .time(resultSet.getTimestamp(PaymentFields.TIME).toLocalDateTime())
                .status(PaymentStatus.valueOf(resultSet.getString(PaymentFields.STATUS)))
                .senderAccountId(resultSet.getInt(PaymentFields.SENDER_ACCOUNT_ID))
                .receiverAccountId(resultSet.getInt(PaymentFields.RECEIVER_ACCOUNT_ID))
                .build();
    }
}
