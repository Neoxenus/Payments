package com.my.controller.filter;

import com.my.controller.Servlet;
import com.my.model.entities.User;
import com.my.model.entities.enums.Role;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/")
public class AccessFilter implements Filter {
    @Override
    public void init(FilterConfig config) throws ServletException {
    }
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Servlet.logger.info("Enter into AccessFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String param = String.valueOf(request.getParameter("command"));
        if(param.contains("Admin")
            || param.contains("User")){
            User user = (User) req.getSession().getAttribute("user");
            if (user == null || user.getRole().equals(Role.USER)) {
                Servlet.logger.warn("User tried to access admin page without permission");
                resp.sendRedirect(((HttpServletRequest) request).getContextPath() + "/");
                return;
            } else {
                Servlet.logger.info("Admin opened admin page");
            }
        }
        chain.doFilter(request, response);
    }
}
