package com.my.model.entities;

import lombok.Data;

import java.util.Date;

@Data
public class Account {
    private int id;
    private String number;
    private String IBAN;
    private Date dateOfRegistration;

    private int userId;
}
