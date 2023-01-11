package com.my.controller.commands.implementations;

import com.my.controller.Servlet;
import com.my.controller.commands.Command;
import com.my.model.entities.Account;
import com.my.model.entities.CreditCard;
import com.my.model.entities.User;
import com.my.model.services.AccountService;
import com.my.model.services.CreditCardService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class AddCreditCard implements Command {
    private AccountService accountService;
    CreditCardService creditCardService;
    public AddCreditCard(AccountService accountService, CreditCardService creditCardService){
        this.accountService = accountService;
        this.creditCardService = creditCardService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        String number = request.getParameter("number");
        String cvv = request.getParameter("cvv");
        String expireDate = request.getParameter("expire_date");
        int accountId = Integer.parseInt(request.getParameter("accountId"));
        //Double balanceAmount = Double.valueOf(request.getParameter("balance_amount"));

//        if (email == null || email.equals("")) {
//            request.getSession().setAttribute("error", "registrationEmail");
//            return "view/registration.jsp";
//        }
//        if (username == null || username.equals("")) {
//            request.getSession().setAttribute("error", "registrationLogin");
//            return "view/registration.jsp";
//        }
//        if (password == null || password.equals("")) {
//            request.getSession().setAttribute("error", "registrationPassword");
//            return "view/registration.jsp";
//        }
        CreditCard creditCard = new CreditCard(number, cvv, expireDate, accountId);
        creditCardService.addCreditCard(creditCard);

        return "redirect:/?command=getAccounts";
    }
}
