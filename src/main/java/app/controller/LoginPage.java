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

        out.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");

        out.println("<style>");

        out.println("* { margin:0; padding:0; box-sizing:border-box; }");

        out.println("body {");
        out.println("font-family: Inter, sans-serif;");
        out.println("background: linear-gradient(135deg, #0f172a, #1e3c72);");
        out.println("display:flex;");
        out.println("align-items:center;");
        out.println("justify-content:center;");
        out.println("height:100vh;");
        out.println("color:#e2e8f0;");
        out.println("}");

        out.println(".login-card {");
        out.println("background: rgba(15, 23, 42, 0.85);");
        out.println("backdrop-filter: blur(12px);");
        out.println("padding:40px;");
        out.println("border-radius:20px;");
        out.println("width:380px;");
        out.println("text-align:center;");
        out.println("border:1px solid rgba(255,255,255,0.08);");
        out.println("box-shadow:0 20px 50px rgba(0,0,0,0.45);");
        out.println("}");

        out.println("h1 { color:#ffffff; margin-bottom:20px; font-size:1.8rem; }");

        out.println("input {");
        out.println("width:100%;");
        out.println("padding:10px 12px;");
        out.println("margin:10px 0;");
        out.println("border-radius:10px;");
        out.println("border:1px solid rgba(255,255,255,0.08);");
        out.println("background:#1e293b;");
        out.println("color:#fff;");
        out.println("outline:none;");
        out.println("}");

        out.println("input:focus {");
        out.println("border-color:#38bdf8;");
        out.println("box-shadow:0 0 0 4px rgba(56,189,248,0.15);");
        out.println("}");

        out.println("button {");
        out.println("width:100%;");
        out.println("padding:12px;");
        out.println("border:none;");
        out.println("border-radius:12px;");
        out.println("cursor:pointer;");
        out.println("font-weight:600;");
        out.println("background: linear-gradient(to right, #38bdf8, #6366f1);");
        out.println("color:white;");
        out.println("transition:0.3s ease;");
        out.println("}");

        out.println("button:hover {");
        out.println("transform: translateY(-2px);");
        out.println("box-shadow:0 10px 25px rgba(56,189,248,0.25);");
        out.println("}");

        out.println(".error {");
        out.println("background: rgba(239,68,68,0.15);");
        out.println("color:#ef4444;");
        out.println("padding:10px;");
        out.println("border-radius:10px;");
        out.println("margin-bottom:12px;");
        out.println("border:1px solid rgba(239,68,68,0.3);");
        out.println("font-size:0.85rem;");
        out.println("}");

        out.println(".info {");
        out.println("background: rgba(56,189,248,0.08);");
        out.println("color:#38bdf8;");
        out.println("padding:10px;");
        out.println("border-radius:10px;");
        out.println("margin-bottom:12px;");
        out.println("border:1px solid rgba(56,189,248,0.2);");
        out.println("font-size:0.85rem;");
        out.println("}");

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
        out.println("</form>");

        out.println("</div></body></html>");
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
