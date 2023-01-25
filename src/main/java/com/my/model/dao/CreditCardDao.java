package com.my.model.dao;

import com.my.model.entities.CreditCard;

import java.util.List;
import java.util.Map;

/**
 * Interface CreditCardDao is used to access the information about credit cards from the database.
 *
 * @author Volodymyr Kyryliuk
 */
public interface CreditCardDao extends GenericDao<CreditCard> {

    /**
     * The method gets all the accounts by account's id.
     *
     * @param accountId - account's id.
     */
    List<CreditCard> findByAccountId(int accountId);

    /**
     * The method gets information about the CreditCard by its number.
     *
     * @param number - credit card's number.
     */
    CreditCard findByNumber(String number);
}
