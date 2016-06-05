/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.text.DecimalFormat;
/**
 *
 * @author Howard
 */
public class checkout extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public String formatDecimal(float number) {
        float epsilon = 0.004f; // 4 tenths of a cent
            if (Math.abs(Math.round(number) - number) < epsilon) {
                return String.format("%10.0f", number); // sdb
            } else {
                return String.format("%10.2f", number); // dj_segfault
            }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            request.getRequestDispatcher("checkout1.html").include(request, response);

            //Request session to use for accessing items in the cart
            HttpSession session = request.getSession(true);
//the cart attribute in the session named "cart".
//It's a Map object with the pid as the key and the value is the quantity
 //data type mapping is <Integer, Integer>
            
            
            Map<Integer, Integer> cart = (LinkedHashMap<Integer, Integer>)session.getAttribute("cart");
            
            Map<String,Integer> cart_list = new HashMap(); //map of item names from the cart and their quantity values
            Map<String, Float> item_price = new HashMap(); //map of item name as key and item price as value

            if (cart == null) {
                
                out.println("<p style=\"bottom:20px;font-size: 200%;color:#DDE5F9;text-shadow: 2px 2px #000000\">The cart is empty.</p>\n");
                cart = new LinkedHashMap<>();
            }
            
            else {
                try {
                    out.println("<div style=\"bottom:20px;font-size: 200%;color:#DDE5F9;text-shadow: 2px 2px #000000\">Items in the cart:</div>\n");
                    DatabaseConnection dbc = new DatabaseConnection(request, response);
                    Connection conn = dbc.connect();

                    //execute sql statements for each pid in session


                    for (Map.Entry<Integer,Integer> entry: cart.entrySet()) {
                        Integer pid = entry.getKey();

                        Integer quantity = entry.getValue();
                        //TODO: finish querying the database

                        try {
                            Statement stmt = conn.createStatement();
                            String sql;
                            sql = "SELECT name, price FROM products WHERE pid = " + pid;
                            
                            ResultSet rs = stmt.executeQuery(sql);

                            //Extract data from set
                            while (rs.next()) {
                                //retrieve by column name
                                String name = rs.getString("name");
                                name = name.replaceAll("\\s","");
                                float price = rs.getFloat("price");

                                cart_list.put(name, quantity);
                                item_price.put(name, price);
                            }

                            rs.close();
                            stmt.close();
                        }
                        catch (SQLException e) {
                            // do something with the error
                            out.println("<p>SQL Exception</p>");
                        }
                    }
                    conn.close();
                }
                catch (SQLException e) {
                    out.println("<p>SQL Exception</p>");
                }
                

            }
            
            //cart_list(name,quantity) and item_price(name,price)
            Integer total_quantity = 0;
            Float total_price = 0.0f;
            
            for (Map.Entry<String,Integer> entry: cart_list.entrySet()) {
                String name = entry.getKey();
                Integer quantity = entry.getValue();
                //calculate the total price using quantity
                total_price += item_price.get(name) * quantity;
                
                total_quantity += quantity;
                switch (name) {
                    case "Pipefish":
                    out.println("<div style=\"bottom:20px;font-size: 150%;color:#DDE5F9;text-shadow: 2px 2px #000000\">Pipefish: " + 
                        quantity +
                        "</div>\n"
                    );
//Extra credit: does not work yet                    
//                         out.println("<div style=\"bottom:20px;font-size: 150%;color:#DDE5F9;text-shadow: 2px 2px #000000\">Pipefish: "
//                                 + "<select name=\"quantity\" id=\"quantity\" onchange=\"calculatePrice();addQuantity(value)\" form=\"orderForm\">\n" +
// "               <option value=\"1\" selected>1</option>\n" +
// "               <option value=\"2\">2</option>\n" +
// "               <option value=\"3\">3</option>\n" +
// "               <option value=\"4\">4</option>\n" +
// "               <option value=\"5\">5</option>\n" +
// "               <option value=\"6\">6</option>\n" +
// "               <option value=\"7\">7</option>\n" +
// "               <option value=\"8\">8</option>\n" +
// "               <option value=\"9\">9</option>\n" +
// "            </select>" + "</div>\n");
                        
                        break;
                    case "Discus":
                        out.println("<div style=\"bottom:20px;font-size: 150%;color:#DDE5F9;text-shadow: 2px 2px #000000\">Discus: " + 
                            quantity +
                            "</div>\n"
                        );
                        break;
                    case "Frags":
                        out.println("<div style=\"bottom:20px;font-size: 150%;color:#DDE5F9;text-shadow: 2px 2px #000000\">Frags: " + 
                            quantity +
                            "</div>\n"
                        );
                        break;
                    case "Octopus":
                        out.println("<div style=\"bottom:20px;font-size: 150%;color:#DDE5F9;text-shadow: 2px 2px #000000\">Octopus: " + 
                            quantity +
                            "</div>\n"
                        );
                        break;
                    case "Seahorse":
                        out.println("<div style=\"bottom:20px;font-size: 150%;color:#DDE5F9;text-shadow: 2px 2px #000000\">Seahorse: " + 
                            quantity +
                            "</div>\n"
                        );
                        break;
                    case "Plecostomus":
                        out.println("<div style=\"bottom:20px;font-size: 150%;color:#DDE5F9;text-shadow: 2px 2px #000000\">Plecostomus: " + 
                            quantity +
                            "</div>\n"
                        );
                        break;
                    case "TorchCoral":
                        out.println("<div style=\"bottom:20px;font-size: 150%;color:#DDE5F9;text-shadow: 2px 2px #000000\">Torch Coral: " + 
                            quantity +
                            "</div>\n"
                        );
                        break;
                    case "SeaUrchin":
                        out.println("<div style=\"bottom:20px;font-size: 150%;color:#DDE5F9;text-shadow: 2px 2px #000000\">Sea Urchin: " + 
                            quantity +
                            "</div>\n"
                        );
                        break;
                    case "Eel":
                        out.println("<div style=\"bottom:20px;font-size: 150%;color:#DDE5F9;text-shadow: 2px 2px #000000\">Eel: " + 
                            quantity +
                            "</div>\n"
                        );
                        break;
                    case "Flowerhorn":
                        out.println("<div style=\"bottom:20px;font-size: 150%;color:#DDE5F9;text-shadow: 2px 2px #000000\">Flowerhorn: " + 
                            quantity +
                            "</div>\n"
                        );
                        break;
                    case "SoftCoral":
                        out.println("<div style=\"bottom:20px;font-size: 150%;color:#DDE5F9;text-shadow: 2px 2px #000000\">Soft Coral: " + 
                            quantity +
                            "</div>\n"
                        );
                        break;
                    case "Sponge":
                        out.println("<div style=\"bottom:20px;font-size: 150%;color:#DDE5F9;text-shadow: 2px 2px #000000\">Sponge: " + 
                            quantity +
                            "</div>\n"
                        );
                        break;
                }
                
              
            }
            
            
            request.getRequestDispatcher("checkout2.html").include(request, response);
            
            String temp_price = String.format("%.2f",total_price);
           
           
            total_price = Float.parseFloat(temp_price);
            
            
            
            
            out.println(
                    "<tr>\n" +
"                      <td style=\"bottom:20px;font-size: 100%;color:#DDE5F9;text-shadow: 2px 2px #000000\">State/Province/Region:<br>\n" +
"                        <input type=\"text\" id=\"state\" name=\"state\"><br>\n" +
"                      </td>\n" +
"                      <td>\n" +
"                        <h1 style=\"font-size:20px; color:black\"</h1>\n" +
"                        \n" +
"                        <div type=\"text\" style=\"font-size:125%;position:relative;bottom:20px;color:#DDE5F9;text-shadow: 2px 2px #000000\"  id=\"quantity\" value=\"" +
                            total_quantity +
                            "\">Total Quantity: " +
                            total_quantity +
                            "</div>\n" +
"                      </td>" +
"                    </tr>\n" +
"                    <tr>\n" +
"                      <td style=\"bottom:20px;font-size: 100%;color:#DDE5F9;text-shadow: 2px 2px #000000\">ZIP:<br>\n" +
"                        <input type=\"text\" id='zip' name='zip' value=''>\n" +
"                      </td>\n" +
"                      <td>\n" +
"                        <h1 style=\"font-size:20px; color:black\"</h1>\n" +
"                        <div type=\"text\" id=\"price\" value=\"" +
                            total_price +
                            "\" > </div>\n" +
"                        <div style=\"font-size:175%;position:relative;bottom:20px;color:#DDE5F9;text-shadow: 2px 2px #000000\" type=\"total\" id=\"total\" >Total: $" +
                            total_price +
                            "</div>\n" +
"                        <button type=\"submit\" form=\"orderForm\" style=\"font-size:150%;width:20%;background-color:#204060;position:bottom;bottom:30px;left:450px;\" class=\"w3-btn\">Buy</button>\n" +
//without onclick below
//                            "                        <button type=\"submit\" form=\"orderForm\" style=\"font-size:150%;width:20%;background-color:#204060;position:bottom;bottom:30px;left:450px;\" \"class=\"w3-btn\">Buy</button>\n" +
                            "                      </td>\n" +
"                    </tr>"
                  
                    
            );
            request.getRequestDispatcher("checkout3.html").include(request, response);
            
            
            
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
