package com.my.model.entities;

import com.my.model.entities.enums.Block;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class Account {
    private int id;
    private String number;
    private String accountName;
    private String IBAN;
    private LocalDateTime dateOfRegistration;
    private double balanceAmount;

    private Block isBlocked;
    private int userId;

    public Account(String number, String accountName, String IBAN, LocalDateTime dateOfRegistration, double balanceAmount, int userId) {
        this.number = number;
        this.accountName = accountName;
        this.IBAN = IBAN;
        this.dateOfRegistration = dateOfRegistration;
        this.balanceAmount = balanceAmount;
        this.isBlocked = Block.ACTIVE;
        this.userId = userId;
    }
    public void replenish(double amount){
        balanceAmount += amount;
    }
}
