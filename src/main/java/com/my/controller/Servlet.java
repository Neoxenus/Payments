package com.my.controller;

import com.my.controller.commands.Command;
import com.my.controller.commands.CommandContainer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * todo
 * <p>
 * payments
 *
 * <p></p>
 * sorting
 * paggination???
 * unblock account button
 */



@WebServlet("/")
public class Servlet extends HttpServlet {

    public static final Logger logger = LogManager.getLogger(Servlet.class);


    @Override
    public void init() {
        logger.info("==========================================================================");
        logger.info("init servlet");
        //System.out.println("init servlet");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        servletHandler(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        servletHandler(req, resp);
    }
    private void servletHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();

        if (httpSession.isNew()) {
            httpSession.setAttribute("user", null);
        }
        String page = "view/home.jsp";

        String commandName = request.getParameter("command");
        logger.info("Servlet.doGet; commandName ==> " + commandName);

        if(commandName == null) {
            request.getRequestDispatcher(page).forward(request, response);

        } else {

            Command command = CommandContainer.getCommand(commandName);
            page = CommandContainer.doCommand(command, request);

            if (page.contains("redirect")) {
                logger.info("Redirect to "+ request.getContextPath() + page.replace("redirect:",""));
                response.sendRedirect(request.getContextPath() + page.replace("redirect:",""));
            } else {
                logger.info("Forward to " + page);
                request.getRequestDispatcher(page).forward(request, response);
            }
        }
    }
}
