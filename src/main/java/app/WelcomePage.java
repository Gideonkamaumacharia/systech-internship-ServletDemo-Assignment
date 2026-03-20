package com.gideon.jakartaassignment;


import jakarta.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Way 1: Implementing the Servlet Interface.
 * This is the most manual way and requires all 5 lifecycle methods.
 */
public class WelcomePage implements Servlet {

    private ServletConfig config;

    // 1. Lifecycle: Called ONCE when the servlet is created
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.config = servletConfig;
        System.out.println("DEBUG: WelcomePage Servlet => init() called. Lifecycle Started.");
    }

    // 2. The Core: Called for EVERY request from the browser
    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {

        System.out.println("DEBUG: WelcomePage Servlet => service() called. Generating HTML.");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Car Showroom - Home</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', Arial; margin: 0; background-color: #f0f2f5; }");
        out.println(".hero { background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%); color: white; padding: 60px; text-align: center; }");
        out.println(".container { padding: 40px; max-width: 800px; margin: auto; background: white; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); margin-top: -30px; }");
        out.println("h1 { margin: 0; font-size: 2.5em; }");
        out.println(".nav-links { margin-top: 20px; }");
        out.println(".nav-links a { color: #1e3c72; text-decoration: none; font-weight: bold; margin: 0 15px; border-bottom: 2px solid transparent; }");
        out.println(".nav-links a:hover { border-bottom: 2px solid #1e3c72; }");
        out.println("</style>");
        out.println("</head>");

        out.println("<body>");
        out.println("<div class=\"hero\">");
        out.println("<h1>Elite Car Showroom</h1>");
        out.println("<p>Precision Engineering & Design Patterns</p>");
        out.println("</div>");

        out.println("<div class=\"container\">");
        out.println("<h2>Welcome to the Portfolio</h2>");
        out.println("<p>This page is built by <b>implementing the Servlet Interface</b>. It demonstrates the full manual control of the Jakarta EE lifecycle.</p>");

        out.println("<div class=\"nav-links\">");
        out.println("<a href=\"./\">Home (Interface)</a>");
        out.println("<a href=\"specs\">Specs (Generic)</a>");
        out.println("<a href=\"inventory\">Inventory (HTTP)</a>");
        out.println("</div>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }

    // 3. Configuration: Returns the config object provided during init
    @Override
    public ServletConfig getServletConfig() {
        return this.config;
    }

    // 4. Metadata: Optional information for the server logs/admin tools
    @Override
    public String getServletInfo() {
        return "WelcomePage Servlet v1.0 - Manual Implementation";
    }

    // 5. Lifecycle: Called ONCE when the server stops or redeploys
    @Override
    public void destroy() {
        System.out.println("DEBUG: WelcomePage Servlet => destroy() called. Cleaning up resources.");
    }
}