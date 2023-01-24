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
<div class="container mt-4" style="width: 20em; border: 2px solid #999; border-radius: 5px">
    <form method="post" action="<c:url value="/"/>"  class="form-group">
        <input name="command" type="hidden" value="login">
        <label for="email" class="form-label"><fmt:message key='user.email'/></label>
        <input type="email" name="email" class="form-control" required
               id="email">
        <br/>
        <label for="password" ><fmt:message key='user.password'/></label>
        <input type="password" autocomplete="off" name="password" class="form-control" required
               id="password">
        <br/>
        <input type="submit" class="btn btn-info" value="<fmt:message key='header.login'/>">
    </form>
</div>
<c:if test="${sessionScope.error == 'badLogin'}">
    <script>
        alert("Invalid login or password input")
    </script>
    ${sessionScope.error = null}
</c:if>
</body>
</html>
