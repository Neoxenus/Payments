package com.my.controller.commands.implementations.user;

import com.my.controller.commands.Command;
import com.my.model.services.UserService;

import javax.servlet.http.HttpServletRequest;

public class PromoteUser implements Command {
    UserService userService;

    public PromoteUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        userService.promoteUser(userId);
        return "redirect:/?command=getUsersAdmin";

        //request.getSession().setAttribute("userList", userList);
    }
}
