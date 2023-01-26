package com.my.controller.commands.implementations.account;

import com.my.controller.commands.Command;
import com.my.model.entities.Account;
import com.my.model.services.AccountService;

import javax.servlet.http.HttpServletRequest;

public class BlockAccountAdmin implements Command {
    AccountService accountService;

    public BlockAccountAdmin(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int accountId = Integer.parseInt(request.getParameter("accountId"));
        Account account = accountService.blockAdmin(accountId);
        int userId = account.getUserId();
        return "redirect:/?command=getAccountsAdmin&userId="+userId;
    }
}
