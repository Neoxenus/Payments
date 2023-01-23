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
            <th></th>
        </tr>
        <%--@elvariable id="showedUser" type="com.my.model.entities.User"--%>
        <tr>
            <td>${showedUser.name}</td>
            <td>${showedUser.email}</td>
            <td>${showedUser.phoneNumber}</td>
            <td>${showedUser.role}</td>
            <td>${showedUser.isBlocked}</td>
            <td class="d-flex justify-content-around">
                <form  class="form-inline mx-1" method="post" action="<c:url value="/"/>">
                    <input name="command" type="hidden" value="blockUser">
                    <input name="userId" type="hidden" value="${showedUser.id}">
                    <button class="btn btn-sm btn-warning mt-3"
                    ${(showedUser.role eq 'ADMIN' and showedUser.isBlocked eq 'ACTIVE')? 'disabled' : ''}
                            type="submit">${showedUser.isBlocked eq 'ACTIVE' ? 'Block' : 'Unblock'}</button>
                </form>
                <form  class="form-inline mx-1" action="<c:url value="/"/>" method="post">
                    <input name="command" type="hidden" value="promoteUser">
                    <input name="userId" type="hidden" value="${showedUser.id}">
                    <button class="btn btn-sm btn-success mt-3"
                        ${showedUser.role eq 'USER' ? '' : 'disabled'}
                            type="submit">Promote</button>
                </form>
            </td>
        </tr>
    </table>
    <hr>
    <table class="table table-bordered align-middle">
        <tr>
            <th>Account number</th>
            <th>Account name</th>
            <th>IBAN</th>
            <th>Date of registration</th>
            <th>Balance amount</th>
            <th>Blocked</th>
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
                <td>${account.isBlocked}</td>
                <td class="d-flex justify-content-around">
                    <form  class="form-inline mx-1" method="get" action="<c:url value="/"/>">
                        <input name="command" type="hidden" value="blockAccountAdmin">
                        <input name="accountId" type="hidden" value="${account.id}">
                        <button class="btn btn-sm btn-warning mt-3"
                                type="submit">${account.isBlocked eq 'ACTIVE' ? 'Block' : 'Unblock'}</button>
                    </form>
                </td>
            </tr>

        </c:forEach>
    </table>
</div>
</body>