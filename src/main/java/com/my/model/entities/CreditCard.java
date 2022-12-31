package com.my.model.entities;

import lombok.Data;

@Data
public class CreditCard {
    private int id;
    private long number;
    private int cvv;
    private String expireDate;
    private int accountId;
}
