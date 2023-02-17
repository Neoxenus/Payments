package com.my.model.services;

import com.my.model.dao.AccountDao;
import com.my.model.dao.CreditCardDao;
import com.my.model.dao.DaoFactory;
import com.my.model.dao.PaymentDao;
import com.my.model.entities.Account;
import com.my.model.entities.Payment;
import com.my.model.entities.User;
import com.my.model.entities.enums.PaymentStatus;
import lombok.Getter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentService {
    public static final int PAGINATION_PAYMENTS_SIZE = 5;
    public static final Logger logger = LogManager.getLogger(PaymentService.class);
    public static final String[] PAYMENT_SORT_TYPES = new String[]{"number", "oldToNew", "newToOld"};

    @Getter
    private final PaymentDao paymentDao = DaoFactory.getInstance().createPaymentDao();
    private final AccountDao accountDao = DaoFactory.getInstance().createAccountDao();
    private final CreditCardDao creditCardDao = DaoFactory.getInstance().createCreditCardDao();


    public String addPayment(double amount, String assignment, String senderAccountNumber, String receiverAccountNumber, User currentUser){
        Account senderAccount = accountDao.findByNumber(senderAccountNumber);
        if(senderAccount == null)
            return "badSender";
        if(senderAccount.getUserId() != currentUser.getId())
            return "badSender";
        Account receiverAccount = accountDao.findByNumber(receiverAccountNumber);
        if(receiverAccount == null || senderAccount.equals(receiverAccount))
            return "badReceiver";
        Payment payment = new Payment(amount, assignment, senderAccount.getId(), receiverAccount.getId());
        paymentDao.add(payment);
        return null;
    }

    public void cancelPayment(int paymentId){
        paymentDao.delete(paymentId);
    }
    public void sendPayment(int paymentId){
        Payment payment = paymentDao.findById(paymentId);
        Account sender = accountDao.findById(payment.getSenderAccountId());
        Account receiver = accountDao.findById(payment.getReceiverAccountId());
        sender.setBalanceAmount(sender.getBalanceAmount() - payment.getAmount());
        receiver.setBalanceAmount(receiver.getBalanceAmount() + payment.getAmount());
        payment.setStatus(PaymentStatus.SENT);
        payment.setTime(LocalDateTime.now());
        accountDao.update(sender);
        accountDao.update(receiver);
        paymentDao.update(payment);

    }
    public List<Payment> getPayments(int userId, Integer pageNumber, String pageCommand, String sortType){
        pageNumber = getPage(userId, pageNumber, pageCommand);

        return paymentDao.findByUserId(userId, pageNumber, sortType);
    }
    public Integer getPage(int userId, Integer pageNumber, String pageCommand){
        int paymentListSize = paymentDao.getNumberByUserId(userId);
        int maxPage = paymentListSize / PAGINATION_PAYMENTS_SIZE + (paymentListSize % PAGINATION_PAYMENTS_SIZE == 0 ? 0 : 1);
        if(maxPage == 0)
            maxPage = 1;
        if(pageCommand.equals("next")){
            pageNumber = Math.min((pageNumber + 1), maxPage);
        } else if(pageCommand.equals("previous")){
            pageNumber = Math.max(pageNumber - 1, 1);
        }
        return pageNumber;
    }

}
