/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBWorks;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sherry
 */
public class RegistrationServlet extends HttpServlet {

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
        HttpSession session = request.getSession(true);
        session.setAttribute("login", "");
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
        String profileID = request.getParameter("profileid");
        String age = request.getParameter("age");
        String datingAgeStart = request.getParameter("datingagestart");
        String datingAgeEnd = request.getParameter("datingageend");
        String geoRange = request.getParameter("datinggeorange");
        String gender = request.getParameter("mf");
        String hobbies = request.getParameter("hobbies");
        String height = request.getParameter("height");
        String weight = request.getParameter("weight");
        String hairColor = request.getParameter("haircolor");
        java.util.Date today = new java.util.Date();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());

        String query1 = "SELECT * FROM Person WHERE SSN='" + ssn + "'";
        String query2 = "INSERT INTO Person VALUES ('" + ssn + "','"
                + password + "','" + firstName + "','" + lastName + "','" + street
                + "','" + city + "','" + state + "','" + zipCode + "','" + email
                + "','" + phone + "')";
        String query3 = "INSERT INTO User (SSN) VALUES ('" + ssn + "')";
        String query4 = "INSERT INTO Profile VALUES ('" + profileID + "','"
                + ssn + "','" + age + "','" + datingAgeStart + "','" + datingAgeEnd
                + "','" + geoRange + "','" + gender + "','" + hobbies + "','" + height
                + "','" + weight + "','" + hairColor + "','" + timestamp +  "','" + timestamp + "')";
        
        java.sql.ResultSet rs = DBConnection.ExecQuery(query1);
        if (rs.next()) {
            //Person already exists
            message = "<p style=\"color:red\"><i>This SSN already exists!</i></p>";
            request.setAttribute("message", message);
            request.getRequestDispatcher("Registration.jsp").forward(request, response);
        }
        else {
            int result2 = DBConnection.ExecUpdateQuery(query2);
            int result3 = DBConnection.ExecUpdateQuery(query3);
            int result4 = DBConnection.ExecUpdateQuery(query4);
            if ((result2 > 0) && (result3 > 0) && (result4 > 0)) {
                // User profile successfully created
                session.setAttribute("login", email);
                response.sendRedirect("CustomerDashboard.jsp");
            }
            else {
                message = "<p style=\"color:red\"><i>Registration error! Please double check your entries.</i></p>";
                request.setAttribute("message", message);
                request.getRequestDispatcher("Registration.jsp").forward(request, response);
            }
        }
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
            Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RegistrationServlet.class.getName()).log(Level.SEVERE, null, ex);
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
