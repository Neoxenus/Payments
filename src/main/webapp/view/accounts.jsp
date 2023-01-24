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
  <style>
    tr > .right-border{
      border-right: 1px solid #000;
    }
  </style>
</head>
<body>

<jsp:include page="header.jsp"/>

<br>
<div style="width: 80%; margin: auto">
  <h3 class="text-center"><fmt:message key='account.head.name'/></h3>
  <hr>
  <div class="container text-center">
    <a href="<c:url value="/view/addAccount.jsp"/>" class="btn btn-block btn-success">
      <fmt:message key='accounts.addAccount'/>
    </a>
  </div>

  <br>
  <table class="table table-bordered align-middle">
    <tr>
      <th colspan="7" class="right-border"><fmt:message key='account.table.accounts'/></th>

      <th colspan="4"><fmt:message key='account.table.creditCards'/></th>
    </tr>
    <tr>
      <th><fmt:message key='account.table.accountNumber'/></th>
      <th><fmt:message key='account.table.accountName'/></th>
      <th><fmt:message key='account.table.iban'/></th>
      <th><fmt:message key='account.table.dateOfRegistration'/></th>
      <th ><fmt:message key='account.table.balanceAmount'/></th>
      <th><fmt:message key='account.table.blockedStatus'/></th>
      <th class="d-flex justify-content-center right-border">
        <form action="<c:url value="/"/>" method="get">
          <input name="command" type="hidden" value="getAccounts">
          <label for="sortSelect"><fmt:message key='account.table.sortingBy'/>:</label>
          <select class="form-select" id="sortSelect" name="accountSortType" onchange="this.form.submit()">
            <%--@elvariable id="accountSortTypes" type="java.util.List"--%>
            <c:forEach var="type" items="${accountSortTypes}" varStatus="loop">
              <c:choose>
                <%--@elvariable id="accountSortType" type="java.lang.String"--%>
                <c:when test="${accountSortType == type}">
                  <option value="${type}" selected = "selected">
                      ${type}
                  </option>
                </c:when>
                <c:otherwise>
                  <option value="${type}">
                      ${type}
                  </option>
                </c:otherwise>
              </c:choose>
            </c:forEach>
          </select>
        </form>
      </th>


      <th><fmt:message key='account.table.creditCardNumber'/></th>
      <th><fmt:message key='account.table.cvv'/></th>
      <th><fmt:message key='account.table.expireDate'/></th>
      <th></th>

    </tr>
      <c:choose>
        <c:when test="${sessionScope.accountList.isEmpty()}">
          <tr>
            <td colspan="7" class="right-border"><fmt:message key='account.table.noAccountsMessage1'/></td>
            <td colspan="4"><fmt:message key='account.table.noAccountsMessage2'/></td>
          </tr>
        </c:when>
        <c:otherwise>
          <%--@elvariable id="accountList" type="java.util.List"--%>
          <c:forEach var="account" items="${accountList}">
            <c:set var="rowspan" value="${sessionScope.creditCardMap.get(account.id).size()}"/>
            <c:if test="${rowspan == 0}">
              <c:set var="rowspan" value="1"/>
            </c:if>

            <tr${account.isBlocked eq 'ACTIVE' ? '' : 'class="disabled"'}>
              <td rowspan="${rowspan}">${account.number}</td>
              <td rowspan="${rowspan}">${account.accountName}</td>
              <td rowspan="${rowspan}">${account.IBAN}</td>
              <td rowspan="${rowspan}">
                <fmt:parseDate value="${account.dateOfRegistration}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both"/>
                <fmt:formatDate value="${parsedDateTime}" pattern="dd.MM.yyyy HH:mm:ss"/>
              </td>
              <td rowspan="${rowspan}"><fmt:formatNumber type="number" maxFractionDigits="2" value="${account.balanceAmount}"/></td>
              <td rowspan="${rowspan}">${account.isBlocked}</td>
              <td rowspan="${rowspan}" class="d-flex justify-content-around right-border">
                <form  class="form-inline mx-1" method="get" action="<c:url value="/"/>">
                  <input name="command" type="hidden" value="blockAccount">
                  <input name="accountId" type="hidden" value="${account.id}">
                  <c:choose>
                    <c:when test="${account.isBlocked eq 'ACTIVE'}">
                      <button class="btn btn-sm btn-warning mt-3"
                              type="submit">
                        <fmt:message key='account.table.block'/>
                      </button>
                    </c:when>
                    <c:otherwise>
                      <button class="btn btn-sm btn-warning mt-3"
                              type="submit">
                        <fmt:message key='account.table.unblock'/>
                      </button>
                    </c:otherwise>
                  </c:choose>
                </form>
                <form  class="form-inline mx-1" action="<c:url value="/view/replenishAccount.jsp"/>" method="post">
                  <input name="command" type="hidden" value="replenishAccount">
                  <input name="accountId" type="hidden" value="${account.id}">
                  <input name="account" type="hidden" value="${account.number}">
                  <button class="btn btn-sm btn-success mt-3"
                          ${account.isBlocked eq 'ACTIVE' ? '' : 'disabled'}
                          type="submit">
                    <fmt:message key='account.table.replenish'/>
                  </button>
                </form>
              </td>

              <c:choose>
              <c:when test="${sessionScope.creditCardMap.get(account.id).isEmpty()}">
                <td colspan="3">
                  <fmt:message key='account.table.noCreditCard'/>
                </td>
                <td>
                  <form  class="form-inline mx-1" action="<c:url value="/view/addCreditCard.jsp"/>" method="post">
                    <input name="accountId" type="hidden" value="${account.id}">

                    <button class="btn btn-sm btn-success mt-3"
                      ${account.isBlocked eq 'ACTIVE' ? '' : 'disabled'}
                            type="submit">
                      <fmt:message key='account.table.addCreditCard'/>
                    </button>
                  </form>
                </td>
              </c:when>
              <c:otherwise>
                <c:set var="j" value="0"/>
                <%--@elvariable id="creditCardMap" type="java.util.Map"--%>
                <c:forEach var="creditCard" items="${creditCardMap.get(account.id)}">
                  <c:if test="${j>0}">
                    <tr
                    ${account.isBlocked eq 'ACTIVE' ? '' : 'class="disabled"'}
                    >
                  </c:if>
                  <td>${creditCard.number}</td>
                  <td>${creditCard.cvv}</td>
                  <td>${creditCard.expireDate}</td>
                  <td>
                    <form  class="form-inline mx-1" action="<c:url value="/"/>" method="post">
                      <input name="command" type="hidden" value="deleteCreditCard">
                      <input name="creditCardId" type="hidden" value="${creditCard.id}">
                      <button class="btn btn-sm btn-warning mt-3"
                        ${account.isBlocked eq 'ACTIVE' ? '' : 'disabled'}
                              type="submit">
                        <fmt:message key='account.table.deleteCreditCard'/>
                      </button>

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
        </c:forEach>
        </c:otherwise>
      </c:choose>
  </table>
</div>

</body>