package com.my.model.dao.constatns.queries;

public final class AccountQueries {
    public static final String FIND_BY_ID = "SELECT * FROM account WHERE id = ?";

    private AccountQueries(){}

    public static final String INSERT = 
            "INSERT INTO account (number, account_name, iban,  date_of_registration, " +
                    "balance_amount, is_blocked, user_id) VALUES(?,?,?,?,?,?,?)";
    public static final String FIND_BY_USER_ID = "SELECT * FROM account WHERE user_id = ?";
    
    public static final String UPDATE = 
            "UPDATE account SET number=?, account_name=?, iban=?, date_of_registration=?, " +
                    "balance_amount=?, is_blocked=?, user_id=? WHERE id=?";
}
