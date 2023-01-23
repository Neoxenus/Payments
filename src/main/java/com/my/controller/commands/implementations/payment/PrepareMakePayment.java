package com.my.controller.commands.implementations.payment;

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

public class PrepareMakePayment implements Command {
    private AccountService accountService;
    private CreditCardService creditCardService;

    public PrepareMakePayment(AccountService accountService, CreditCardService creditCardService){
        this.accountService = accountService;
        this.creditCardService = creditCardService;
    }
    @Override
    public String execute(HttpServletRequest request) {

        User currentUser = (User) request.getSession().getAttribute("user");
        if(currentUser == null){
            Servlet.logger.error("User is null in command /MakePayments/");
            return "redirect:/";
        }

        List<Account> accountList = accountService.findByUserIdNotBlocked(currentUser.getId());
        if(accountList.isEmpty()){
            Servlet.logger.warn("No accounts for make payment");
            request.getSession().setAttribute("error", "noAccountForPayment");
            return "redirect:/?command=getPayments";

        }
        Map<Integer, List<CreditCard>> creditCardMap = creditCardService.findByUserId(currentUser.getId());


        request.getSession().setAttribute("accountListNotBlocked", accountList);
        request.getSession().setAttribute("creditCardMap", creditCardMap);
        return "redirect:/view/makePayment.jsp";
    }
}
