package com.my.controller.commands.implementations;

import com.my.controller.commands.Command;
import com.my.model.services.UserService;
import com.my.model.entities.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class LogIn implements Command {
    private UserService userService;

    public LogIn(UserService userService){
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(email == null || email.equals("")) {
            request.getSession().setAttribute("error", "loginLogin");
            return "view/login.jsp";
        }
        if(password == null || password.equals("")) {
            request.getSession().setAttribute("error", "loginPassword");
            return "view/login.jsp";
        }

        Optional<User> user = userService.findByEmailAndPassword(email, password);
        if (user.isEmpty()) {
            request.getSession().setAttribute("error", "badLogin");
            return "view/login.jsp";
        }


        request.getSession().setAttribute("user", user.get());
        return "redirect:/";
    }
}
