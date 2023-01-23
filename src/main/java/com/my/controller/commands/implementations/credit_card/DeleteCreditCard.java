package com.my.controller.commands.implementations.credit_card;

import com.my.controller.Servlet;
import com.my.controller.commands.Command;
import com.my.model.entities.Account;
import com.my.model.entities.CreditCard;
import com.my.model.entities.User;
import com.my.model.services.CreditCardService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class DeleteCreditCard implements Command {
    CreditCardService creditCardService;

    public DeleteCreditCard(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int creditCardId = Integer.parseInt(request.getParameter("creditCardId"));
        creditCardService.deleteCreditCard(creditCardId);

        User currentUser = (User) request.getSession().getAttribute("user");
        if(currentUser == null){
            Servlet.logger.error("User is null in command /GetAccounts/");
            return "redirect:/home";
        }
        Map<Integer, List<CreditCard>> creditCardMap = creditCardService.findByUserId(currentUser.getId());
        request.getSession().setAttribute("creditCardMap", creditCardMap);

        return "redirect:/view/accounts.jsp";
    }
}
