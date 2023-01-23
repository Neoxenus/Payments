package com.my.model.dao;

import com.my.model.entities.Payment;

import java.util.List;

public interface PaymentDao extends GenericDao<Payment>{
    List<Payment> findByAccountId(int accountId);
}
