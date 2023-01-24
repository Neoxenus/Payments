package com.my.controller.commands.implementations;

import com.my.controller.commands.Command;

import javax.servlet.http.HttpServletRequest;

public class ChangeLanguage implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String language = request.getParameter("language");
        request.getSession().setAttribute("language", language);
        return "redirect:/";
    }
}
