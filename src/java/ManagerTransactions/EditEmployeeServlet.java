/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagerTransactions;

import DBWorks.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Enumeration;
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
public class EditEmployeeServlet extends HttpServlet {

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
        
        //Enumeration<String> names = request.getParameterNames();
        String ssn = request.getParameter("ssn");
        String role = request.getParameter("role");
        String startDate = request.getParameter("startdate");
        String hourlyRate = request.getParameter("hourlyrate");
        
        String query1 = "SELECT * FROM Employee WHERE SSN = '" + ssn + "'";
        String query2 = "UPDATE Employee SET Role='" + role + "',StartDate='" +
                startDate + "',HourlyRate='" + hourlyRate + "' WHERE SSN='" + ssn + "'";
        
        String message = "";
        int result = 0;
        java.sql.ResultSet rs = DBConnection.ExecQuery(query1);
        if (rs.next()) {
            // Employee exists
            result = DBConnection.ExecUpdateQuery(query2);
            if (result > 0) 
                // Employee updated
                message = "<p class=\"submission-good\">Employee information updated!</p>";
            else
                message = "<p class=\"submission-error\">Employee not updated. Please double check your entries.</p>";
        }
        else {
            // Employee does not exist
            message = "<p class=\"submission-error\">Employee with SSN \"" + ssn + "\" does not exist!</p>";
        }
        response.sendRedirect("Manager_EmployeeEdit.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
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
            Logger.getLogger(EditEmployeeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EditEmployeeServlet.class.getName()).log(Level.SEVERE, null, ex);
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