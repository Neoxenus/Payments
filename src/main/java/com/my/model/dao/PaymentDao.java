package com.my.model.dao;

import com.my.model.entities.Payment;

import java.util.List;

/**
 * Interface PaymentDao is used to access the information about payments from the database.
 *
 * @author Volodymyr Kyryliuk
 */
public interface PaymentDao extends GenericDao<Payment>{

    /**
     * The method gets all the payments associated with account's id.
     *
     * @param accountId - account's id.
     * @return list of payments associated with account.
     */
    List<Payment> findByAccountId(int accountId);
}
