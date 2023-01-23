package com.my.controller.commands.implementations.user;

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


        if(userService.addUser(username, phoneNumber, email, password)){
            //mb login
            return "redirect:/";
        } else {
            request.getSession().setAttribute("error", "userExists");
            return "view/registration.jsp";
        }


    }
}
