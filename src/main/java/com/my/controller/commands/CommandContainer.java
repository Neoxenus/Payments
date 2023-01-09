package com.my.controller.commands;

import com.my.controller.commands.implementations.*;
import com.my.model.services.AccountService;
import com.my.model.services.CreditCardService;
import com.my.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static final Map<String, Command> commands;

    private static final UserService userService = new UserService();
    private static final AccountService accountService = new AccountService();
    private static final CreditCardService creditCardService = new CreditCardService();

    private CommandContainer(){}
    static {
        commands = new HashMap<>();
        commands.put("register", new Registration(userService));
        commands.put("login", new LogIn(userService));
        commands.put("logOut", new LogOut());
        commands.put("getAccounts", new GetAccounts(accountService, creditCardService));
        commands.put("addAccount", new AddAccount(accountService));
        commands.put("blockAccount", new BlockAccount(accountService));
        commands.put("addCreditCard", new AddCreditCard(accountService, creditCardService));
        commands.put("deleteCreditCard", new DeleteCreditCard(creditCardService));
    }
    public static Command getCommand(String command) {
        return commands.get(command);
    }

    public static String doCommand(Command command, HttpServletRequest request) {
        return command.execute(request);
    }
}
