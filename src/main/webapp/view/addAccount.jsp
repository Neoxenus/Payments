<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${empty language}">
    <c:set var="language" scope="session" value="${pageContext.request.locale.language}"/>
</c:if>
<c:if test="${!empty language}">
    <fmt:setLocale value="${language}" scope="session"/>
</c:if>

<fmt:setBundle basename="Localization"/>
<html>
<head>
    <title>Payments</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>

<jsp:include page="header.jsp"/>

<div class="container mt-4" style="width: 20em; border: 2px solid #999; border-radius: 5px">
    <form method="post" action="<c:url value="/"/>"  class="form-group">

        <input name="command" type="hidden" value="addAccount">

        <label for="number" class="form-label"><fmt:message key='account.table.accountNumber'/></label>
        <input type="text" name="number" class="form-control" required
               id="number">
        <br/>
        <label for="account_name" class="form-label"><fmt:message key='account.table.accountName'/></label>
        <input type="text" name="account_name" class="form-control" required
               id="account_name">
        <br/>
        <label for="IBAN" class="form-label"><fmt:message key='account.table.iban'/></label>
        <input type="text" name="IBAN" class="form-control" required
               id="IBAN">
        <br/>
        <input type="submit" class="btn btn-info" placeholder="<fmt:message key='accounts.addAccount'/>">
    </form>
</div>
<c:if test="${sessionScope.error == 'accountExists'}">
    <script>
        alert("Account with such number already exists")
    </script>
    ${sessionScope.error = null}
</c:if>
</body>