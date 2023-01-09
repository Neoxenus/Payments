package com.my.controller.commands.implementations;

import com.my.controller.commands.Command;

import javax.servlet.http.HttpServletRequest;

public class LogOut implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute("user", null);
        return "redirect:/";
    }
}
