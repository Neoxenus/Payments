package com.my.controller.commands;

import com.my.controller.commands.implementations.account.*;
import com.my.controller.commands.implementations.credit_card.AddCreditCard;
import com.my.controller.commands.implementations.credit_card.DeleteCreditCard;
import com.my.controller.commands.implementations.payment.*;
import com.my.controller.commands.implementations.user.*;
import com.my.model.services.AccountService;
import com.my.model.services.CreditCardService;
import com.my.model.services.PaymentService;
import com.my.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static final Map<String, Command> commands;

    private static final UserService userService = new UserService();
    private static final AccountService accountService = new AccountService();
    private static final CreditCardService creditCardService = new CreditCardService();
    private static final PaymentService paymentService = new PaymentService();

    private CommandContainer(){}
    static {
        commands = new HashMap<>();
        commands.put("register", new Registration(userService));
        commands.put("login", new LogIn(userService));
        commands.put("logOut", new LogOut());

        commands.put("getAccounts", new GetAccounts(accountService, creditCardService));
        commands.put("addAccount", new AddAccount(accountService));
        commands.put("blockAccount", new BlockAccount(accountService));
        commands.put("replenishAccount", new ReplenishAccount(accountService));

        commands.put("addCreditCard", new AddCreditCard(accountService, creditCardService));
        commands.put("deleteCreditCard", new DeleteCreditCard(creditCardService));


        commands.put("prepareMakePayment", new PrepareMakePayment(accountService, creditCardService));
        commands.put("makePayment", new MakePayment(accountService, creditCardService, paymentService));
        commands.put("getPayments", new GetPayments(accountService, creditCardService, paymentService));
        commands.put("cancelPayment", new CancelPayment(paymentService));
        commands.put("sendPayment", new SendPayment(accountService, paymentService));
        //admin commands:
        commands.put("getUsersAdmin", new GetUsersAdmin(userService));
        commands.put("blockUser", new BlockUser(userService));
        commands.put("promoteUser", new PromoteUser(userService));

        commands.put("getAccountsAdmin", new GetAccountsAdmin(userService, accountService, creditCardService));
        commands.put("blockAccountAdmin", new BlockAccountAdmin(accountService));


    }
    public static Command getCommand(String command) {
        return commands.get(command);
    }

    public static String doCommand(Command command, HttpServletRequest request) {
        return command.execute(request);
    }
}

