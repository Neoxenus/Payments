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

<div class="container mt-4" style="width: 20em; border: 2px solid #999; border-radius: 5px">
    <form method="post" action="<c:url value="/"/>"  class="form-group">

        <input name="command" type="hidden" value="addCreditCard">
        <input name="accountId" type="hidden" value="${param.accountId}">

        <label for="number" class="form-label">Number</label>
        <input type="text" autocomplete="off" name="number" class="form-control" required
               id="number">
        <br/>
        <label for="cvv" class="form-label">cvv</label>
        <input type="text" autocomplete="off" name="cvv" class="form-control" required
               id="cvv">
        <br/>
        <label for="expire_date" class="form-label">Expire date</label>
        <input type="text" autocomplete="off" name="expire_date" class="form-control" required
               id="expire_date">
        <br/>
        <input type="submit" class="btn btn-info" value="Add Credit Card">
    </form>
</div>
<c:if test="${sessionScope.error == 'creditCardExists'}">
    <script>
        alert("Credit card with such number already exists")
    </script>
    ${sessionScope.error = null}
</c:if>
</body>
