package com.my.controller.commands.implementations.payment;

import com.my.controller.Servlet;
import com.my.controller.commands.Command;
import com.my.model.entities.Account;
import com.my.model.entities.CreditCard;
import com.my.model.entities.Payment;
import com.my.model.entities.User;
import com.my.model.services.AccountService;
import com.my.model.services.CreditCardService;
import com.my.model.services.PaymentService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.my.model.services.PaymentService.PAYMENT_SORT_TYPES;

public class GetPayments implements Command {
    private AccountService accountService;
    private CreditCardService creditCardService;
    private PaymentService paymentService;

    public GetPayments(AccountService accountService, CreditCardService creditCardService, PaymentService paymentService){
        this.accountService = accountService;
        this.creditCardService = creditCardService;
        this.paymentService = paymentService;
    }
    @Override
    public String execute(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute("user");
        if(currentUser == null){
            Servlet.logger.error("User is null in command /GetPayments/");
            return "redirect:/";
        }


        String sortType = request.getParameter("paymentSortType");
        //Servlet.logger.info("sortType = " + sortType);
        if(sortType == null){
            sortType = Objects.requireNonNullElse(
                    (String)request.getSession().getAttribute("paymentSortType"),
                    PAYMENT_SORT_TYPES[0]);
        }
        Integer pageNumber = Integer.valueOf(Optional.ofNullable(request.getParameter("paymentPageNumber")).orElse("1"));
        String pageCommand = Optional.ofNullable(request.getParameter("pageCommand")).orElse("");
        Servlet.logger.info("page number:" + pageNumber);
        List<Payment> paymentList =
                paymentService.getPayments(currentUser.getId(),
                        pageNumber,
                        pageCommand,
                        sortType);
        //System.out.println(paymentList.size());
        pageNumber = paymentService.getPage(currentUser.getId(),
                pageNumber,
                pageCommand);

        request.getSession().setAttribute("paymentList", paymentList);


        request.getSession().setAttribute("paymentSortType", sortType);
        request.getSession().setAttribute("paymentSortTypes",
                Arrays.stream(PAYMENT_SORT_TYPES).toList());

        //Map<Integer, List<CreditCard>> creditCardMap = creditCardService.findByUserId(currentUser.getId());

        Map<Integer, Account> accountMap = accountService.findAll();
        request.getSession().setAttribute("accountMap", accountMap);
        request.getSession().setAttribute("paymentPageNumber", pageNumber);


        return "redirect:/view/payments.jsp";
    }
}
