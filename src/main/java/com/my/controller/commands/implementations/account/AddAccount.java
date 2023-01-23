package com.my.controller.commands.implementations.account;

import com.my.controller.Servlet;
import com.my.controller.commands.Command;
import com.my.model.entities.User;
import com.my.model.services.AccountService;
import com.my.model.services.PaymentService;

import javax.servlet.http.HttpServletRequest;

public class AddAccount implements Command {
    private AccountService accountService;

    public AddAccount(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String number = request.getParameter("number");
        String accountName = request.getParameter("account_name");
        String iban = request.getParameter("IBAN");

        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            Servlet.logger.error("User is null in command /AddAccount/");
            return "redirect:/";
        }
        if(accountService.addAccount(number, accountName, iban,
                currentUser.getId())){
            return "redirect:/?command=getAccounts";
        } else {
            request.getSession().setAttribute("error", "accountExists");
            return "view/addAccount.jsp";
        }
    }
}
