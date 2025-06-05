<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
    </head>
    <body>
        <%@ page import="model.*" %>
        <%@ page import="java.util.*" %>
        <%@ page import="java.text.*" %>

        <h1>Shopping Cart Check Out</h1>

        <form method="post" action="jsp/thankyou.jsp">
            <input type="hidden" name="action" value="validate_credit">
            <table>
                <thead>
                    <tr>
                        <th colspan="2">You have selected to purchase the following items</th>
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
                        String totalOrderInDollars = dollars.format(totalCostOfOrder);
                    %>
                </tbody>
            </table>

            <p>Please input the following information.</p>

            <table>

                <tr>
                    <td>Last name:</td>
                    <td><input type="text" name="lastname" size="25"></td>
                </tr>
                <tr>
                    <td>Street:</td>
                    <td><input type="text" name="street" size="25"></td>
                </tr>
                <tr>
                    <td>City:</td>
                    <td><input type="text" name="city" size="25"></td>
                </tr>
                <tr>
                    <td>State:</td>
                    <td><input type="text" name="state" size="2"></td>
                </tr>
                <tr>
                    <td>Zip code:</td>
                    <td><input type="text" name="zipcode" size="10"></td>
                </tr>
                <tr>
                    <td>Phone #:</td>
                    <td>
                        <input type="text" name="phone" size="12">
                    </td>
                </tr>
                <tr>
                    <td>Credit Card #:</td>
                    <td><input type="text" name="card_num" size="25"></td>
                </tr>
                <tr>
                    <td>Expiration (mm/yy):</td>
                    <td>
                        <input type="text" name="expires" size="2">/
                        <input type="text" name="expires2" size="2">
                    </td>
                </tr>
                <tr>
                    <td>Order Amount $</td>
                    <td><input type="text" name="amount" value="<%= totalOrderInDollars%>"></td>
                </tr>
            </table>

            <p><input type="submit" value="Submit"></p>
        </form>
    </body>
</html>