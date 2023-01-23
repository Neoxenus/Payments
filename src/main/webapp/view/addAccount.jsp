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

        <input name="command" type="hidden" value="addAccount">

        <label for="number" class="form-label">Number</label>
        <input type="text" name="number" class="form-control" required
               id="number">
        <br/>
        <label for="account_name" class="form-label">Account name</label>
        <input type="text" name="account_name" class="form-control" required
               id="account_name">
        <br/>
        <label for="IBAN" class="form-label">IBAN</label>
        <input type="text" name="IBAN" class="form-control" required
               id="IBAN">
        <br/>
        <input type="submit" class="btn btn-info" value="Add account">
    </form>
</div>
<c:if test="${sessionScope.error == 'accountExists'}">
    <script>
        alert("Account with such number already exists")
    </script>
    ${sessionScope.error = null}
</c:if>
</body>