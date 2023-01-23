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
        <div class="navbar-nav ml-auto">

          <a class="btn btn-primary mx-2" href="<c:url value="/view/login.jsp"/>">Log In</a>

          <a class="btn btn-primary mx-2" href="<c:url value="/view/registration.jsp"/>">Sign Up</a>
        </div>
      </c:when>
      <c:otherwise>
        <c:if test="${sessionScope.user.isBlocked == 'ACTIVE'}">
          <form class="form-inline mx-2" action="<c:url value="/"/>" method="get">
              <%--                <a class="btn btn-primary my-2" href="<c:url value="/view/accounts.jsp"/>">Accounts</a>--%>
            <input name="command" type="hidden" value="getAccounts">
            <input name="sortType" type="hidden" value="byNumber">

              <%--                <input name="pageNum" type="hidden" value="1">--%>
              <%--                <input name="sortType" type="hidden" value="default">--%>
            <button class="btn btn-primary mt-3" type="submit">My Accounts</button>
          </form>
          <form class="form-inline mx-2" action="<c:url value="/"/>" method="get">
              <%--                <a class="btn btn-primary my-2" href="<c:url value="/view/accounts.jsp"/>">Accounts</a>--%>
            <input name="command" type="hidden" value="getPayments">
              <%--                <input name="pageNum" type="hidden" value="1">--%>
              <%--                <input name="sortType" type="hidden" value="default">--%>
            <button class="btn btn-primary mt-3" type="submit">My Payments</button>
          </form>
          <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <div class="mx-auto">
              <form class="form-inline mx-2" action="<c:url value="/"/>" method="get">
                  <%--                <a class="btn btn-primary my-2" href="<c:url value="/view/accounts.jsp"/>">Accounts</a>--%>
                <input name="command" type="hidden" value="getUsersAdmin">
                  <%--                <input name="pageNum" type="hidden" value="1">--%>
                  <%--                <input name="sortType" type="hidden" value="default">--%>
                <button class="btn btn-primary mt-3" type="submit">Users</button>
              </form>
            </div>

          </c:if>
        </c:if>
        <form class="ml-auto form-inline mx-2 mt-3" action="<c:url value="/"/>" method="get">
          <input name="command" type="hidden" value="changeLanguage">
          <select class="form-select" id="languageSelect" name="language" onchange="this.form.submit()">
            <option value="en"
              ${"en" eq language ? 'selected' : ''}
            >En</option>
            <option value="ukr"
              ${"ukr" eq language ? 'selected' : ''}
            >Ukr</option>
          </select>
        </form>
        <div class="mx-2">Logged as: <b><i>${sessionScope.user.name}</i></b></div>
        <form  class="form-inline mx-2" action="<c:url value="/"/>" method="get">
          <input name="command" type="hidden" value="logOut">
          <button class="btn btn-primary mt-3" type="submit">Log Out</button>
        </form>
      </c:otherwise>
    </c:choose>

  </nav>
</header>