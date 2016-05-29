<%-- 
    Document   : orderdetails
    Created on : May 28, 2016, 3:38:07 PM
    Author     : mkobayashi
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*,java.sql.*,java.text.*"%>
<%@ page import="exoticfish.DatabaseConnection" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Fish Forever - Order Details</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- custom CSS -->
        <link rel="stylesheet" href="css/main.css" type="text/css">

        <!-- jQuery CDN -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

        <!-- Javascript for Ajax call -->
        <script src="js/zipcode_ajax.js"></script>
        
        <!-- Not sure what this is for -->
        <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
        <link href='https://fonts.googleapis.com/css?family=Playfair+Display:400,700' rel='stylesheet' type='text/css'>
    </head>
    
    <body style="background-color:rgba(0, 255, 0, 0)">
    <style>  
        html {
            background: url("img/altBackground1.png"); 
            background-size:100% auto;
            background-repeat:no-repeat;
            background-attachment: fixed;
        }
        table, th, td {
            border-collapse: collapse;
            background-color:rgba(0, 0, 0, 0);
        }
        input {
            width: 250px;display: block;
        }
        th, td {
            margin: 5px;
            text-align: left;    
        }
        formB {
            border-radius: 5px;
            background-color: #f2f2f2;
            margin: 40px;
        }
        input[type=text], select {
            width: 250px;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .mySlides {display:none}
        .w3-left, .w3-right, .w3-badge {cursor:pointer}
        .w3-badge {height:13px;width:13px;padding:0}
        img {
            border-radius: 50px;
        }
    </style>

    <section style="font-family:Arial, Helvetica, sans-serif;" id="top-navbar">
        <ul style="position:relative;bottom:5px;font-size:110%;padding:default">
            <li><a href="home.html">Home</a></li>
            <li><a href="home.html#about">About Us</a></li>
            <li><a href="store.html">Store</a></li>
            <li style="margin:0;padding:5px 15px 0px 15px; display:inline; width:5em;float:right"id="logo-img-li" ><a style="width:120%;"href="home.html"><img class="rotate" style="height:6%;" id="logo-img" src="img/fish-logo.png"/></a></li>
            <li class="right">
               <h1 style="font-weight:bold;font-size:150%">Fish Forever</h1>
            </li>
        </ul>
    </section>
    
    <div class="confirmation">
        <p class="confirm-title">Order Details</p>
        <p class="confirm-tagline">Thank you for your order! Here are the order details:</p>
        <ul>
    <%
        String orderId = request.getParameter("oid");
        
        String[] fish = {
            "Pipefish", "Discus", "Frags", "Octopus", "Seahorse", "Plecostomus",
            "TorchCoral", "SeaUrchin", "Pufferfish", "Flowerhorn", 
            "SoftCoral", "Sponge"
        };
        
        // Query database for product information
        DatabaseConnection dbc = new DatabaseConnection(request, response);
        Connection connection = dbc.connect();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String sql = "SELECT * FROM purchase_info WHERE id = " + 
                orderId;
            ResultSet rs = stmt.executeQuery(sql);

            int loopCheck = 0;
            while(rs.next()){
                // Retrieve by column name
                if (loopCheck == 0) {
                    String name = rs.getString("FullName");
                    String address = rs.getString("Addr");
                    String city = rs.getString("Cityinfo");
                    String state = rs.getString("State");
                    Integer zip = rs.getInt("Zip");
                    Integer phone = rs.getInt("Phone");
                    Integer creditcard = rs.getInt("Creditcard");
                    Float shipping = rs.getFloat("Shipping");
                    
                    Map<String, Integer> quantity = new LinkedHashMap<String, Integer>();
                    for (int i = 0; i < 12; i++) {
                        quantity.put(fish[i], rs.getInt(fish[i]));
                    }
                    
                    // DEBUG
//                    out.println("<p class=\"confirm-tagline\">" + 
//                            quantity + "</p>"); 
                    
                    out.println("<li>Name: " + name + "</li>");
                    out.println("<li>Address: " + address + "</li>");
                    out.println("<li>City: " + city + "</li>");
                    out.println("<li>State: " + state + "</li>");
                    out.println("<li>Zip: " + Integer.toString(zip) + "</li>");
                    out.println("<li>Phone Number: " + Integer.toString(phone) + "</li>");
                    out.println("<li>Credit Card: " + Integer.toString(creditcard) + "</li>");
                    DecimalFormat decimalFormat = new DecimalFormat("'$'0.00");
                    String shipCost= decimalFormat.format(shipping);
                    out.println("<li>Shipping Cost: " + shipCost + "</li>");
                    
                    Iterator it = quantity.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        if (((Integer)pair.getValue()) > 0) {
                            out.println("<li>" + pair.getKey() + ": " + 
                                    Integer.toString((Integer)pair.getValue()) + "</li>");
                        }
                        it.remove(); // avoids a ConcurrentModificationException
                    }
                    
                    ++loopCheck;
                }
            }

            connection.close();
        }
        catch (SQLException e) {
            out.println("<p>SQLException thrown</p>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
    %>
        </ul>
    </div>
    </body>
</html>