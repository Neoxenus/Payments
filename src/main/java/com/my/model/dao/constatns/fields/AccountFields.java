package com.my.model.dao.constatns.fields;

public final class AccountFields {
    private AccountFields(){}

    public static final String ID = "id";
    public static final String NUMBER = "number";
    public static final String ACCOUNT_NAME = "account_name";
    public static final String IBAN = "IBAN";
    public static final String DATE_OF_REGISTRATION = "date_of_registration";
    public static final String BALANCE_AMOUNT = "balance_amount";
    public static final String IS_BLOCKED = "is_blocked";

    public static final String USER_ID = "user_id";
    private static final String regex = "([\\d]{4}-((0?[1-9])|(1[0-2])))";

}
