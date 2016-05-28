/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mkobayashi
 */
public class AddToCart extends HttpServlet {
    
    private static final Boolean DEBUG = true;
    
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
            out.println("<title>Servlet AddToCart</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddToCart at " + request.getContextPath() + "</h1>");
            addToCart(request, response);
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    private synchronized void addToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Create session object if not already exists
        HttpSession session = request.getSession(true);
        
        try (PrintWriter out = response.getWriter()) {
            Map<Integer, Integer> cart = (LinkedHashMap<Integer, Integer>)session.getAttribute("cart");
            
            if (cart == null) {
                if (DEBUG) {
                    out.println("<p>cart is null</p>");
                }
                cart = new LinkedHashMap<>();
            }
            
            // Get pid of item to add to cart
            String sPid = request.getParameter("pid");
            Integer pid = new Integer(sPid);
            
            if (cart.containsKey(pid)) {
                cart.put(pid, cart.get(pid) + 1);
            }
            else {
                cart.put(pid, 1);
            }
            
            if (DEBUG) {
                out.println("<p>cart Elements</p>");
                out.println("<p>" + cart + "</p>");
            }
            
            session.setAttribute("cart", cart);
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
