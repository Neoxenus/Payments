<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
  <title>Payments</title>
  <link rel="stylesheet"
        href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
        crossorigin="anonymous">
  <style>
    tr > .right-border{
      border-right: 1px solid #000;
    }
  </style>
</head>
<body>

<header>
  <nav class="navbar navbar-expand-md navbar-dark"
       style="background-color: darkslategray; color:white">
    <div class="">
      <a href="<c:url value="/"/>" class="navbar-brand"> Payments </a>


      <%--            <c:out value="${sessionScope.user.name}"/>--%>
      <%--            <c:out value='<%= request.getSession().getAttribute("userName")%>'/>--%>
    </div>
    <c:choose>
      <c:when test="${sessionScope.user == null}">
        <div class="navbar-nav">

          <a class="btn btn-primary mx-2" href="<c:url value="/view/login.jsp"/>">Login</a>

          <a class="btn btn-primary mx-2" href="<c:url value="/view/registration.jsp"/>">Sign Up</a>
        </div>
      </c:when>
      <c:otherwise>

        <form class=" mr-auto form-inline mx-2" method="post">
            <%--                <a class="btn btn-primary my-2" href="<c:url value="/view/accounts.jsp"/>">Accounts</a>--%>
          <input name="command" type="hidden" value="getAccounts">
            <%--                <input name="pageNum" type="hidden" value="1">--%>
            <%--                <input name="sortType" type="hidden" value="default">--%>
          <button class="btn btn-primary mt-3" type="submit">Accounts</button>
        </form>

        <div class="mx-2">Logged as ${sessionScope.user.name}</div>
        <form  class="form-inline mx-2" action="home" method="get">
          <input name="command" type="hidden" value="logOut">
          <button class="btn btn-primary mt-3" type="submit">LogOut</button>
        </form>
      </c:otherwise>
    </c:choose>

  </nav>
</header>
<br>
<div style="width: 80%; margin: auto">
  <h3 class="text-center">Account and credit cards</h3>
  <hr>
  <div class="container text-center">
    <a href="/view/addAccount.jsp" class="btn btn-block btn-success">Add
      Account</a>
  </div>

  <br>
  <table class="table table-bordered align-middle">
    <tr>
      <th colspan="7" class="right-border">Accounts</th>

      <th colspan="4">Credit Cards</th>
    </tr>
    <tr>
      <th>Account number</th>
      <th>Account name</th>
      <th>IBAN</th>
      <th>Date of registration</th>
      <th >Balance amount</th>
      <th>Blocked</th>
      <th class="right-border"></th>

      <th>Credit card number</th>
      <th>cvv</th>
      <th>Expire date</th>
      <th></th>

    </tr>
      <c:choose>
        <c:when test="${sessionScope.accountList.isEmpty()}">
          <tr>
            <td colspan="7" class="right-border">You don't have accounts yet</td>
            <td colspan="4">Add account before add credit card</td>
          </tr>
        </c:when>
        <c:otherwise>
          <c:forEach var="account" items="${accountList}">
            <c:set var="rowspan" value="${sessionScope.creditCardMap.get(account.id).size()}"/>
            <c:if test="${rowspan == 0}">
              <c:set var="rowspan" value="1"/>
            </c:if>

            <tr>
              <td rowspan="${rowspan}">${account.number}</td>
              <td rowspan="${rowspan}">${account.accountName}</td>
              <td rowspan="${rowspan}">${account.IBAN}</td>
              <td rowspan="${rowspan}">
                <fmt:parseDate value="${account.dateOfRegistration}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
                <fmt:formatDate value="${parsedDateTime}" pattern="dd.MM.yyyy HH:mm"/>
              </td>
              <td rowspan="${rowspan}"><fmt:formatNumber type="number" maxFractionDigits="2" value="${account.balanceAmount}"/></td>
              <td rowspan="${rowspan}">
                <c:choose>
                  <c:when test="${account.isBlocked}">YES</c:when>
                  <c:otherwise>NO</c:otherwise>
                </c:choose>
              </td>
              <td rowspan="${rowspan}" class="right-border">
                <form  class="form-inline mx-1" method="get">
                  <input name="command" type="hidden" value="blockAccount">
                  <input name="accountId" type="hidden" value="${account.id}">
                  <button class="btn btn-sm btn-warning mt-3" type="submit">Block Account</button>
                </form>
                <form  class="form-inline mx-1" action="<c:url value="/view/replenishAccount.jsp"/>" method="post">
                  <input name="command" type="hidden" value="replenishAccount">
                  <input name="accountId" type="hidden" value="${account.id}">
                  <input name="account" type="hidden" value="${account.number}">
                  <button class="btn btn-sm btn-success mt-3"
                          ${account.isBlocked eq true ? 'disabled="disabled"' : ''}
                          type="submit">Replenish Account</button>
                </form>
              </td>

              <c:choose>
              <c:when test="${sessionScope.creditCardMap.get(account.id).isEmpty()}">
                <td colspan="3">
                  No credit cards for this account
                </td>
                <td>
                  <form  class="form-inline mx-1" action="<c:url value="/view/addCreditCard.jsp"/>" method="post">
                    <input name="accountId" type="hidden" value="${account.id}">

                    <button class="btn btn-sm btn-success mt-3" type="submit">Add Credit Card</button>
                  </form>
                </td>
              </c:when>
              <c:otherwise>
                <c:set var="j" value="0"/>
                <c:forEach var="creditCard" items="${creditCardMap.get(account.id)}">
                  <c:if test="${j>0}">
                    <tr>
                  </c:if>
                  <td>${creditCard.number}</td>
                  <td>${creditCard.cvv}</td>
                  <td>${creditCard.expireDate}</td>
                  <td>
                    <form  class="form-inline mx-1" action="home" method="post">
                      <input name="command" type="hidden" value="deleteCreditCard">
                      <input name="creditCardId" type="hidden" value="${creditCard.id}">
                      <button class="btn btn-sm btn-warning mt-3" type="submit">Delete</button>
                    </form>
                  </td>
                  <c:if test="${j>0}">
                    </tr>
                  </c:if>
                  <c:set var="j" value="${j+1}"/>
                </c:forEach>

              </c:otherwise>
            </c:choose>
            </tr>
            <c:set var="i" value="${i+1}"/>

        </c:forEach>
        </c:otherwise>
      </c:choose>
  </table>
</div>
</body>