package com.my.controller.commands.implementations;

import com.my.controller.commands.Command;
import com.my.model.services.UserService;
import com.my.model.entities.User;

import javax.servlet.http.HttpServletRequest;

public class Registration implements Command {

    private UserService userService;
    public Registration(UserService userService){
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String phoneNumber = request.getParameter("phone_number");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.equals("")) {
            request.getSession().setAttribute("error", "registrationEmail");
            return "view/registration.jsp";
        }
        if (username == null || username.equals("")) {
            request.getSession().setAttribute("error", "registrationLogin");
            return "view/registration.jsp";
        }
        if (password == null || password.equals("")) {
            request.getSession().setAttribute("error", "registrationPassword");
            return "view/registration.jsp";
        }
        User user = new User(username, phoneNumber, email, password);
        userService.addUser(user);

        //mb login
        return "redirect:/";
    }
}
