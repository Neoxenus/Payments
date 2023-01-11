package com.my.controller.commands.implementations;

import com.my.controller.Servlet;
import com.my.controller.commands.Command;
import com.my.model.entities.Account;
import com.my.model.entities.User;
import com.my.model.services.AccountService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ReplenishAccount implements Command {
    public ReplenishAccount(AccountService accountService) {
        this.accountService = accountService;
    }

    AccountService accountService;

    @Override
    public String execute(HttpServletRequest request) {

        int accountId = Integer.parseInt((request.getParameter("accountId")));
        double amount = Double.parseDouble((request.getParameter("amount")));
        accountService.replenishAccount(accountId, amount);

        return "redirect:/?command=getAccounts";
    }
}
