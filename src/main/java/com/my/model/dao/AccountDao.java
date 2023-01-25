package com.my.model.dao;

import com.my.model.entities.Account;

import java.util.List;

/**
 * Interface AccountDao is used to access the information about accounts from the database.
 *
 * @author Volodymyr Kyryliuk
 */
public interface AccountDao extends GenericDao<Account>{

    /**
     * The method gets all the accounts by user's id.
     *
     * @param userId - user's id.
     */
    List<Account> findByUserId(int userId);

    /**
     * The method gets information about the account by its number.
     *
     * @param number - account's number.
     */
    Account findByNumber(String number);
}
