package DBWorks;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import DBWorks.DBConnection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sherry
 */
public class LoginServlet extends HttpServlet {

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
        // create session if one doesn't exist
        HttpSession session = request.getSession(true); 
        
        if ((request.getParameter("action") != null) && (request.getParameter("action").trim().equals("logout"))) {
                session.setAttribute("login", "");
		response.sendRedirect("/");
		return;
	}
        
        String username = request.getParameter("username");
	String userpasswd = request.getParameter("userpasswd");
        String query=null;
	session.setAttribute("login", "");
        session.setAttribute("message", "");
	if ((username != null) && (userpasswd != null))
        {
            if (username.trim().equals("") || userpasswd.trim().equals("")) {
		response.sendRedirect("index.html");
            }        
            else {
                query = "SELECT * FROM Person P, Employee E WHERE P.Email = '" +
                            username + "' AND P.Password = '" + userpasswd  + "'" +
                            "AND P.SSN=E.SSN AND E.Role='Manager'";
               	java.sql.ResultSet rs = DBConnection.ExecQuery(query);
		if (rs.next()) {
                    // login success for Manager
                    session.setAttribute("login", username);
                    response.sendRedirect("ManagerDashboard.jsp");
		} 
                else {
                    query = "SELECT * FROM Person P, Employee E WHERE P.Email = '" +
                            username + "' AND P.Password = '" + userpasswd  + "'" +
                            "AND P.SSN=E.SSN AND E.Role='CustRep'";
                    rs = DBConnection.ExecQuery(query);
                    if (rs.next()) {
                        // login success for Customer Representative
                        session.setAttribute("login", username);
                        response.sendRedirect("CustRepDashboard.jsp");
                    }
                    else {
                        query = "SELECT * FROM Person P, User U WHERE P.Email = '" +
                            username + "' AND P.Password = '" + userpasswd  + "'" +
                            "AND P.SSN=U.SSN";
                        rs = DBConnection.ExecQuery(query);
                        if (rs.next()) {
                            // login success for Customer Representative
                            session.setAttribute("login", username);
                            response.sendRedirect("CustomerDashboard.jsp");
                        }
                        else {
                            // username or password mistake
                            try (PrintWriter out = response.getWriter()) {
                                //session.setAttribute("message", "Username or Password is not Correct!!!");
                                //out.print("Username or Password is not Correct!!!");
                                //request.getRequestDispatcher("index.html").forward(request, response);
                                response.sendRedirect("index.html");
                            }
                        }
                    }
                }
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
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
