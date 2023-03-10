package com.my.model.services;

import com.my.model.dao.AccountDao;
import com.my.model.dao.DaoFactory;
import com.my.model.entities.Account;
import com.my.model.entities.enums.Block;
import lombok.Getter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountService {
    public static final String[] ACCOUNT_SORT_TYPES = new String[]{"number", "accountName", "amount"};
    public static final Logger logger = LogManager.getLogger(AccountService.class);
    public static final double DEFAULT_AMOUNT = 1000.0;

    @Getter
    private final AccountDao accountDao = DaoFactory.getInstance().createAccountDao();

    public boolean addAccount(String number, String accountName, String IBAN, int userId){
        Account account = new Account(number, accountName, IBAN, DEFAULT_AMOUNT,
                userId);
        Account dbAccount = accountDao.findByNumber(number);
        if(dbAccount == null){
            accountDao.add(account);
            return true;
        }
        return false;
    }
    public List<Account> findByUserId(int userId){
        return accountDao.findByUserId(userId);
    }
    public List<Account> findByUserIdNotBlocked(int userId){
        return accountDao.findByUserId(userId).stream().filter(e -> e.getIsBlocked().equals(Block.ACTIVE)).collect(Collectors.toList());
    }
    public void sortAccountsByParameter(List<Account> accountList, String sortType){
        switch (sortType){
            case "accountName" -> accountList.sort(Comparator.comparing(Account::getAccountName));
            case "amount" -> accountList.sort(Comparator.comparing(Account::getBalanceAmount));
            default -> accountList.sort(Comparator.comparing(Account::getNumber));//by number
        }
        //return accountList;
    }

    public Map<Integer, Account> findAll(){
        List<Account> accountList = accountDao.findAll();
        return accountList.stream().collect(Collectors.toMap(Account::getId, e->e ));
    }

    public void block(int accountId){
        Account account = accountDao.findById(accountId);
        if(account == null){
            logger.warn("Failed to get account with id " + accountId +" from db");
            return;
        }
        if(account.getIsBlocked().equals(Block.ACTIVE))
            account.setIsBlocked(Block.BLOCKED);
        else if(account.getIsBlocked().equals(Block.BLOCKED))
            account.setIsBlocked(Block.APPROVAL);
        else if(account.getIsBlocked().equals(Block.APPROVAL))
            return;
        accountDao.update(account);
    }
    public Account blockAdmin(int accountId){
        Account account = accountDao.findById(accountId);
        if(account == null){
            logger.warn("Failed to get account with id " + accountId +" from db");
            return null;
        }
        if(account.getIsBlocked().equals(Block.ACTIVE))
            account.setIsBlocked(Block.BLOCKED);
        else if(account.getIsBlocked().equals(Block.BLOCKED))
            account.setIsBlocked(Block.ACTIVE);
        else if(account.getIsBlocked().equals(Block.APPROVAL))
            account.setIsBlocked(Block.ACTIVE);
        accountDao.update(account);
        return account;
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
