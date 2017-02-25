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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
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
public class ProduceDatedCustomersServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     * @throws java.lang.InstantiationException
     * @throws java.lang.IllegalAccessException
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        String mysJDBCDriver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/jss";
        String username = "root";
        String password = DBConnection.getPassword();

        Class.forName(mysJDBCDriver).newInstance();
        Connection conn = DriverManager.getConnection(url, username, password);

        String message = "";
        String ssn = request.getParameter("ssn");
        int rowCount = 0;

        String statement = "SELECT R.OwnerSSN AS 'SSN' FROM Profile R, Date D " +
                "WHERE ((R.ProfileID = D.Profile1) AND D.Profile2 IN " +
                "(SELECT R.ProfileID FROM Profile R WHERE R.OwnerSSN = ?)) " +
                "OR ((R.ProfileID = D.Profile2) AND D.Profile1 IN " +
                "(SELECT R.ProfileID FROM Profile R WHERE R.OwnerSSN = ?)) " +
                "GROUP BY R.OwnerSSN";
       
        PreparedStatement query = conn.prepareStatement(statement);
        query.setString(1, ssn);
        query.setString(2, ssn);
        java.sql.ResultSet rs = query.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        // table header
        message += "<TR>";
        for (int i = 0; i < columnCount; i++) {
            message += "<TH>" + rsmd.getColumnLabel(i + 1) + "</TH>";
        }
        message += "</TR>";
        // the data
        while (rs.next()) {
            rowCount++;
            message += "<TR>";
            for (int i = 0; i < columnCount; i++) {
                message += "<TD>" + rs.getString(i + 1) + "</TD>";
            }
            message += "</TR>";
        }
        
        if (rowCount == 0)
            message = "<p class=\"submission-error\">No customers have dated the person with SSN \"" + ssn + "\".</p>";
        
        response.sendRedirect("Manager_ProduceAListofDatedCstmrs.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
        
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
        }  catch (ClassNotFoundException ex) {
            Logger.getLogger(ProduceDatedCustomersServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ProduceDatedCustomersServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ProduceDatedCustomersServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProduceDatedCustomersServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProduceDatedCustomersServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProduceDatedCustomersServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ProduceDatedCustomersServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ProduceDatedCustomersServlet.class.getName()).log(Level.SEVERE, null, ex);
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
