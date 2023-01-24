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
      <a href="<c:url value="/"/>" class="navbar-brand"> <fmt:message key='header.home'/> </a>
    </div>
    <c:choose>
      <c:when test="${sessionScope.user == null}">
      </c:when>
      <c:otherwise>
        <c:if test="${sessionScope.user.isBlocked == 'ACTIVE'}">
          <form class="form-inline mx-2" action="<c:url value="/"/>" method="get">
            <input name="command" type="hidden" value="getAccounts">
            <input name="sortType" type="hidden" value="byNumber">
            <button class="btn btn-primary mt-3" type="submit">
              <fmt:message key='header.myAccounts'/>
            </button>
          </form>
          <form class="form-inline mx-2" action="<c:url value="/"/>" method="get">
            <input name="command" type="hidden" value="getPayments">
            <button class="btn btn-primary mt-3" type="submit">
              <fmt:message key='header.myPayments'/>
            </button>
          </form>
          <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <div class="mx-auto">
              <form class="form-inline mx-2" action="<c:url value="/"/>" method="get">
                <input name="command" type="hidden" value="getUsersAdmin">
                <button class="btn btn-primary mt-3" type="submit">
                  <fmt:message key='header.adminUsers'/>
                </button>
              </form>
            </div>
          </c:if>
        </c:if>
      </c:otherwise>
    </c:choose>
    <form class="ml-auto form-inline mx-2 mt-3" action="<c:url value="/"/>" method="get">
      <input name="command" type="hidden" value="changeLanguage">
      <select class="form-select" id="language" name="language" onchange="this.form.submit()">
        <option value="en"
        ${"en" eq language ? 'selected' : ''}
        >English</option>
        <option value="ukr"
        ${"ukr" eq language ? 'selected' : ''}
        >Українська</option>
      </select>
    </form>
    <c:choose>
      <c:when test="${sessionScope.user == null}">
        <div class="navbar-nav">

          <a class="btn btn-primary mx-2" href="<c:url value="/view/login.jsp"/>">
            <fmt:message key='header.login'/></a>

          <a class="btn btn-primary mx-2" href="<c:url value="/view/registration.jsp"/>">
            <fmt:message key='header.registration'/></a>
        </div>
      </c:when>
      <c:otherwise>
        <div class="mx-2"><fmt:message key='header.loggedAs'/>: <b><i>${sessionScope.user.name}</i></b></div>
        <form  class="form-inline mx-2" action="<c:url value="/"/>" method="get">
          <input name="command" type="hidden" value="logOut">
          <button class="btn btn-primary mt-3" type="submit">
            <fmt:message key='header.logOut'/>
          </button>
        </form>
      </c:otherwise>
    </c:choose>
  </nav>
</header>