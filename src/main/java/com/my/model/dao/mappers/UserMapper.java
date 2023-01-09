package com.my.model.dao.mappers;

import com.my.model.dao.constatns.fields.UserFields;
import com.my.model.entities.User;
import com.my.model.entities.enums.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements Mapper<User> {
    @Override
    public User extractFromResultSet(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt(UserFields.ID))
                .email(resultSet.getString(UserFields.EMAIL))
                .name(resultSet.getString(UserFields.USERNAME))
                .phoneNumber(resultSet.getString(UserFields.PHONE_NUMBER))
                .role(Role.valueOf(resultSet.getString(UserFields.ROLE)))
                .password(resultSet.getString(UserFields.PASSWORD))
                .isBlocked(resultSet.getBoolean(UserFields.IS_BLOCKED))
                .build();
    }
}
