package com.my.model.dao.constatns.queries;

public final class CreditCardQueries {
    private CreditCardQueries(){}
    public static final String INSERT = "INSERT INTO credit_card (number, cvv, expire_date, account_id) VALUES(?,?,?,?)";
    public static final String DELETE = "DELETE FROM credit_card WHERE id=?";
}
