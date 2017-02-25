/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerTransactions;

import DBWorks.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
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
public class SuggestionListServlet extends HttpServlet {

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
        String email = request.getParameter("login");
        String query = "SELECT ProfileID AS 'SuggestionList' FROM Profile WHERE ProfileID IN("
                + "SELECT Profile2 AS 'noneDateList' FROM Date WHERE Profile2 NOT IN("
                + "SELECT D.Profile2 AS 'DatelistInProfile2' FROM Date D, Person P, Profile F "
                + "WHERE P.Email='" + email + "' AND P.SSN=F.OwnerSSN AND F.ProfileID=D.Profile1) "
                + "AND Profile2 NOT IN( SELECT D.Profile2 FROM Date D, Person P,Profile F "
                + "WHERE P.Email='" + email + "' AND P.SSN=F.OwnerSSN AND F.ProfileID=D.Profile2)"
                + "UNION SELECT Profile1 AS 'noDateListinProfile1' FROM Date,Profile WHERE Profile1 NOT IN("
                + "SELECT D.Profile1 AS 'DatelistInProfile1' FROM Date D, Person P, Profile F "
                + "WHERE P.Email='" + email + "' AND P.SSN=F.OwnerSSN AND F.ProfileID=D.Profile2) "
                + "AND Profile1 NOT IN( SELECT D.Profile1 FROM Date D, Person P,Profile F WHERE "
                + "P.Email='" + email + "' AND P.SSN=F.OwnerSSN AND F.ProfileID=D.Profile1)) "
                + "AND Profile.Age >= (SELECT Profile.Age FROM Profile WHERE ProfileID IN(SELECT Datelist "
                + "FROM(SELECT D.Profile2 AS 'Datelist',D.User1Rating AS 'UserRating' "
                + "FROM Date D, Person P, Profile F WHERE P.Email='" + email + "' "
                + "AND P.SSN=F.OwnerSSN AND F.ProfileID=D.Profile1 UNION SELECT D.Profile1,D.User2Rating "
                + "FROM Date D, Person P, Profile F WHERE P.Email='" + email + "' AND P.SSN=F.OwnerSSN "
                + "AND F.ProfileID=D.Profile2 ORDER BY UserRating DESC)D) LIMIT 1)";
        
        java.sql.ResultSet rs = DBConnection.ExecQuery(query);
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

        response.sendRedirect("Customer_Suggestion.jsp?message=" + URLEncoder.encode(message, "UTF-8"));
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
            Logger.getLogger(SuggestionListServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SuggestionListServlet.class.getName()).log(Level.SEVERE, null, ex);
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
