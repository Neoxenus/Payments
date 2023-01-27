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


<br>
<div style="width: 80%; margin: auto">
  <h3 class="text-center"><fmt:message key="payment.myPayments"/></h3>
  <hr>
  <div class="container text-center">
    <form class=" mr-auto form-inline mx-2" method="post" action="<c:url value="/"/>">
      <%--                <a class="btn btn-primary my-2" href="<c:url value="/view/accounts.jsp"/>">Accounts</a>--%>
      <input name="command" type="hidden" value="prepareMakePayment">
      <%--                <input name="pageNum" type="hidden" value="1">--%>
      <%--                <input name="sortType" type="hidden" value="default">--%>
      <button class="btn btn-block btn-success" type="submit"><fmt:message key="payment.makePayment"/></button>
    </form>
  </div>

  <br>
  <table class="table table-bordered align-middle">
    <tr>
      <th><fmt:message key="payment.number"/></th>
      <th><fmt:message key="payment.time"/></th>
      <th><fmt:message key="payment.amount"/></th>
      <th><fmt:message key="payment.senderAccount"/></th>
      <th><fmt:message key="payment.receiverAccount"/></th>
      <th><fmt:message key="payment.assignment"/></th>
      <th><fmt:message key="payment.status"/></th>
      <th class="d-flex justify-content-center right-border">
      <form action="<c:url value="/"/>" method="get">
        <input name="command" type="hidden" value="getPayments">
        <label for="sortSelect"><fmt:message key="payment.sortingBy"/>:</label>
        <select class="form-select" id="sortSelect" name="paymentSortType" onchange="this.form.submit()">
          <%--@elvariable id="paymentSortTypes" type="java.util.List"--%>
          <c:forEach var="type" items="${paymentSortTypes}" varStatus="loop">
            <%--@elvariable id="paymentSortType" type="java.lang.String"--%>
<%--            ${paymentSortType eq type ? 'disabled' : ''}--%>
<%--            type="submit"><fmt:message key="payment.cancel"/>--%>
            <option value="${type}"${paymentSortType eq type ? 'selected' : ''}>
              <fmt:message key="payment.sort.${type}"/>
<%--                ${type}--%>
            </option>
          </c:forEach>
        </select>
      </form>
    </th>


    </tr>
    <c:choose>
      <c:when test="${sessionScope.paymentList.isEmpty()}">
        <tr>
          <td colspan="9" class="right-border">
            <fmt:message key="payment.noPaymentsMessage"/>
          </td>
        </tr>
      </c:when>
      <c:otherwise>
        <%--@elvariable id="paymentList" type="java.util.List"--%>
        <c:forEach var="payment" items="${paymentList}"><%--@elvariable id="accountMap" type="Map<Integer, Account>"--%>
          <tr>
            <td >${payment.id}</td>
            <td >
              <fmt:parseDate value="${payment.time}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both"/>
              <fmt:formatDate value="${parsedDateTime}" pattern="dd.MM.yyyy HH:mm:ss"/>
            </td>
            <td ><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${payment.amount}"/></td>
            <td >${accountMap.get(payment.senderAccountId).getNumber()}</td>
            <td >${accountMap.get(payment.receiverAccountId).getNumber()}</td>
            <td >${payment.assignment}</td>
            <td >
              <c:choose>
                <c:when test="${payment.status == 'SENT'}">
                  <fmt:message key='payment.status.sent'/>
                </c:when>
                <c:otherwise>
                  <fmt:message key='payment.status.prepared'/>
                </c:otherwise>
              </c:choose>

            </td>

            <td  class="d-flex justify-content-around">
              <form  class="form-inline" method="post" action="<c:url value="/"/>">
                <input name="command" type="hidden" value="cancelPayment">
                <input name="paymentId" type="hidden" value="${payment.id}">
                <button class="btn btn-warning mt-3"
                  ${payment.status.name() eq "SENT" ? 'disabled' : ''}
                        type="submit"><fmt:message key="payment.cancel"/></button>
              </form>
              <form  class="form-inline" action="<c:url value="/"/>"
                     method="post">
                <input name="command" type="hidden" value="sendPayment">
                <input name="paymentId" type="hidden" value="${payment.id}">
                <button class="btn btn-success mt-3"
                  ${payment.status.name() eq "SENT" ? 'disabled' : ''}
                        type="submit"><fmt:message key="payment.send"/></button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </c:otherwise>
    </c:choose>
  </table>
  <div class="row">
    <div class="col d-flex justify-content-center">
      <form method="get" action="<c:url value="/"/>">
        <input name="command" type="hidden" value="getPayments">
        <input name="pageCommand" type="hidden" value="previous">
        <input type="submit" value="<<" />
      </form>
      <%--@elvariable id="pageNumber" type="int"--%>
      <div class="p-1">
        ${paymentPageNumber}
      </div>
      <form method="get" action="<c:url value="/"/>">
        <input name="command" type="hidden" value="getPayments">
        <input name="pageCommand" type="hidden" value="next">
        <input type="submit" value=">>" />
      </form>
    </div>
  </div>

</div>
<c:if test="${sessionScope.error == 'noAccountForPayment'}">
  <script>
    alert("Create account before make payments")
  </script>
  ${sessionScope.error = null}
</c:if>
</body>