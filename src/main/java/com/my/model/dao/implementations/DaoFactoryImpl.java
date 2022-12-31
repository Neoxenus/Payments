package com.my.model.dao.implementations;

import com.my.model.dao.*;
import com.my.model.dao.CreditCardDao;
import com.my.model.dao.PaymentDao;

public class DaoFactoryImpl extends DaoFactory {

    @Override
    public AccountDao createAccountDao() {
        return null;
    }

    @Override
    public UserDao createUserDao() {
        return null;
    }

    @Override
    public CreditCardDao createCreditCardDao() {
        return null;
    }

    @Override
    public PaymentDao createPaymentDao() {
        return null;
    }
}
