package com.my.model.dao.implementations;

import com.my.model.dao.*;
import com.my.model.dao.CreditCardDao;
import com.my.model.dao.PaymentDao;

public class DaoFactoryImpl extends DaoFactory {

    @Override
    public AccountDao createAccountDao() {
        return new AccountDaoImpl();
    }

    @Override
    public UserDao createUserDao() {
        return new UserDaoImpl();
    }

    @Override
    public CreditCardDao createCreditCardDao() {
        return new CreditCardDaoImpl();
    }

    @Override
    public PaymentDao createPaymentDao() {
        return new PaymentDaoImpl();
    }
}
