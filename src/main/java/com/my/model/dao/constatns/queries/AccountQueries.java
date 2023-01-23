package com.my.model.dao.constatns.queries;

public final class AccountQueries {
    public static final String FIND_BY_ID = "SELECT * FROM account WHERE id = ?";
    public static final String FIND_BY_NUMBER = "SELECT * FROM account WHERE number = ?";
    public static final String FIND_ALL = "SELECT * FROM account";

    private AccountQueries(){}

    public static final String INSERT =
            "INSERT INTO account (number, account_name, iban,  date_of_registration, " +
                    "balance_amount, is_blocked, user_id) VALUES(?,?,?,?,?,?::block_type,?)";
    public static final String FIND_BY_USER_ID = "SELECT * FROM account WHERE user_id = ?";
    
    public static final String UPDATE = 
            "UPDATE account SET number=?, account_name=?, iban=?, date_of_registration=?, " +
                    "balance_amount=?, is_blocked=?::block_type, user_id=? WHERE id=?";
}
