<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Online Bookshop</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
    </head>

    <body>
        
        <h1>Items in your Shopping Cart</h1>
        <table>
            <thead>
                <tr>
                    <th>Item</th>
                </tr>
            </thead>
            <tbody>
                <%@ page import="model.*" %>
                <%@ page import="java.util.*" %>
                <%@ page import="java.text.*" %>
                <%
                    Map items = (Map) session.getAttribute("cart");
                    if (items != null) {
                        Set entries = items.entrySet();
                        Iterator iter = entries.iterator();
                        double totalCostOfOrder = 0.00;
                        Book book = null;
                        CartItem item = null;

                        while (iter.hasNext()) {
                            Map.Entry entry = (Map.Entry) iter.next();
                            item = (CartItem) entry.getValue();
                            double cost = item.getOrderCost();
                            totalCostOfOrder += cost;
                %>
                <tr>
                    <td><%= item%></td>
                </tr>
                <%
                    } // end while
                    DecimalFormat dollars = new DecimalFormat("0.00");
                    String totalOrderInDollars = (dollars.format(totalCostOfOrder));

                %>
                <tr>
                    <td>Order Total: $<%= totalOrderInDollars%></td>
                </tr>
                <%
                } else {
                %>
                <tr>
                    <td>No Items in Cart</td>
                </tr>
                <%
                    } // end else
                %>
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
                    <%
                        List books = (List) session.getAttribute("books");
                        Iterator iter = books.iterator();
                        while (iter.hasNext()) {
                            Book book = (Book) iter.next();
                            String isbn = book.getIsbn();
                            String title = book.getTitle();
                            String author = book.getAuthor();
                            String price = book.getDollarPrice();
                    %>
                    <tr>
                        <td><%= isbn%></td>
                        <td><%= title%></td>
                        <td><%= author%></td>
                        <td><%= price%></td>
                        <td>
                            <select name="<%= isbn%>" size="1">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                            </select>
                        </td>
                        <td>
                            <div align="center">
                                <input type="checkbox" name="add" value="<%= isbn%>">
                            </div>
                        </td>
                    </tr>
                    <% } // end while %>
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
        
    </body>
</html>