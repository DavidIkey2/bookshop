<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Online Bookshop</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
    </head>
    <body>
        <%@ page import="model.*" %>
        <%@ page import="java.util.*" %>
        <%@ page import="java.text.*" %>

        <h1>The following items are in your shopping cart</h1>
        <form name="form1" method="post" action="./books">
            <input type="hidden" name="action" value="update_cart">
            <table>
                <thead>
                    <tr>
                        <th>ISBN</th>
                        <th>Title</th>
                        <th>Price/unit</th>
                        <th>Quantity</th>
                        <th>Subtotal</th>
                        <th>Remove</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        Map items = (Map) session.getAttribute("cart");
                        Set entries = items.entrySet();
                        Iterator iter = entries.iterator();
                        double totalCostOfOrder = 0.00;
                        Book book = null;
                        CartItem item = null;
                        while (iter.hasNext()) {
                            Map.Entry entry = (Map.Entry) iter.next();
                            String isbn = (String) entry.getKey();
                            item = (CartItem) entry.getValue();
                            book = item.getBook();
                            String title = book.getTitle();
                            String price = book.getDollarPrice();
                            int quantity = item.getQuantity();
                            double cost = item.getOrderCost();
                            String dollarCost = item.getDollarOrderCost();
                            totalCostOfOrder += cost;
                    %>
                    <tr>
                        <td><%= isbn%></td>
                        <td><%= title%></td>
                        <td><%= price%></td>
                        <td>
                            <input type="text" name="<%= isbn%>" size="2" value="<%= quantity%>" maxlength="4">
                        </td>
                        <td><%= dollarCost%></td>
                        <td>
                            <div align="center">
                                <input type="checkbox" name="remove" value="<%= isbn%>">
                            </div>
                        </td>
                    </tr>
                    <%
                        } // end while
                        DecimalFormat dollars = new DecimalFormat("0.00");
                        String totalOrderInDollars = "ORDER TOTAL $" + dollars.format(totalCostOfOrder);
                    %>
                    <tr>
                        <td colspan="4">
                            <input type="submit" name="Submit" value="Update Cart">
                        </td>
                        <td colspan="2">
                            <div align="right"><b><%= totalOrderInDollars%></b></div>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
        <div class="link-container">
            <p><a href="./books?action=continue">Continue Shopping</a></p>
            <p><a href="./books?action=checkout">Check Out</a></p>
        </div>
    </body>
</html>