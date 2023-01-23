package com.my.controller.commands.implementations.user;

import com.my.controller.commands.Command;
import com.my.model.entities.User;
import com.my.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BlockUser implements Command {
    UserService userService;

    public BlockUser(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        userService.setBlocked(userId);
        return "redirect:/?command=getUsersAdmin";

        //request.getSession().setAttribute("userList", userList);
    }
}
