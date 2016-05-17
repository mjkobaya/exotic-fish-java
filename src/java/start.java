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

/**
 *
 * @author mkobayashi
 */
public class start extends HttpServlet {

    // JDBC database URL
     static final String DB_URL = "jdbc:mysql://sylvester-mccoy-v3.ics.uci.edu/inf124grp16";
     
     //  Database credentials
     static final String USER = "inf124grp16";
     static final String PASS = "n?yUmap3";
     
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet start</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet start at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            connect(response);
        }
    }
    
    /*
     * Database connection via JDBC
     *
     */
    
    protected void connect(HttpServletResponse response) 
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("Loading driver...<br/>");

            try {
                Class.forName("com.mysql.jdbc.Driver");
                out.println("Driver loaded!<br/>");
            } catch (ClassNotFoundException e) {
                out.println("Unable to load driver :(<br/>");
                throw new IllegalStateException("Cannot find the driver in the classpath!", e);
            }
            
            out.println("Connecting database...<br/>");
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
                out.println("Database connected!<br/>");
            } catch (SQLException e) {
                out.println(e.getMessage() + "<br/>");
                throw new IllegalStateException("Cannot connect the database!", e);
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
