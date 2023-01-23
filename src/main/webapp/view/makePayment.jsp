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

    <input name="command" type="hidden" value="makePayment">

    <label for="sender" class="form-label">My account</label>
    <input id="sender" list="account" name="sender" autocomplete="off" class="form-control" required>
    <datalist id="account">
      <%--@elvariable id="accountListNotBlocked" type="java.util.List"--%>
      <c:forEach var="account" items="${accountListNotBlocked}">
        <option id="${account.id}" value="${account.number}"></option>
      </c:forEach>
    </datalist>
    <br>
    <label for="receiver" class="form-label">Receiver account</label>
    <input type="text" autocomplete="off" name="receiver" class="form-control" required
           id="receiver">
    <br>
    <label for="amount" class="form-label">Amount</label>
    <input type="text"  pattern="([\d]+([.][\d]{2})?)" autocomplete="off" required name="amount" class="form-control"
           id="amount">
    <br>
    <label for="assignment" class="form-label">Assignment</label>
    <input type="text" autocomplete="off" name="assignment" class="form-control"
           id="assignment">
    <br>
    <input type="submit" class="btn btn-info" value="Make Payment">
  </form>
</div>
<c:if test="${sessionScope.error == 'badSender'}">
  <script>
    alert("Invalid number for sender's account")
  </script>
  ${sessionScope.error = null}
</c:if>
<c:if test="${sessionScope.error == 'badReceiver'}">
  <script>
    alert("Invalid number for receiver's account")
  </script>
  ${sessionScope.error = null}
</c:if>
</body>