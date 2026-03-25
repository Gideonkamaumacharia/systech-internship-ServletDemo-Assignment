package app;

import jakarta.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class SpecsPage extends GenericServlet {

    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<title>Car Specs | Technical</title>");
        out.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;700&family=JetBrains+Mono&display=swap' rel='stylesheet'>");
        out.println("<style>");
        out.println("body { font-family: 'Inter', sans-serif; margin: 0; background-color: #f0f2f5; display: flex; flex-direction: column; align-items: center; padding: 50px 20px; }");

        // Technical Card
        out.println(".card { background: white; border-radius: 12px; box-shadow: 0 10px 25px rgba(0,0,0,0.05); overflow: hidden; width: 100%; max-width: 800px; }");

        // Technical Header Image (Chassis/Engine focus)
        out.println(".header-img { width: 100%; height: 250px; background-image: url('https://images.unsplash.com/photo-1486262715619-67b85e0b08d3?auto=format&fit=crop&w=1200&q=80'); background-size: cover; background-position: center; border-bottom: 4px solid #1e3c72; }");

        out.println(".content { padding: 40px; }");
        out.println("h1 { color: #1e3c72; margin-top: 0; font-size: 28px; border-bottom: 2px solid #f0f2f5; padding-bottom: 10px; }");
        out.println(".method-badge { background: #e9ecef; color: #495057; padding: 4px 12px; border-radius: 20px; font-size: 12px; font-weight: bold; font-family: 'JetBrains Mono', monospace; }");

        // The Table
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 30px; }");
        out.println("th { text-align: left; padding: 15px; background: #f8f9fa; color: #1e3c72; text-transform: uppercase; font-size: 12px; letter-spacing: 1px; }");
        out.println("td { padding: 15px; border-bottom: 1px solid #f0f2f5; color: #444; }");
        out.println(".spec-label { font-weight: bold; color: #1a1a1a; }");

        // Navigation
        out.println(".footer-nav { margin-top: 30px; display: flex; justify-content: space-between; align-items: center; }");
        out.println(".back-btn { color: #1e3c72; text-decoration: none; font-weight: bold; font-size: 14px; transition: color 0.3s; }");
        out.println(".back-btn:hover { color: #2a5298; text-decoration: underline; }");
        out.println("</style></head><body>");

        out.println("<div class='card'>");
        out.println("<div class='header-img'></div>");

        out.println("<div class='content'>");
        out.println("<h1>Engineering Specifications</h1>");
        out.println("<p style='color: #666;'>Standard baseline requirements for all Elite Showroom vehicles.</p>");

        out.println("<table>");
        out.println("<thead><tr><th>System</th><th>Configuration Status</th></tr></thead>");
        out.println("<tbody>");
        out.println("<tr><td class='spec-label'>Engine Interface</td></tr>");
        out.println("<tr><td class='spec-label'>Chassis</td><td>Unibody Aerospace-Grade Aluminum</td></tr>");
        out.println("<tr><td class='spec-label'>Braking</td><td>Carbon Ceramic Cross-Drilled</td></tr>");
        out.println("<tr><td class='spec-label'>Safety</td><td>5-Star Euro NCAP (Active Collision Avoidance)</td></tr>");
        out.println("<tr><td class='spec-label'>Transmission</td><td>Electronic 8-Speed Seamless-Shift</td></tr>");
        out.println("</tbody></table>");

        out.println("<div class='footer-nav'>");
        out.println("<a href='home' class='back-btn'>&larr; Return to Dashboard</a>");
        out.println("<span style='font-size: 12px; color: #ccc;'>v1.0.4-STABLE</span>");
        out.println("</div>");

        out.println("</div></div>");
        out.println("</body></html>");
    }
}