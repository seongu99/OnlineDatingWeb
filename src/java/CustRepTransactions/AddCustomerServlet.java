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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class AddCustomerServlet extends HttpServlet {

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
            throws ServletException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        String message = "";
        int result = 0;
        
        String ssn = request.getParameter("ssn");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipCode = request.getParameter("zipcode");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String ppp = request.getParameter("ppp");
        String rating = request.getParameter("rating");
        String dateOfLastAct = request.getParameter("dateoflastact");
        
        String query1 = "SELECT * FROM Person WHERE SSN = '" + ssn + "'";
        String query2 = "SELECT * FROM User WHERE SSN = '" + ssn + "'";
        String query3 = "INSERT INTO Person VALUES ('" + ssn + "','" +
                password + "','" + firstName + "','" + lastName + "','" + street +
                "','" + city + "','" + state + "','" + zipCode + "','" + email +
                "','" + phone + "')";
        String query4 = "INSERT INTO User VALUES ('" + ssn + "','" +
                ppp + "','" + rating + "','" + dateOfLastAct + "')";

        java.sql.ResultSet rs = DBConnection.ExecQuery(query1);
        if (rs.next()) {
            // Person already exists, now check User table
            rs = DBConnection.ExecQuery(query2);
            if (rs.next())
                // Customer already exists
                message = "<p class=\"submission-error\">Customer already exists!</p>";
            else { // Add customer to the database
                result = DBConnection.ExecUpdateQuery(query4);
                if (result > 0)
                    message = "<p class=\"submission-good\">Customer added to the database!</p>";
                else
                    message = "<p class=\"submission-error\">Customer not added. Please double check your entries.</p>";
            }
        }
        else {
            // Person doesn't exist. Add person.
            result = DBConnection.ExecUpdateQuery(query3);
            if (result > 0) { 
                // Person added. Now add customer
                result = DBConnection.ExecUpdateQuery(query4);
                if (result > 0)
                    message = "<p class=\"submission-good\">Customer added to the database!</p>";
                else
                    message = "<p class=\"submission-error\">Customer not added. Please double check your entries.</p>";
            }
            else {
                // Person not added
                message = "<p class=\"submission-error\">Customer not added. Please double check your entries.</p>";
            }       
        }
        
        response.sendRedirect("CustRep_CustomerAdd.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(AddCustomerServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ServletException | IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.getMessage();
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
