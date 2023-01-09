package com.my.model.dao;

import com.my.model.entities.CreditCard;

import java.util.List;
import java.util.Map;

public interface CreditCardDao extends GenericDao<CreditCard> {
    List<CreditCard> findByAccountId(int accountId);

}
