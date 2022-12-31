package com.my.model.dao;

public abstract class DaoFactory {
    private static volatile DaoFactory daoFactory;

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class){
                if (daoFactory == null) {
                    //daoFactory =  new DaoFactoryImpl();
                }
            }
        }
        return daoFactory;
    }

    public abstract AccountDao createAccountDao();
    public abstract UserDao createUserDao();
    public abstract CreditCardDao createCreditCardDao();
    public abstract PaymentDao createPaymentDao();
}
