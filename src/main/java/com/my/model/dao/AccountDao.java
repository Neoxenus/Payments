package com.my.model.dao;

import com.my.model.entities.Account;

import java.util.List;

public interface AccountDao extends GenericDao<Account>{
    List<Account> findByUserId(int userId);
    Account findByNumber(String number);
}
