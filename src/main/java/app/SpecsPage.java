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
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Car Specs - Generic</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', Arial; margin: 40px; background-color: #f8f9fa; }");
        out.println(".card { background: white; padding: 30px; border-radius: 10px; border-top: 5px solid #2a5298; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #eee; }");
        out.println("th { background-color: #2a5298; color: white; }");
        out.println(".back-btn { display: inline-block; margin-top: 20px; color: #2a5298; text-decoration: none; font-weight: bold; }");
        out.println("</style>");
        out.println("</head>");

        out.println("<body>");
        out.println("<div class=\"card\">");
        out.println("<h1>Technical Specifications</h1>");
        out.println("<p>Demonstrating: <b>GenericServlet</b> (The Lifecycle Adapter)</p>");

        out.println("<table>");
        out.println("<tr><th>Feature</th><th>Standard Configuration</th></tr>");
        out.println("<tr><td>Engine Interface</td><td>Jakarta DIP Compliant</td></tr>");
        out.println("<tr><td>Chassis Construction</td><td>Unibody Aluminum</td></tr>");
        out.println("<tr><td>Safety Rating</td><td>5-Star Euro NCAP</td></tr>");
        out.println("<tr><td>Transmission</td><td>Electronic 8-Speed</td></tr>");
        out.println("</table>");

        out.println("<a href=\"home\" class=\"back-btn\">&larr; Return to Welcome Page</a>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}