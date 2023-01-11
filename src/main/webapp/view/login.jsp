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
<div class="container mt-4" style="width: 20em; border: 2px solid #999; border-radius: 5px">
    <form method="post" action="<c:url value="/"/>"  class="form-group">
        <input name="command" type="hidden" value="login">
        <label for="email" class="form-label">Email</label>
        <input type="email" name="email" class="form-control"
               id="email">
        <br/>
        <label for="password" >Password</label>
        <input type="password" autocomplete="off" name="password" class="form-control"
               id="password">
        <br/>
        <input type="submit" class="btn btn-info" value="Log in">
    </form>
</div>
</body>
</html>
