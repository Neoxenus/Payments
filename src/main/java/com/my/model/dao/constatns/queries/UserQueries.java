package com.my.model.dao.constatns.queries;


import com.my.model.services.UserService;

public final class UserQueries {
    public static final String FIND_ALL = "SELECT * FROM users";
    public static final String FIND_ON_PAGE =
            "SELECT * FROM users LIMIT " + UserService.PAGINATION_USERS_SIZE + " OFFSET ?";
    public static final String UPDATE = "UPDATE users " +
            "SET name=?, email=?, phone_number=?, role=?::role_type, password=?, is_blocked=?::block_type " +
            "WHERE id=?";

    private UserQueries(){}
    public static final String INSERT =
            "INSERT INTO users (name, email, phone_number, role,  password, is_blocked) " +
                    "VALUES(?, ?, ?, ?::role_type, ?, ?::block_type)";
    public static final String FIND_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    public static final String FIND_BY_ID = "SELECT * FROM users WHERE id = ?";

    public static final String GET_NUMBER =
            "SELECT COUNT(*) as number FROM users ";
    public static final String FIELD_NUMBER = "number";

}
