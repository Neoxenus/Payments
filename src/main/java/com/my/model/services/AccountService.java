package com.my.model.services;

import com.my.model.dao.AccountDao;
import com.my.model.dao.DaoFactory;
import com.my.model.entities.Account;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class AccountService {
    public static final Logger logger = LogManager.getLogger(AccountService.class);

    private final AccountDao accountDao = DaoFactory.getInstance().createAccountDao();

    public void addAccount(String number, String accountName, String IBAN, int userId){
        Account account = new Account(number, accountName, IBAN, LocalDateTime.now(), 1000.0,
                userId);
        accountDao.add(account);
    }
    public List<Account> findByUserId(int userId){
        return accountDao.findByUserId(userId);
    }

    public void setBlocked(int accountId, boolean isBlocked){
        Account account = accountDao.findById(accountId);
        if(account == null){
            logger.warn("Failed to get account with id " + accountId +" from db");
            return;
        }
        account.setIsBlocked(isBlocked);
        accountDao.update(account);
    }
    public void replenishAccount(int accountId, double amount){
        Account account = accountDao.findById(accountId);
        if(account == null){
            logger.warn("Failed to get account with id " + accountId +" from db");
            return;
        }
        account.replenish(amount);
        accountDao.update(account);
    }
}
