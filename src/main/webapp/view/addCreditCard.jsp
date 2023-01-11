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
        <form class=" mr-auto form-inline mx-2" method="get">
            <%--                <a class="btn btn-primary my-2" href="<c:url value="/view/accounts.jsp"/>">Accounts</a>--%>
            <input name="command" type="hidden" value="getAccounts">
            <%--                <input name="pageNum" type="hidden" value="1">--%>
            <%--                <input name="sortType" type="hidden" value="default">--%>
            <button class="btn btn-primary mt-4" type="submit">Accounts</button>
        </form>
        <c:choose>
            <c:when test="${sessionScope.user == null}">
                <div class="navbar-nav">

                    <a class="btn btn-primary mx-2" href="<c:url value="/view/login.jsp"/>">Login</a>

                    <a class="btn btn-primary mx-2" href="<c:url value="/view/registration.jsp"/>">Sign Up</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="mx-2">Logged as ${sessionScope.user.name}</div>
                <form  class="form-inline mx-2" action="home" method="get">
                    <input name="command" type="hidden" value="logOut">
                    <button class="btn btn-primary mt-4" type="submit">LogOut</button>
                </form>
            </c:otherwise>
        </c:choose>

    </nav>
</header>
<div class="container mt-4" style="width: 20em; border: 2px solid #999; border-radius: 5px">
    <form method="post" action="<c:url value="/"/>"  class="form-group">

        <input name="command" type="hidden" value="addCreditCard">
        <input name="accountId" type="hidden" value="${param.accountId}">

        <label for="number" class="form-label">Number</label>
        <input type="text" autocomplete="off" name="number" class="form-control"
               id="number">
        <br/>
        <label for="cvv" class="form-label">cvv</label>
        <input type="text" autocomplete="off" name="cvv" class="form-control"
               id="cvv">
        <br/>
        <label for="expire_date" class="form-label">Expire date</label>
        <input type="text" autocomplete="off" name="expire_date" class="form-control"
               id="expire_date">
        <br/>
        <input type="submit" class="btn btn-info" value="Add Credit Card">
    </form>
</div>
</body>