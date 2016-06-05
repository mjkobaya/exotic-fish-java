/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 *
 * @author Howard
 */
public class form_submit extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
                   
            HttpSession session = request.getSession(true);
            Map<Integer, Integer> cart = (LinkedHashMap<Integer, Integer>)session.getAttribute("cart");

            Map<String,Integer> cart_list = new HashMap();
            out.println("<!DOCTYPE html> <html><head></head>");

            
            if (cart == null) {
                out.println("<p style=\"bottom:20px;font-size: 200%;color:#DDE5F9;text-shadow: 2px 2px #000000\">The cart is empty. Cannot submit order details for an empty cart.</p>\n");
                cart = new LinkedHashMap<>();
            }
            
            else {
                //out.println("<p> IT REACHED THE ELSE</p>");
                DatabaseConnection dbc = new DatabaseConnection(request, response);
                Connection conn = dbc.connect();
      
                
                for (Map.Entry<Integer,Integer> entry: cart.entrySet()) {
                    Integer pid = entry.getKey();
                    Integer quantity = entry.getValue();
                    

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
                            
                        }
                        rs.close();
                        stmt.close();
                    }
                    catch (SQLException e) {
                        // do something with the error
                        out.println("<p>SQL Exception</p>");
                    }
                }
                //out.println("<p> HERE </p>");
                
                String FullName;
                String Addr;
                String Cityinfo;
                String State;
                String Zip;
                String Phone;
                String Creditcard;
                String Shipping;
                //Submit the form data to the sql database
                
                
                FullName = (String)request.getParameter("name");
                Addr = (String)request.getParameter("address");
                Cityinfo = (String)request.getParameter("city");
                //out.println("<p> STRINGS ONLY</p>");
                State = (String)request.getParameter("state");
                //out.println("<p> ATTRIBUTE: " + FullName+" " + Addr+ " " + Cityinfo+" " + State + "</p>");
                Zip = request.getParameter("zip");
                //out.println("<p> ZIP: "+ Zip +"</p>");
                Phone = request.getParameter("phone");
                //out.println("<p> PHONE: " + Phone + "</p>");
                Creditcard = request.getParameter("creditCard");
               // out.println("<p> CREDIT CARD: " + Creditcard + "</p>");
                Shipping = (String)request.getParameter("shipMethod");
                //out.println("<p> SHIPPING: " + Shipping + "</p>");
                
               // out.println("<p> AFTER getting the params: ");
                
                
                    //put map of fish and their quantities into strings
                    String items = "";
                    String quantities = "";

                    for (Map.Entry<String,Integer> entry: cart_list.entrySet()) {
                        String item_name = entry.getKey();
                        Integer amount = entry.getValue();

                        items = items + item_name + ",";
                        quantities = quantities + amount + ",";
                    }

                    //out.println("<p>AFTER FOR LOOP: " + items + " " + quantities +"</p>");
                    
                    items = items.substring(0,items.length()-1);
                    
                   
                    //out.println("<p> QUANTITY LENGTH</p>");
                    quantities = quantities.substring(0,quantities.length()-1);
                    
                    

                    //out.println("<p> BEFORE SQL STRING</p>");

                    String sql;
                    sql = "INSERT INTO purchase_info ( FullName,Addr,Cityinfo,"
                            + "State,Zip,Phone,Creditcard,Shipping," + 
                            items + 
                            ") VALUES (" +
                            "\"" +
                            FullName + 
                            "\"" +
                            "," +
                            "\""+
                            (String)Addr + 
                            "\",\"" +
                            Cityinfo + 
                            "\"" +
                            ",\"" + 
                            State + "\"," +
                            Zip + "," +
                            Phone + ",\"" +
                            Creditcard + "\"," +
                            Shipping + "," +
                            quantities + ")";
                        
                    
                   // out.println("<p> AFTER SQL: "+sql+"</p>");
                    
                try {    
                    
                    Statement stmt = conn.createStatement();
                    
                    PreparedStatement ps = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                    
                    
                    Integer inserted = ps.executeUpdate();
                    if (inserted == 0) {
                        out.println("<p>Order was not submitted successfully. Please try again another time.</p>\n");
                    } else {
                        //out.println("<p> INSERTED </p>");
                    
                        try (ResultSet rs = ps.getGeneratedKeys()) {
                            //out.println("<p> INSIDE RS</p>");
                            if (rs.next()) {
                                //out.println("<p> INSIDE THE IF</p>");
                                int oid = rs.getInt(1);
                                //out.println("<p> AFTER OID" + oid + "</p>");
                                session.setAttribute("oid",oid);
                               // out.println("<p>" + session.getAttribute("oid") + "</p>");
                               // out.println("<p> AFTER SETTING ATTRIBUTE</p>");
                                request.getRequestDispatcher("orderdetails.jsp").forward(request, response);
                            }
                        } catch (SQLException e) {
                            out.println("<p> There is an error with the ResultSet for method getGeneratedKeys().");
                        }
                    }
                    
                    
                   // out.println("</html>");
                    
              

                   
                    conn.close();
                   
                    stmt.close();

                }
                catch (SQLException e) {
                    // do something with the error
                    out.println("<p>Form SQL Exception: " + e.getMessage() + "</p>");
                }
                
            }
            out.println("</html>");
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
