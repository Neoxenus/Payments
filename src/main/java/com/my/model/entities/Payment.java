package com.my.model.entities;

import com.my.model.entities.enums.PaymentStatus;
import lombok.Data;

import java.util.Date;

@Data
public class Payment {
    private long number;

    private double amount;

    private String assignment;
    private Date time;
    private PaymentStatus status;

    private int senderAccountId;
    private int receiverAccountId;
}
