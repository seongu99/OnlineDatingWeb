/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerTransactions;

import DBWorks.DBConnection;
import java.io.IOException;
//import java.io.PrintWriter;
import java.net.URLEncoder;
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
public class MostActiveProfilesServlet extends HttpServlet {

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
        String query1 = "CREATE OR REPLACE VIEW Daters(ProfileName) AS SELECT Profile1 " +
                "FROM Date UNION ALL SELECT Profile2 FROM Date";
        String query2 = "CREATE OR REPLACE VIEW NumDates(ProfileName,NumDates) AS" +
                " SELECT ProfileName, COUNT(ProfileName) FROM Daters GROUP BY ProfileName" +
                " ORDER BY COUNT(ProfileName) DESC";
        String query3 = "SELECT R.ProfileID AS 'Most Active Users'" +
                " FROM Profile R, NumDates N, Likes L, User U" +
                " WHERE (R.ProfileID = N.ProfileName) OR (R.ProfileID = L.Liker)" +
                " OR (R.OwnerSSN = U.SSN) GROUP BY R.ProfileID" +
                " ORDER BY 4*(N.NumDates)+2*(COUNT(L.Liker))+(1/(CURTIME()-U.DateOfLastAct))" +
                " DESC LIMIT 5";
        
        // Creates a view of all profiles that went on dates
        DBConnection.ExecQuery(query1);
        // Creates a view of the number of dates that each profile went on 
        DBConnection.ExecQuery(query2);
        
        // Queries Most Active Users by Weighted Average according to Piazza question 10
        java.sql.ResultSet rs = DBConnection.ExecQuery(query3);
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
            //rowCount++;
            message += "<TR>";
            for (int i = 0; i < columnCount; i++) {
                message += "<TD>" + rs.getString(i + 1) + "</TD>";
            }
            message += "</TR>";
        }
        
        response.sendRedirect("Customer_MostActiveProfiles.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
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
            Logger.getLogger(MostActiveProfilesServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(MostActiveProfilesServlet.class.getName()).log(Level.SEVERE, null, ex);
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
