package com.my.controller.commands.implementations;

import com.my.controller.Servlet;
import com.my.controller.commands.Command;
import com.my.model.dao.AccountDao;
import com.my.model.dao.CreditCardDao;
import com.my.model.dao.DaoFactory;
import com.my.model.entities.Account;
import com.my.model.entities.CreditCard;
import com.my.model.entities.User;
import com.my.model.services.AccountService;
import com.my.model.services.CreditCardService;
import com.my.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class GetAccounts implements Command {
    private AccountService accountService;
    private CreditCardService creditCardService;

    public GetAccounts(AccountService accountService, CreditCardService creditCardService){
        this.accountService = accountService;
        this.creditCardService = creditCardService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute("user");
        if(currentUser == null){
            Servlet.logger.error("User is null in command /GetAccounts/");
            return "redirect:/";
        }
        List<Account> accountList = accountService.findByUserId(currentUser.getId());
        Map<Integer, List<CreditCard>> creditCardMap = creditCardService.findByUserId(currentUser.getId());


        request.getSession().setAttribute("accountList", accountList);
        request.getSession().setAttribute("creditCardMap", creditCardMap);
        return "redirect:/view/accounts.jsp";
    }
}
