/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mkobayashi
 */
public class ProductDetails extends HttpServlet {

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
            /*
             * Including first part of details page in product_details.html
             */
            request.getRequestDispatcher("product_details.html").include(request, response);
            
            // Get the pid from the request
            String pid = request.getParameter("pid");
            
            // Query database for product information
            DatabaseConnection dbc = new DatabaseConnection(request, response);
            Connection connection = dbc.connect();
            Statement stmt = null;
            try {
                stmt = connection.createStatement();
                String sql = "SELECT name, price, description, slide1,"
                        + " slide2, slide3 FROM products WHERE "
                        + "pid = " + pid;
                ResultSet rs = stmt.executeQuery(sql);
                
                int loopCheck = 0;
                while(rs.next()){
                    // Retrieve by column name
                    if (loopCheck == 0) {
                        String name = rs.getString("name");
                        float price = rs.getFloat("price");
                        String description = rs.getString("description");
                        String slide1 = rs.getString("slide1");
                        String slide2 = rs.getString("slide2");
                        String slide3 = rs.getString("slide3");

                        out.println("<img class=\"mySlides\" src=\"" + slide1 + "\"style=\"width:100%; height:400px\">");
                        out.println("<img class=\"mySlides\" src=\"" + slide2 + "\"style=\"width:100%; height:400px\">");
                        out.println("<img class=\"mySlides\" src=\"" + slide3 + "\"style=\"width:100%; height:400px\">");
                    
                        request.getRequestDispatcher("product_details2.html").include(request, response);
                        
                        out.println("<p style=\"position: relative;bottom:20px;font-size: 300%;color:#DDE5F9;text-shadow: 2px 2px #000000\">" + name);
                        out.println("<br> <a style=\"position: relative;bottom:20px;font-size:30px\"> Price: $" + price + "</a></p>");
                        out.println("<p style=\"position: relative;bottom:70px;font-size: 105%;color:#DDE5F9;text-shadow: 1px 1px #000000\">" + description + "</p>");
                        
                        out.println("<button style=\" border-radius: 10px;font-size:200%;position: relative;bottom:40px;background-color:#204060\"onclick=\"addToCart(" + pid + ")\" class=\"w3-btn\">Add To Cart</button>");
                        request.getRequestDispatcher("product_details3.html").include(request, response);
                        
                        ++loopCheck;
                    }
                }
                
                // Added these two lines
//                stmt.close();
//                rs.close();
                connection.close();
            }
            catch (SQLException e) {
                out.println("<p>SQLException thrown</p>");
            }
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
