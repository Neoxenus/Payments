package com.my.model.dao.mappers;

import com.my.model.dao.constatns.fields.AccountFields;
import com.my.model.entities.Account;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements Mapper<Account> {
    @Override
    public Account extractFromResultSet(ResultSet resultSet) throws SQLException {
        return Account.builder()
                .id(resultSet.getInt(AccountFields.ID))
                .number(resultSet.getString(AccountFields.NUMBER))
                .accountName(resultSet.getString(AccountFields.ACCOUNT_NAME))
                .IBAN(resultSet.getString(AccountFields.IBAN))
                .dateOfRegistration(resultSet.getTimestamp(AccountFields.DATE_OF_REGISTRATION).toLocalDateTime())
                .balanceAmount(resultSet.getDouble(AccountFields.BALANCE_AMOUNT))
                .isBlocked(resultSet.getBoolean(AccountFields.IS_BLOCKED))
                .userId(resultSet.getInt(AccountFields.USER_ID))
                .build();
    }
}
