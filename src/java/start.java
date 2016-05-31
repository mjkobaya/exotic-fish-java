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
public class start extends HttpServlet {
     
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //
            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet start</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1> WORKING </h1>");
            //request.getRequestDispatcher("details2.html").include(request, response);
            //DatabaseConnection dbc = new DatabaseConnection(request, response);
            //Connection connection = dbc.connect();
            //Statement stmt = null;
            
//            Products.test(request, response);
//            String pid = request.getParameter("pid");
//            out.println("<p>Pid is: " + pid + "</p>");
//            out.println("</body>");
//            out.println("</html>");
        try {
            lastViewed lastV;
            ProductServlet prod;
            HttpSession session = request.getSession();
            if (session.getAttribute("lastViewed") == null) {
                session.setAttribute("lastViewed", new lastViewed());
            }
            lastV = (lastViewed) session.getAttribute("lastViewed");
            
            Products p1 = new Products();
            Products p2 = new Products();
            Products p3 = new Products();
            Products p4 = new Products();
            Products p5 = new Products();
            
            
            p1.name="discus";
            p1.price="120";
            p1.fish_type="freshwater";
            p1.img="img/discus1";
            
            p2.name="eel";
            p2.price="120";
            p2.fish_type="freshwater";
            p2.img="img/eel1";
            
            p3.name="flowerhorn";
            p3.price="22";
            p3.fish_type="freshwater";
            p3.img="img/flowerhorn1";
            
            p4.name="plecostomus";
            p4.price="55";
            p4.fish_type="freshwater";
            p4.img="img/plecostomus1";
            
            p5.name="frags";
            p5.price="88";
            p5.fish_type="coral";
            p5.img="img/frags1";
            
  
            
            lastV.add(p1);
            lastV.add(p2);
            lastV.add(p3);
            lastV.add(p4);
            lastV.add(p5);
            
            
            Products.printHeader(response.getWriter());
            
            Products.printLastFive(response.getWriter(), lastV);
            
            Products.createTable(response.getWriter());
            
            
            
            //template.footer(writer);
         } catch (Exception e) {}
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     *
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
