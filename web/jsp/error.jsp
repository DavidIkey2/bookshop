<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error Page</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<jsp:include page="header.jsp" />
    <c:if test="${not empty requestScope.result}">
        <h3>${requestScope.result}</h3>
    </c:if>

    <!-- Optional: Invalidate session using JSTL -->
    <c:if test="${not empty sessionScope}">
        <c:remove var="cart" scope="session" />
        <c:remove var="books" scope="session" />
    </c:if>
    <jsp:include page="footer.jsp" />
</body>
</html>
