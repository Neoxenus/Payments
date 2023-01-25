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
    <meta charset="utf-8">
    <title>Payments</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
</head>
<body>
<div class="container mt-4" style="width: 20em; border: 2px solid #999; border-radius: 5px">
    <form method="post" action="<c:url value="/"/>"  class="form-group">
        <input name="command" type="hidden" value="register">
        <label for="username" class="form-label" ><fmt:message key='user.userName'/></label>
        <input type="text" autocomplete="off" name="username" required class="form-control"
               id="username">
        <br/>
        <label for="phone_number" class="form-label"><fmt:message key='user.phoneNumber'/></label>
        <input type="tel" autocomplete="off" name="phone_number" class="form-control"
               id="phone_number">
        <br/>
        <label for="email" class="form-label"><fmt:message key='user.email'/></label>
        <input type="email" autocomplete="off" name="email" required class="form-control"
               id="email">
        <br/>
        <label for="password" ><fmt:message key='user.password'/></label>
        <input type="text" autocomplete="off" name="password" required class="form-control"
               id="password">
        <br/>
        <input type="submit" class="btn btn-info" value="<fmt:message key='user.register'/>">
    </form>
</div>
<c:if test="${sessionScope.error == 'userExists'}">
    <script>
        alert("User with such email already exists")
    </script>
    ${sessionScope.error = null}
</c:if>
</body>
</html>
