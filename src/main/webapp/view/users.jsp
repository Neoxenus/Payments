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
<c:if test="${sessionScope.user.role == 'ADMIN'}">
<div style="width: 80%; margin: auto">
    <h3 class="text-center">Users</h3>
    <hr>
    <table class="table table-bordered align-middle">
        <tr>
            <th><fmt:message key='user.userName'/></th>
            <th><fmt:message key='user.email'/></th>
            <th><fmt:message key='user.phoneNumber'/></th>
            <th><fmt:message key='user.role'/></th>
            <th><fmt:message key='user.blockedStatus'/></th>
            <th></th>
        </tr>
        <%--@elvariable id="userList" type="java.util.List"--%>
        <c:forEach var="user" items="${userList}">
            <tr>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.phoneNumber}</td>
                <td>
                    <c:choose>
                        <c:when test="${user.role eq 'ADMIN'}">
                            <fmt:message key='user.role.admin'/>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key='user.role.user'/>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${user.isBlocked eq 'ACTIVE'}">
                            <fmt:message key='user.blockedStatus.active'/>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key='user.blockedStatus.blocked'/>
                        </c:otherwise>
                    </c:choose>
                        <%--                  ${account.isBlocked}--%>
                </td>
                <td class="d-flex justify-content-around">
                    <form  class="form-inline mx-1" action="<c:url value="/"/>" method="get">
                        <input name="command" type="hidden" value="getAccountsAdmin">
                        <input name="userId" type="hidden" value="${user.id}">
                        <button class="btn btn-sm btn-primary mt-3"
                                type="submit"><fmt:message key='user.more'/></button>
                    </form>
                    <form  class="form-inline mx-1" method="post" action="<c:url value="/"/>">
                        <input name="command" type="hidden" value="blockUser">
                        <input name="userId" type="hidden" value="${user.id}">
                        <c:choose>
                            <c:when test="${user.isBlocked eq 'ACTIVE'}">
                                <button class="btn btn-sm btn-warning mt-3"
                                    ${(user.role eq 'ADMIN' and user.isBlocked eq 'ACTIVE')? 'disabled' : ''}
                                        type="submit"><fmt:message key='user.block'/> </button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-sm btn-warning mt-3"
                                    ${(user.role eq 'ADMIN' and user.isBlocked eq 'ACTIVE')? 'disabled' : ''}
                                        type="submit"><fmt:message key='user.unblock'/> </button>
                            </c:otherwise>
                        </c:choose>
                    </form>
                    <form  class="form-inline mx-1" action="<c:url value="/"/>" method="post">
                        <input name="command" type="hidden" value="promoteUser">
                        <input name="userId" type="hidden" value="${user.id}">
                        <button class="btn btn-sm btn-success mt-3"
                            ${user.role eq 'USER' ? '' : 'disabled'}
                                type="submit"><fmt:message key='user.promote'/></button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div class="row">
        <div class="col d-flex justify-content-center">
            <form method="get" action="<c:url value="/"/>">
                <input name="command" type="hidden" value="getUsersAdmin">
                <input name="pageCommand" type="hidden" value="previous">
                <input type="submit" value="<<" />
            </form>
            <%--@elvariable id="pageNumber" type="int"--%>
            <div class="p-1">
                ${pageNumber}
            </div>
            <form method="get" action="<c:url value="/"/>">
                <input name="command" type="hidden" value="getUsersAdmin">
                <input name="pageCommand" type="hidden" value="next">
                <input type="submit" value=">>" />
            </form>
        </div>
    </div>
</div>
</c:if>
</body>