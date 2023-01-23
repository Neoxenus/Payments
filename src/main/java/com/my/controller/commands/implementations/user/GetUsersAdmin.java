package com.my.controller.commands.implementations.user;

import com.my.controller.Servlet;
import com.my.controller.commands.Command;
import com.my.model.entities.Account;
import com.my.model.entities.CreditCard;
import com.my.model.entities.User;
import com.my.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GetUsersAdmin implements Command {


    UserService userService;

    public GetUsersAdmin(UserService userService) {
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest request) {
//        User currentUser = (User) request.getSession().getAttribute("user");
//        if(currentUser == null){
//            Servlet.logger.error("User is null in command /GetAccounts/");
//            return "redirect:/";
//        }

//        String sortType = request.getParameter("accountSortType");
//        //String selectValue = request.getParameter("selectValue");
//        Servlet.logger.info("sortType = " + sortType);
//        if(sortType == null){
//            sortType = Objects.requireNonNullElse(
//                    (String)request.getSession().getAttribute("accountSortType"),
//                    "Number");
//        }

//        List<Account> accountList = accountService.findByUserId(currentUser.getId());
//        Map<Integer, List<CreditCard>> creditCardMap = creditCardService.findByUserId(currentUser.getId());
//        //accountList = accountService.getAccountsSortedByParameter(accountList, sortType);
//        accountService.sortAccountsByParameter(accountList, sortType);
//
//        request.getSession().setAttribute("accountSortType", sortType);
//        request.getSession().setAttribute("accountSortTypes",
//                Arrays.stream(new String[]{"Number", "Account Name", "Amount"}).toList());
//        request.getSession().setAttribute("accountList", accountList);
//        request.getSession().setAttribute("creditCardMap", creditCardMap);
        List<User> userList = userService.findAllUsers();
        request.getSession().setAttribute("userList", userList);

        return "redirect:/view/users.jsp";
    }
}
