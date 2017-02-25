/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustRepTransactions;

import DBWorks.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sherry
 */
public class RecordDateServlet extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String message = "";
        int result = 0;
        String email = request.getParameter("login");
        
        String profile1 = request.getParameter("profile1");
        String profile2 = request.getParameter("profile2");
        String ssn = request.getParameter("crssn");
        String dateTime = request.getParameter("datetime");
        String location = request.getParameter("location");
        String bookingFee = request.getParameter("bookingfee");
        String comments = request.getParameter("comments");
        String user1rating = request.getParameter("user1rating");
        String user2rating = request.getParameter("user2rating");
        
        String query1 = "SELECT * FROM Person WHERE SSN='" + ssn + "' AND Email='" +
                email + "'";
        String query2 = "UPDATE Date SET CustRep='" + ssn + "',Date_Time='" +
                dateTime + "',Location='" + location + "',BookingFee='" + bookingFee +
                "',Comments='" + comments + "',User1Rating='" + user1rating + 
                "',User2Rating='" + user2rating + "' WHERE Profile1='" + profile1 + 
                "' AND Profile2='" + profile2 + "'";
        
        java.sql.ResultSet rs = DBConnection.ExecQuery(query1);
        if (!rs.next()) {
            message = "<p class=\"submission-error\">Please enter your correct SSN!</p>";
        }
        else {
            result = DBConnection.ExecUpdateQuery(query2);
            if (result > 0) { // Date recorded
                message = "<p class=\"submission-good\">Date successfully recorded!</p>";
            }
            else { // Date not recorded
                message = "<p class=\"submission-error\">Date not recorded. Please double check your entries.</p>";
            }
        }
        
        response.sendRedirect("CustRep_RecordDates.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RecordDateServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(RecordDateServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
