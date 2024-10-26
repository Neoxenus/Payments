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

      <h4>Replenishment of account ${param.account}</h4>
    <input name="command" type="hidden" value="replenishAccount">
    <input name="accountId" type="hidden" value="${param.accountId}">

    <label for="amount" class="form-label"><fmt:message key='account.amount'/> </label>
    <input type="text" pattern="([\d]+([.][\d]{2})?)" name="amount" class="form-control" placeholder="<fmt:message key='account.amount.format'/>"
           id="amount">
    <br/>
    <input type="submit" class="btn btn-info" value="<fmt:message key='account.table.replenish'/>">
  </form>
</div>
</body>