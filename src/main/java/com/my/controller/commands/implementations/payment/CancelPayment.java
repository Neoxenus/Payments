package com.my.controller.commands.implementations.payment;

import com.my.controller.Servlet;
import com.my.controller.commands.Command;
import com.my.model.entities.Payment;
import com.my.model.entities.User;
import com.my.model.services.PaymentService;

import javax.servlet.http.HttpServletRequest;

public class CancelPayment implements Command {
    private PaymentService paymentService;
    public CancelPayment(PaymentService paymentService){
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

        paymentService.cancelPayment(paymentId);

        return "redirect:/?command=getPayments";
    }
}
