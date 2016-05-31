/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 *
 * @author Jonathan
 */



@WebServlet(urlPatterns = {"/product"})
public class ProductServlet extends HttpServlet {

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
        lastViewed lastV;
        
        Products p = new Products();
        Products p1 = new Products();
        
        p.name = "fish";
        p.price = "10000";
        p.fish_type = "fish type";
        p.img = "";
        
        
        p1.name="discus";
        p1.price="120";
        p1.fish_type="freshwater";
        p1.img="img/discus1";
       
        
        HttpSession session = request.getSession();
        if (session.getAttribute("lastViewed") == null) {
            session.setAttribute("lastViewed", new lastViewed());
        }
        lastV = (lastViewed) session.getAttribute("lastViewed");
        
        // database query
        // if found add to lastV
        lastV.add(p);
        lastV.add(p1);
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            response.setContentType("text/html;charset=UTF-8");
            String pid = request.getParameter("pid");
            if (pid == null) {
            // 404 page
                out.println("<h1>404</h1>");
                return;
            }
            
            // "SELECT * FROM product WHERE pid = " + pid;
            
            
            
            // FiveLastViewed flv = new FiveLastViewed();
            // flv.add(p);
            
            // for printing
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
