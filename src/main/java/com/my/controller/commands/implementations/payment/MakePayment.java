package com.my.controller.commands.implementations.payment;

import com.my.controller.Servlet;
import com.my.controller.commands.Command;
import com.my.model.entities.Payment;
import com.my.model.entities.User;
import com.my.model.services.AccountService;
import com.my.model.services.CreditCardService;
import com.my.model.services.PaymentService;

import javax.servlet.http.HttpServletRequest;

public class MakePayment implements Command {
    private AccountService accountService;
    private CreditCardService creditCardService;
    private PaymentService paymentService;

    public MakePayment(AccountService accountService, CreditCardService creditCardService, PaymentService paymentService){
        this.accountService = accountService;
        this.creditCardService = creditCardService;
        this.paymentService = paymentService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        String senderNumber = request.getParameter("sender");
        String receiverNumber = request.getParameter("receiver");
        double amount = Double.parseDouble(request.getParameter("amount"));
        String assignment = request.getParameter("assignment");
        Servlet.logger.info("Get params: sender: " + senderNumber + " and receiver: " + receiverNumber);

        User currentUser = (User) request.getSession().getAttribute("user");
        if(currentUser == null){
            Servlet.logger.error("User is null in command /ConfirmPayments/");
            return "redirect:/";
        }
        String rsp = paymentService.addPayment(amount, assignment, senderNumber, receiverNumber, currentUser);
        if (rsp == null) {
            return "redirect:/?command=getPayments";
        } else {
            request.getSession().setAttribute("error", rsp);
            return "redirect:/?command=prepareMakePayment";
        }

    }
}
