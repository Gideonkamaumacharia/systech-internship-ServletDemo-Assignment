package app.action;


import app.bean.UserBean;
import app.model.User;
import app.security.ShowroomIdentityStore;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginPage", urlPatterns = {"/login"})
public class LoginPage extends HttpServlet {

    @EJB
    UserBean userBean;

    @Inject
    ShowroomIdentityStore identityStore;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Just forward to the JSP — it reads error/msg/dest params itself
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("uname");
        String password = req.getParameter("pass");

        if (username == null || username.isBlank() || password == null) {
            resp.sendRedirect(req.getContextPath() + "/login?error=missing");
            return;
        }

        // Validate directly — no container involved
        User user = identityStore.validate(username, password);

        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("userAuthenticated", true);
            session.setAttribute("activeUser", user);
            session.setAttribute("UserActualName", user.getUsername());

            System.out.println("=== Login SUCCESS: " + user.getUsername()
                    + " role: " + user.getRole().name());

            String dest = req.getParameter("dest");
            if (dest == null || dest.isBlank()) dest = "/home";
            resp.sendRedirect(req.getContextPath() + dest);

        } else {
            System.out.println("=== Login FAILED for: " + username);
            resp.sendRedirect(req.getContextPath() + "/login?error=invalid");
        }
    }
}