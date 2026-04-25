package app.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginPage",
            urlPatterns = {"/login"})
public class LoginPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><head><title>Login | Elite Showroom</title>");
        out.println("<style>body{font-family:Inter,sans-serif; background:#f0f2f5; display:flex; align-items:center; justify-content:center; height:100vh; margin:0;}");
        out.println(".login-card{background:white; padding:40px; border-radius:12px; box-shadow:0 10px 25px rgba(0,0,0,0.1); width:350px; text-align:center;}");
        out.println("input{width:100%; padding:12px; margin:10px 0; border:1px solid #ddd; border-radius:6px; box-sizing:border-box;}");
        out.println("button{width:100%; background:#1e3c72; color:white; border:none; padding:12px; border-radius:6px; cursor:pointer; font-weight:bold;}</style></head>");

        out.println("<body><div class='login-card'>");
        out.println("<h1>Showroom Access</h1>");
        String destination = req.getParameter("dest");
        if (destination == null) destination = "home"; // Default backup

        out.println("<form action='login' method='POST'>");
        out.println("<input type='hidden' name='redirectPath' value='" + destination + "'>");

        out.println("<input type='password' name='pass' placeholder='Enter System Password' required>");
        out.println("<button type='submit'>Enter Showroom</button>");
        out.println("</form></div></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String systemPass = getServletContext().getInitParameter("appPassword");
        String userPass = req.getParameter("pass");

        if (systemPass.equals(userPass)) {
            // SUCCESS: Create session and mark as authenticated
            HttpSession session = req.getSession(true);
            session.setAttribute("userAuthenticated", true);
//            session.setAttribute("username", "Admin_User");
            session.setMaxInactiveInterval(60);

            String redirectPath = req.getParameter("redirectPath");
            resp.sendRedirect(redirectPath); // Redirect to the respective path
        } else {
            resp.sendRedirect("login?error=1");
        }
    }
}
