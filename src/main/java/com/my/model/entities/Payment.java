package com.my.model.entities;

import com.my.model.entities.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class Payment {
    private int id;
    //private String number;


    private double amount;

    private String assignment;
    private LocalDateTime time;
    private PaymentStatus status;

    private int senderAccountId;
    private int receiverAccountId;
    public Payment(double amount, String assignment,  int senderAccountId, int receiverAccountId) {
        this.amount = amount;
        this.assignment = assignment;
        this.time = LocalDateTime.now();
        this.status = PaymentStatus.PREPARED;
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
    }
}
