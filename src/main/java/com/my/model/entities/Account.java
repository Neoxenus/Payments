package com.my.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class Account {
    private int id;
    private String number;
    private String accountName;
    private String IBAN;
    private Date dateOfRegistration;
    private double balanceAmount;

    private Boolean isBlocked;
    private int userId;

    public Account(String number, String accountName, String IBAN, Date dateOfRegistration, double balanceAmount, int userId) {
        this.number = number;
        this.accountName = accountName;
        this.IBAN = IBAN;
        this.dateOfRegistration = dateOfRegistration;
        this.balanceAmount = balanceAmount;
        this.isBlocked = false;
        this.userId = userId;
    }
}
