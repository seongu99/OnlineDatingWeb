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
public class DeleteEmployeeServlet extends HttpServlet {

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
        String ssn = request.getParameter("ssn");
        String query1 = "SELECT * FROM Employee E, User U WHERE (E.SSN=U.SSN AND E.SSN='"
                + ssn + "')";
        String query2 = "DELETE FROM Employee WHERE SSN='" + ssn + "'";
        String query3 = "DELETE FROM Person WHERE SSN='" + ssn + "'";

        java.sql.ResultSet rs = DBConnection.ExecQuery(query1);
        if (rs.next()) {
            // Employee is also a user, so only delete from Employee, not Person
            DBConnection.ExecUpdateQuery(query2);
            message = "<p class=\"submission-good\">Employee was deleted from the database.</p>";
        } else {
            // Employee is not also a user, so delete from Person table
            result = DBConnection.ExecUpdateQuery(query3);
            if (result > 0) // Employee successfully deleted
            {
                message = "<p class=\"submission-good\">Employee was deleted from the database.</p>";
            } else {
                message = "<p class=\"submission-error\">Employee does not exist!</p>";
            }

        }

        response.sendRedirect("Manager_EmployeeDelete.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
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
            Logger.getLogger(DeleteEmployeeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DeleteEmployeeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
