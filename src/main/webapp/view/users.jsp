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
    <h3 class="text-center">Users</h3>
    <hr>
    <table class="table table-bordered align-middle">
        <tr>
            <th>User name</th>
            <th>User email</th>
            <th>Phone number</th>
            <th>Role</th>
            <th>Blocked status</th>
            <th></th>
<%--            <th class="d-flex justify-content-center right-border">--%>
<%--                <form action="<c:url value="/"/>" method="get">--%>
<%--                    <input name="command" type="hidden" value="getAccounts">--%>
<%--                    <label for="sortSelect">Sorting by:</label>--%>
<%--                    <select class="form-select" id="sortSelect" name="accountSortType" onchange="this.form.submit()">--%>
<%--                        &lt;%&ndash;@elvariable id="accountSortTypes" type="java.util.List"&ndash;%&gt;--%>
<%--                        <c:forEach var="type" items="${accountSortTypes}" varStatus="loop">--%>
<%--                            <c:choose>--%>
<%--                                &lt;%&ndash;@elvariable id="accountSortType" type="java.lang.String"&ndash;%&gt;--%>
<%--                                <c:when test="${accountSortType == type}">--%>
<%--                                    <option value="${type}" selected = "selected">--%>
<%--                                            ${type}--%>
<%--                                    </option>--%>
<%--                                </c:when>--%>
<%--                                <c:otherwise>--%>
<%--                                    <option value="${type}">--%>
<%--                                            ${type}--%>
<%--                                    </option>--%>
<%--                                </c:otherwise>--%>
<%--                            </c:choose>--%>
<%--                        </c:forEach>--%>
<%--                    </select>--%>
<%--                </form>--%>
<%--            </th>--%>
<%--
buttons:
block/unlock
promote to admin
more
--%>

        </tr>
                <%--@elvariable id="userList" type="java.util.List"--%>
                <c:forEach var="user" items="${userList}">
                    <tr>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>${user.phoneNumber}</td>
                    <td>${user.role}</td>
                    <td>${user.isBlocked}</td>
                    <td class="d-flex justify-content-around">
                        <form  class="form-inline mx-1" action="<c:url value="/"/>" method="get">
                            <input name="command" type="hidden" value="getAccountsAdmin">
                            <input name="userId" type="hidden" value="${user.id}">
                            <button class="btn btn-sm btn-primary mt-3"
                                    type="submit">More</button>
                        </form>
                        <form  class="form-inline mx-1" method="post" action="<c:url value="/"/>">
                            <input name="command" type="hidden" value="blockUser">
                            <input name="userId" type="hidden" value="${user.id}">
                            <button class="btn btn-sm btn-warning mt-3"
                                ${(user.role eq 'ADMIN' and user.isBlocked eq 'ACTIVE')? 'disabled' : ''}
                                    type="submit">${user.isBlocked eq 'ACTIVE' ? 'Block' : 'Unblock'}</button>
                        </form>
                        <form  class="form-inline mx-1" action="<c:url value="/"/>" method="post">
                            <input name="command" type="hidden" value="promoteUser">
                            <input name="userId" type="hidden" value="${user.id}">
                            <button class="btn btn-sm btn-success mt-3"
                                ${user.role eq 'USER' ? '' : 'disabled'}
                                    type="submit">Promote</button>
                        </form>
                    </td>
                    </tr>

                </c:forEach>
    </table>
</div>
</body>