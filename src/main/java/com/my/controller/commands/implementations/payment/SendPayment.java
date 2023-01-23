package com.my.controller.commands.implementations.payment;

import com.my.controller.Servlet;
import com.my.controller.commands.Command;
import com.my.model.entities.User;
import com.my.model.services.AccountService;
import com.my.model.services.CreditCardService;
import com.my.model.services.PaymentService;

import javax.servlet.http.HttpServletRequest;

public class SendPayment implements Command {
    private AccountService accountService;
    private PaymentService paymentService;

    public SendPayment(AccountService accountService, PaymentService paymentService) {
        this.accountService = accountService;
        this.paymentService = paymentService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute("user");
        if(currentUser == null){
            Servlet.logger.error("User is null in command /CancelPayments/");
            return "redirect:/";
        }
        int paymentId = Integer.parseInt(request.getParameter("paymentId"));

        paymentService.sendPayment(paymentId);

        return "redirect:/?command=getPayments";
    }
}
