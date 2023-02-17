package com.my.model.dao.constatns.queries;

import com.my.model.entities.enums.PaymentStatus;
import com.my.model.services.PaymentService;
import com.my.model.services.UserService;

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

//                  "SELECT * " +
//                          "FROM hall h INNER JOIN exhibitions_halls eh ON h.id = eh.hall_id " +
//                          "INNER JOIN exhibition e on eh.exhibition_id = e.id " +
//                          "WHERE e.id = ?";
    public static final String GET_NUMBER_BY_USER_ID =
        "SELECT COUNT(*) as number " +
                "FROM payment p "+
                "INNER JOIN account a on a.id = p.sender_account_id " +
                "or a.id = p.receiver_account_id and p.status = 'SENT' "+
                "INNER JOIN users u on u.id = a.user_id"
                +" WHERE u.id = ? ";
    public static final String FIND_BY_USER_ID = "SELECT * " +
        "FROM payment p "+
        "INNER JOIN account a on a.id = p.sender_account_id " +
        "or a.id = p.receiver_account_id and p.status = 'SENT' "+
        "INNER JOIN users u on u.id = a.user_id"
        +" WHERE u.id = ? ";
    public static final String BY_NUMBER = " ORDER BY p.id ";
    public static final String BY_DATE_ASC = " ORDER BY p.time asc ";
    public static final String BY_DATE_DESC = " ORDER BY p.time desc ";
    public static final String LIMIT = "LIMIT " + PaymentService.PAGINATION_PAYMENTS_SIZE + " OFFSET ?";
    public static final String FIND_BY_ACCOUNT_ID =
            "SELECT * FROM payment WHERE sender_account_id = ? OR receiver_account_id = ?";
    //"SELECT * FROM payment WHERE sender_account_id = ? OR (receiver_account_id = ? AND status = 'SENT'::payment_status_type)";
    public static final String GET_NUMBER =
            "SELECT COUNT(*) as number FROM payment ";
    public static final String FIELD_NUMBER = "number";
}
