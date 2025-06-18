<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Online Bookshop</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <jsp:include page="header.jsp" />
    <h1>Items in your Shopping Cart</h1>
    <table>
        <thead>
            <tr>
                <th>Item</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty sessionScope.cart}">
                    <c:set var="totalCost" value="0" />
                    <c:forEach var="entry" items="${sessionScope.cart}">
                        <c:set var="item" value="${entry.value}" />
                        <tr>
                            <td>${item}</td>
                        </tr>
                        <c:set var="totalCost" value="${totalCost + item.orderCost}" />
                    </c:forEach>
                    <tr>
                        <td>Order Total: $<fmt:formatNumber value="${totalCost}" type="number" minFractionDigits="2" /></td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td>No Items in Cart</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
    <hr>

    <h2>Welcome to the Online Book Store</h2>
    <form name="form1" method="post" action="./books">
        <input type="hidden" name="action" value="add_to_cart">
        <table>
            <thead>
                <tr>
                    <th>ISBN</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Add</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="book" items="${sessionScope.books}">
                    <tr>
                        <td>${book.isbn}</td>
                        <td>${book.title}</td>
                        <td>${book.author}</td>
                        <td>${book.dollarPrice}</td>
                        <td>
                            <select name="${book.isbn}" size="1">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                            </select>
                        </td>
                        <td>
                            <div align="center">
                                <input type="checkbox" name="add" value="${book.isbn}" />
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="6">
                        <input type="submit" name="Details" value="Add to Cart">
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
    <div class="link-container">
        <p><a href="./books?action=view_cart">View Shopping Cart</a></p>
    </div>
    <jsp:include page="footer.jsp" />
</body>
</html>
