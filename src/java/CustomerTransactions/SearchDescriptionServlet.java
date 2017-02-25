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
public class SearchDescriptionServlet extends HttpServlet {

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
        String description = "";
        int rowCount = 0;
        String d = request.getParameter("searchdescription");

        String query = "SELECT ProfileID FROM Profile WHERE Age='" + d +
                "' OR M_F='" + d + "' OR Hobbies LIKE '%" + d +
                "%' OR Weight='" + d + "' OR HairColor='" + d + "'";
        
        java.sql.ResultSet rs = DBConnection.ExecQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        // table header
        description += "<TR>";
        for (int i = 0; i < columnCount; i++) {
            description += "<TH>" + rsmd.getColumnLabel(i + 1) + "</TH>";
        }
        description += "</TR>";
        // the data
        while (rs.next()) {
            rowCount++;
            description += "<TR>";
            for (int i = 0; i < columnCount; i++) {
                String str = rs.getString(i + 1);
                description += "<TD><a href=\"CustomerProfileInfo.jsp?id=" 
                        + str + "\">" + str + "</a></TD>";
            }
            description += "</TR>";
        }
        
        if (rowCount == 0) {
            // No pending dates
            description = "<p class=\"submission-error\">There are no profiles that match your search.</p>";
        }
        
        if (d.equals("")) {
            description = "<p class=\"submission-error\">There are no profiles that match your search.</p>";
        }
        
        request.setAttribute("description", description);
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
            Logger.getLogger(SearchDescriptionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SearchDescriptionServlet.class.getName()).log(Level.SEVERE, null, ex);
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
