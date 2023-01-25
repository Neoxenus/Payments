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
    <h3 class="text-center">Accounts of user:</h3>
    <table class="table table-bordered align-middle">
        <tr>
            <th>User name</th>
            <th>User email</th>
            <th>Phone number</th>
            <th>Role</th>
            <th>Blocked status</th>
            <th>
                <form action="<c:url value="/"/>" method="get">
                    <input name="command" type="hidden" value="getAccountsAdmin">
                    <input name="userId" type="hidden" value="${showedUser.id}">
                    <label for="sortSelect"><fmt:message key='account.table.sortingBy'/>:</label>
                    <select class="form-select" id="sortSelect" name="accountSortType" onchange="this.form.submit()">
                        <%--@elvariable id="accountSortType" type="java.lang.String"--%>
                        <%--@elvariable id="accountSortTypes" type="java.util.List"--%>
                        <c:forEach var="type" items="${accountSortTypes}" varStatus="loop">
                            <option value="${type}" ${accountSortType eq type ? 'selected' : ''}>
                                <fmt:message key="account.sort.${type}"/>
                            </option>
                        </c:forEach>
                    </select>
                </form>
            </th>
        </tr>
        <%--@elvariable id="showedUser" type="com.my.model.entities.User"--%>
        <tr>
            <td>${showedUser.name}</td>
            <td>${showedUser.email}</td>
            <td>${showedUser.phoneNumber}</td>
            <td>
                <c:choose>
                    <c:when test="${showedUser.role eq 'ADMIN'}">
                        <fmt:message key='user.role.admin'/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key='user.role.user'/>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${showedUser.isBlocked eq 'ACTIVE'}">
                        <fmt:message key='user.blockedStatus.active'/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key='user.blockedStatus.blocked'/>
                    </c:otherwise>
                </c:choose>
                <%--                  ${account.isBlocked}--%>
            </td>
            <td class="d-flex justify-content-around">
                <form  class="form-inline mx-1" method="post" action="<c:url value="/"/>">
                    <input name="command" type="hidden" value="blockUser">
                    <input name="userId" type="hidden" value="${showedUser.id}">
                    <c:choose>
                        <c:when test="${showedUser.isBlocked eq 'ACTIVE'}">
                            <button class="btn btn-sm btn-warning mt-3"
                                ${(showedUser.role eq 'ADMIN' and showedUser.isBlocked eq 'ACTIVE')? 'disabled' : ''}
                                    type="submit"><fmt:message key='user.block'/> </button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-sm btn-warning mt-3"
                                ${(showedUser.role eq 'ADMIN' and showedUser.isBlocked eq 'ACTIVE')? 'disabled' : ''}
                                    type="submit"><fmt:message key='user.unblock'/> </button>
                        </c:otherwise>
                    </c:choose>
                </form>
                <form  class="form-inline mx-1" action="<c:url value="/"/>" method="post">
                    <input name="command" type="hidden" value="promoteUser">
                    <input name="userId" type="hidden" value="${showedUser.id}">
                    <button class="btn btn-sm btn-success mt-3"
                    ${showedUser.role eq 'USER' ? '' : 'disabled'}
                            type="submit"><fmt:message key='user.promote'/></button>
                </form>
            </td>
        </tr>
    </table>
    <hr>
    <table class="table table-bordered align-middle">
        <tr>
            <th><fmt:message key='account.table.accountNumber'/></th>
            <th><fmt:message key='account.table.accountName'/></th>
            <th><fmt:message key='account.table.iban'/></th>
            <th><fmt:message key='account.table.dateOfRegistration'/></th>
            <th><fmt:message key='account.table.balanceAmount'/></th>
            <th><fmt:message key='account.table.blockedStatus'/></th>
            <th></th>

        </tr>
        <%--@elvariable id="accountList" type="java.util.List"--%>
        <c:forEach var="account" items="${accountList}">
            <tr>
                <td>${account.number}</td>
                <td>${account.accountName}</td>
                <td>${account.IBAN}</td>
                <td>
                    <fmt:parseDate value="${account.dateOfRegistration}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both"/>
                    <fmt:formatDate value="${parsedDateTime}" pattern="dd.MM.yyyy HH:mm:ss"/>
                </td>
                <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${account.balanceAmount}"/></td>
                <td rowspan="${rowspan}">
                    <c:choose>
                        <c:when test="${account.isBlocked eq 'BLOCKED'}">
                            <fmt:message key='account.table.blockedStatus.blocked'/>
                        </c:when>
                        <c:when test="${account.isBlocked eq 'APPROVAL'}">
                            <fmt:message key='account.table.blockedStatus.approval'/>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key='account.table.blockedStatus.active'/>
                        </c:otherwise>
                    </c:choose>
                        <%--                  ${account.isBlocked}--%>
                </td>
                <td class="d-flex justify-content-around">
                    <form  class="form-inline mx-1" method="get" action="<c:url value="/"/>">
                        <input name="command" type="hidden" value="blockAccountAdmin">
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
                </td>
            </tr>

        </c:forEach>
    </table>
</div>
</body>