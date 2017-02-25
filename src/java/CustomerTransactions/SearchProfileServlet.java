/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerTransactions;

import DBWorks.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
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
public class SearchProfileServlet extends HttpServlet {

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
        String profileTable = "";
        int rowCount = 0;
        String id = request.getParameter("searchid");

        String query = "SELECT ProfileID FROM Profile WHERE ProfileID='" + id + "'";
        
        java.sql.ResultSet rs = DBConnection.ExecQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        // table header
        profileTable += "<TR>";
        for (int i = 0; i < columnCount; i++) {
            profileTable += "<TH>" + rsmd.getColumnLabel(i + 1) + "</TH>";
        }
        profileTable += "</TR>";
        // the data
        while (rs.next()) {
            rowCount++;
            profileTable += "<TR>";
            for (int i = 0; i < columnCount; i++) {
                String str = rs.getString(i + 1);
                profileTable += "<TD><a href=\"CustomerProfileInfo.jsp?id=" 
                        + str + "\">" + str + "</a></TD>";
            }
            profileTable += "</TR>";
        }
        
        if (rowCount == 0) {
            // No pending dates
            profileTable = "<p class=\"submission-error\">There are no profiles that match your search.</p>";
        }
        
        request.setAttribute("profileTable", profileTable);
        request.getRequestDispatcher("CustomerDashboard.jsp").forward(request, response);
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
            Logger.getLogger(SearchProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SearchProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short profileTable of the servlet.
     *
     * @return a String containing servlet profileTable
     */
    @Override
    public String getServletInfo() {
        return "Short profileTable";
    }// </editor-fold>

}
