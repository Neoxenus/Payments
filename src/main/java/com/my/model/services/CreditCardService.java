package com.my.model.services;

import com.my.model.dao.AccountDao;
import com.my.model.dao.CreditCardDao;
import com.my.model.dao.DaoFactory;
import com.my.model.entities.Account;
import com.my.model.entities.CreditCard;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreditCardService {
    private final CreditCardDao creditCardDao = DaoFactory.getInstance().createCreditCardDao();
    private final AccountDao accountDao = DaoFactory.getInstance().createAccountDao();

    public void addCreditCard(CreditCard creditCard){
        creditCardDao.add(creditCard);
    }
    public Map<Integer, List<CreditCard>> findByUserId(int userId){
        List<Account> accounts = accountDao.findByUserId(userId);
        return accounts.stream().collect(Collectors.toMap(Account::getId,
                e -> creditCardDao.findByAccountId(e.getId()), (a, b) -> b));
    }
    public void deleteCreditCard(int creditCardId){
        creditCardDao.delete(creditCardId);
    }
}
