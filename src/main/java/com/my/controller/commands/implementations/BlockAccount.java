package com.my.controller.commands.implementations;

import com.my.controller.Servlet;
import com.my.controller.commands.Command;
import com.my.model.entities.Account;
import com.my.model.entities.CreditCard;
import com.my.model.entities.User;
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
        int accountId = Integer.parseInt(request.getParameter("accountId"));
        accountService.setBlocked(accountId, true);

//        User currentUser = (User) request.getSession().getAttribute("user");
//        if(currentUser == null){
//            Servlet.logger.error("User is null in command /GetAccounts/");
//            return "redirect:/home";
//        }
//        List<Account> accountList = accountService.findByUserId(currentUser.getId());
//        request.getSession().setAttribute("accountList", accountList);
        return "redirect:/?command=getAccounts";
    }
}
