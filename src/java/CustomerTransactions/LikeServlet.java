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
public class LikeServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String likeResponse = "";
        String str="";
        //int result = 0;
        String liker = request.getParameter("liker");
        String likee = request.getParameter("likee");
        java.util.Date today = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
        String query0 = "CREATE OR REPLACE VIEW ProfilesEmails AS SELECT R.ProfileID, "
                + "P.Email, P.SSN FROM Profile R, Person P WHERE P.SSN=R.OwnerSSN";
        String query1 = "SELECT * FROM Likes L, ProfilesEmails E WHERE L.Liker=E.ProfileID AND E.Email='" + liker + "' AND Likee='" + likee + "'";
        String query3 = "SELECT ProfileID FROM ProfilesEmails WHERE Email='" + liker + "'";

        DBConnection.ExecQuery(query0);
        java.sql.ResultSet rs = DBConnection.ExecQuery(query1);
        if (rs.next()) {
            // Already liked
            likeResponse = "<p class=\"submission-error\">You already liked this profile!</p>";
        } else {
            rs = DBConnection.ExecQuery(query3);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                for (int i = 0; i < columnCount; i++) {
                    str = rs.getString("ProfileID");
                    break;
                }
            }
            String query2 = "INSERT INTO Likes VALUES ('" + str + "', '" + likee
                    + "', '" + timestamp + "')";
            DBConnection.ExecUpdateQuery(query2);
            likeResponse = "<p class=\"submission-good\">Profile liked!</p>";
        }

        response.sendRedirect("CustomerProfileInfo.jsp?id=" + likee + "&likeResponse=" + URLEncoder.encode(likeResponse, "UTF-8"));
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
            Logger.getLogger(LikeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LikeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
