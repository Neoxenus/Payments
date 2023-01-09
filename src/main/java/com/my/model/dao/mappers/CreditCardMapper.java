package com.my.model.dao.mappers;

import com.my.model.dao.constatns.fields.CreditCardFields;
import com.my.model.entities.CreditCard;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditCardMapper implements Mapper<CreditCard>{
    @Override
    public CreditCard extractFromResultSet(ResultSet resultSet) throws SQLException {
        return CreditCard.builder()
                .id(resultSet.getInt(CreditCardFields.ID))
                .number(resultSet.getString(CreditCardFields.NUMBER))
                .cvv(resultSet.getString(CreditCardFields.CVV))
                .expireDate(resultSet.getString(CreditCardFields.EXPIRE_DATE))
                .accountId(resultSet.getInt(CreditCardFields.ACCOUNT_ID))
                .build();
    }
}
