package com.my.model.services;

import com.my.controller.Servlet;
import com.my.model.dao.AccountDao;
import com.my.model.dao.CreditCardDao;
import com.my.model.dao.DaoFactory;
import com.my.model.dao.PaymentDao;
import com.my.model.entities.Account;
import com.my.model.entities.Payment;
import com.my.model.entities.User;
import com.my.model.entities.enums.PaymentStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentService {
    private static final int PAGINATION_SIZE = 5;
    public static final Logger logger = LogManager.getLogger(PaymentService.class);
    public static final String[] PAYMENT_SORT_TYPES = new String[]{"number", "oldToNew", "newToOld"};

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
        if(receiverAccount == null)
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
        List<Payment> paymentList = findPaymentsByUserId(userId);
        sortPaymentsByParameter(paymentList, sortType);
        pageNumber = getPage(userId, pageNumber, pageCommand);
        int start = (pageNumber - 1) * PAGINATION_SIZE;
        int end = Math.min(start + PAGINATION_SIZE, paymentList.size());
        return paymentList.subList(start, end);
    }
    public Integer getPage(int userId, Integer pageNumber, String pageCommand){
        List<Payment> paymentList = findPaymentsByUserId(userId);
        int maxPage = paymentList.size() / PAGINATION_SIZE + (paymentList.size() % 5 == 0 ? 0 : 1);
        if(pageCommand.equals("next")){
            pageNumber = Math.min((pageNumber + 1), maxPage);
        } else if(pageCommand.equals("previous")){
            pageNumber = Math.max(pageNumber - 1, 1);
        }
        return pageNumber;
    }
    private void sortPaymentsByParameter(List<Payment> accountList, String sortType){
        switch (sortType){
            case "oldToNew" -> accountList.sort(Comparator.comparing(Payment::getTime));
            case "newToOld" -> accountList.sort(Comparator.comparing(Payment::getTime).reversed());
            default -> accountList.sort(Comparator.comparing(Payment::getId));//by number
        }
        //return accountList;
    }
    private List<Payment> findPaymentsByUserId(int userId){
        List<Account> accounts = accountDao.findByUserId(userId);
        List<Payment> result = new ArrayList<>();
        accounts.forEach(e -> {
            List<Payment> paymentList = paymentDao.findByAccountId(e.getId());
            paymentList = paymentList
                    .stream()
                    .filter(p -> !(p.getStatus().equals(PaymentStatus.PREPARED) && e.getId()==p.getReceiverAccountId()))
                    .collect(Collectors.toList());
            result.addAll(paymentList);
        });
        return new ArrayList<>(new LinkedHashSet<>(result));
    }
}
