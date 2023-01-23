package com.my.controller.commands.implementations.account;

import com.my.controller.Servlet;
import com.my.controller.commands.Command;
import com.my.model.entities.Account;
import com.my.model.entities.CreditCard;
import com.my.model.entities.User;
import com.my.model.entities.enums.Block;
import com.my.model.services.AccountService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class BlockAccount implements Command {
    private AccountService accountService;
    public BlockAccount(AccountService accountService){
        this.accountService = accountService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        accountService.setBlocked(Integer.parseInt(request.getParameter("accountId")));
        return "redirect:/?command=getAccounts";
    }
}
