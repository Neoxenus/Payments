package com.my.controller.commands.implementations.user;

import com.my.controller.Servlet;
import com.my.controller.commands.Command;
import com.my.model.entities.User;
import com.my.model.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class GetUsersAdmin implements Command {


    UserService userService;

    public GetUsersAdmin(UserService userService) {
        this.userService = userService;
    }
    @Override
    public String execute(HttpServletRequest request) {


        Integer pageNumber = Integer.valueOf(Optional.ofNullable(request.getParameter("pageNumber")).orElse("1"));
        String pageCommand = Optional.ofNullable(request.getParameter("pageCommand")).orElse("");
        Servlet.logger.info("page number:" + pageNumber);
//        List<Payment> paymentList =
//                paymentService.getPayments(currentUser.getId(),
//                        pageNumber,
//                        pageCommand,
//                        sortType);
//        pageNumber = paymentService.getPage(currentUser.getId(),
//                pageNumber,
//                pageCommand);
//
//        request.getSession().setAttribute("paymentList", paymentList);

        List<User> userList = userService.getUsers(pageNumber, pageCommand);
        pageNumber = userService.getPage(pageNumber, pageCommand);
        request.getSession().setAttribute("pageNumber", pageNumber);



        request.getSession().setAttribute("userList", userList);

        return "redirect:/view/users.jsp";
    }
}
