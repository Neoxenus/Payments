package com.my.model.dao.constatns.queries;


public final class UserQueries {
    private UserQueries(){}
    public static final String INSERT =
            "INSERT INTO users (name, email, phone_number, role,  password, is_blocked) VALUES(?, ?, ?, ?::role_type, ?, ?)";
    public static final String FIND_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
    public static final String FIND_BY_ID = "SELECT * FROM users WHERE id = ?";

}
