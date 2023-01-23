package com.my.controller.commands.implementations.account;

import com.my.controller.Servlet;
import com.my.controller.commands.Command;
import com.my.model.entities.Account;
import com.my.model.entities.CreditCard;
import com.my.model.entities.User;
import com.my.model.services.AccountService;
import com.my.model.services.CreditCardService;
import com.my.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GetAccountsAdmin implements Command {
    private UserService userService;
    private AccountService accountService;
    private CreditCardService creditCardService;

    public GetAccountsAdmin(UserService userService, AccountService accountService, CreditCardService creditCardService){
        this.userService = userService;
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

        int userId = Integer.parseInt(request.getParameter("userId"));
        User user = userService.findById(userId).get();
        List<Account> accountList = accountService.findByUserId(userId);
        Map<Integer, List<CreditCard>> creditCardMap = creditCardService.findByUserId(userId);
        //accountList = accountService.getAccountsSortedByParameter(accountList, sortType);
        //accountService.sortAccountsByParameter(accountList, sortType);

//        request.getSession().setAttribute("accountSortType", sortType);
//        request.getSession().setAttribute("accountSortTypes",
//                Arrays.stream(new String[]{"Number", "Account Name", "Amount"}).toList());
        request.getSession().setAttribute("showedUser", user);
        request.getSession().setAttribute("accountList", accountList);
        //request.getSession().setAttribute("creditCardMap", creditCardMap);
        return "redirect:/view/userAccounts.jsp";
    }
}
