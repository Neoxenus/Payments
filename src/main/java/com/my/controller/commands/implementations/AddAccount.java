package com.my.controller.commands.implementations;

import com.my.controller.Servlet;
import com.my.controller.commands.Command;
import com.my.model.dao.DaoFactory;
import com.my.model.entities.Account;
import com.my.model.entities.CreditCard;
import com.my.model.entities.User;
import com.my.model.services.AccountService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AddAccount implements Command {
    private AccountService accountService;
    public AddAccount(AccountService accountService){
        this.accountService = accountService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        String number = request.getParameter("number");
        String accountName = request.getParameter("account_name");
        String iban = request.getParameter("IBAN");
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

        User currentUser = (User) request.getSession().getAttribute("user");
        if(currentUser == null){
            Servlet.logger.error("User is null in command /AddAccount/");
            return "redirect:/";
        }
        accountService.addAccount(number, accountName, iban,
               currentUser.getId());

        return "redirect:/?command=getAccounts";
    }
}
