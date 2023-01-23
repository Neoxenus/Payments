package com.my.model.dao.constatns.queries;

public final class PaymentQueries {
    private PaymentQueries(){}
    public static final String INSERT =
            "INSERT INTO payment" +
            " (amount, assignment, time, status, sender_account_id, receiver_account_id) " +
                    "VALUES(?,?,?, ?::payment_status_type,?,?)";

    public static final String UPDATE = "UPDATE payment SET amount=?, assignment=?, time=?, " +
            "status=?::payment_status_type, " +
            "sender_account_id=?, receiver_account_id=? WHERE id=?";

    public static final String DELETE = "DELETE FROM payment WHERE id=?";

    public static final String FIND_BY_ID = "SELECT * FROM payment WHERE id = ?";
    public static final String FIND_BY_ACCOUNT_ID =
            "SELECT * FROM payment WHERE sender_account_id = ? OR receiver_account_id = ?";
}
