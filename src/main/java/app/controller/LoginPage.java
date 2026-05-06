package app.controller;

import app.bean.UserBean;
import app.dao.GenericDao;
import app.model.User;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginPage",
            urlPatterns = {"/login"})
public class LoginPage extends HttpServlet {

    @EJB
    UserBean userBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><head><title>Login | Elite Showroom</title>");
        out.println("<style>body{font-family:Inter,sans-serif; background:#f0f2f5; display:flex; align-items:center; justify-content:center; height:100vh; margin:0;}");
        out.println(".login-card{background:white; padding:40px; border-radius:12px; box-shadow:0 10px 25px rgba(0,0,0,0.1); width:350px; text-align:center;}");
        out.println("input{width:100%; padding:12px; margin:10px 0; border:1px solid #ddd; border-radius:6px; box-sizing:border-box;}");
        out.println("button{width:100%; background:#1e3c72; color:white; border:none; padding:12px; border-radius:6px; cursor:pointer; font-weight:bold;}");
        out.println(".error { background:#ffe6e6; color:#cc0000; padding:10px; border-radius:6px; margin-bottom:12px; }");
        out.println(".info { background:#eef7ff; color:#0b5394; padding:10px; border-radius:6px; margin-bottom:12px; }");
        out.println("</style></head>");

        out.println("<body><div class='login-card'>");
        out.println("<h1>Showroom Access</h1>");
        String destination = req.getParameter("dest");
        if (destination == null) destination = "home";

        String error = req.getParameter("error");
        if ("missing".equals(error)) {
            out.println("<div class='error'>Please provide both username and password.</div>");
        } else if ("invalid".equals(error)) {
            out.println("<div class='error'>Invalid username or password. Please try again.</div>");
        } else if ("locked".equals(error)) {
            out.println("<div class='error'>Your account is locked. Contact the administrator.</div>");
        } else {
            String msg = req.getParameter("msg");
            if (msg != null && !msg.isBlank()) {
                out.println("<div class='info'>" + escapeHtml(msg) + "</div>");
            }
        }

        out.println("<form action='login' method='POST'>");
        out.println("<input type='hidden' name='redirectPath' value='" + destination + "'>");

        out.println("<input type='text' name='uname' placeholder='Enter Username' required>");
        out.println("<input type='password' name='pass' placeholder='Enter System Password' required>");
        out.println("<button type='submit'>Enter Showroom</button>");
        out.println("</form></div></body></html>");
    }

    //prevent xss from echoing querry params
    private String htmlEscape(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

    private String escapeHtml(String input) {
        return htmlEscape(input);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("uname");
        String password = req.getParameter("pass");

        if (username == null || username.isBlank() || password == null) {
            resp.sendRedirect("login?error=missing");
            return;
        }

        User user = userBean.findByUsername(username);

        if(user == null){
            resp.sendRedirect("login?error=invalid");
            return;
        }

        String systemPass = user.getPassword();
        String userPass = password;

        if(systemPass != null && systemPass.equals(userPass)){
            HttpSession session = req.getSession(true);
            session.setAttribute("userAuthenticated",true);

            session.setAttribute("activeUser",user);

            session.setAttribute("UserActualName",user.getUsername());

            String redirectPath = req.getParameter("redirectPath");
            if(redirectPath == null || redirectPath.isBlank()){
                redirectPath = "home";
            }
            resp.sendRedirect(redirectPath);
        }else{
            resp.sendRedirect("login?error=invalid");
        }
    }

}
