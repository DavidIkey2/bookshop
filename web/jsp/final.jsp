<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thank You</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    <h2>Online Bookstore</h2>
    <hr>
    <h3>Thank you for shopping with us.</h3>

    <c:if test="${not empty requestScope.result}">
        <table>
            <tr>
                <td>${requestScope.result}</td>
            </tr>
        </table>
    </c:if>

    <!-- Clean up session attributes -->
    <c:remove var="cart" scope="session" />
    <c:remove var="books" scope="session" />
    <jsp:include page="footer.jsp" />
</body>
</html>
