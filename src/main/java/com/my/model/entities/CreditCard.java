package com.my.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CreditCard {
    private int id;
    private String number;
    private String cvv;
    private String expireDate;
    private int accountId;

    public CreditCard(String number, String cvv, String expireDate, int accountId) {
        this.number = number;
        this.cvv = cvv;
        this.expireDate = expireDate;
        this.accountId = accountId;
    }
}
