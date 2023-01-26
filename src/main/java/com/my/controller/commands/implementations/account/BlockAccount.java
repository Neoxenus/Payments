package com.my.controller.commands.implementations.account;

import com.my.controller.commands.Command;
import com.my.model.services.AccountService;

import javax.servlet.http.HttpServletRequest;

public class BlockAccount implements Command {
    private AccountService accountService;
    public BlockAccount(AccountService accountService){
        this.accountService = accountService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        accountService.block(Integer.parseInt(request.getParameter("accountId")));
        return "redirect:/?command=getAccounts";
    }
}
