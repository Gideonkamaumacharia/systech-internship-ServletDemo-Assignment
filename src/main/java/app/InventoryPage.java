package app;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class InventoryPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Car Inventory | Register</title>");
        // Using Google Fonts for a more "premium" feel
        out.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;700&display=swap' rel='stylesheet'>");
        out.println("<style>");
        out.println("body { font-family: 'Inter', sans-serif; margin: 0; background-color: #f0f2f5; display: flex; align-items: center; justify-content: center; min-height: 100vh; }");
        out.println(".card { background: white; border-radius: 12px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); overflow: hidden; width: 100%; max-width: 450px; }");
        out.println(".hero-img { width: 100%; height: 200px; object-fit: cover; background-image: url('https://images.unsplash.com/photo-1503376780353-7e6692767b70?auto=format&fit=crop&w=800&q=80'); background-size: cover; }");
        out.println(".content { padding: 30px; }");
        out.println("h1 { margin-top: 0; color: #1a1a1a; font-size: 24px; }");
        out.println("label { display: block; margin-top: 15px; font-weight: bold; color: #555; font-size: 14px; }");
        out.println("input { width: 100%; padding: 12px; margin-top: 5px; border: 1px solid #ddd; border-radius: 6px; box-sizing: border-box; transition: border 0.3s; }");
        out.println("input:focus { border-color: #2a5298; outline: none; }");
        out.println("button { width: 100%; background: #1e3c72; color: white; border: none; padding: 14px; margin-top: 25px; cursor: pointer; border-radius: 6px; font-weight: bold; font-size: 16px; transition: background 0.3s; }");
        out.println("button:hover { background: #2a5298; }");
        out.println(".back-link { display: block; text-align: center; margin-top: 20px; color: #666; text-decoration: none; font-size: 14px; }");
        out.println("</style></head><body>");

        out.println("<div class='card'>");
        out.println("<div class='hero-img'></div>");
        out.println("<div class='content'>");
        out.println("<h1>Register New Vehicle</h1>");
        out.println("<p style='color:#888; font-size:14px;'>Enter details for the showroom inventory.</p>");

        out.println("<form action='inventory' method='POST'>");
        out.println("<label>Car Model</label>");
        out.println("<input type='text' name='carModel' placeholder='e.g. Range Rover Sport' required>");
        out.println("<label>Engine Specification</label>");
        out.println("<input type='text' name='engineType' placeholder='e.g. 5.0L V8 Supercharged' required>");
        out.println("<button type='submit'>Add to Showroom</button>");
        out.println("</form>");

        out.println("<a href='home' class='back-link'>&larr; Return to Dashboard</a>");
        out.println("</div></div></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String model = req.getParameter("carModel");
        String engine = req.getParameter("engineType");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;700&display=swap' rel='stylesheet'>");
        out.println("<style>");
        out.println("body { font-family: 'Inter', sans-serif; background-color: #f0f2f5; display: flex; align-items: center; justify-content: center; min-height: 100vh; margin: 0; }");
        out.println(".success-card { background: white; padding: 40px; border-radius: 12px; box-shadow: 0 10px 25px rgba(0,0,0,0.1); text-align: center; max-width: 400px; }");
        out.println(".icon { font-size: 50px; color: #28a745; margin-bottom: 20px; }");
        out.println("h1 { color: #1a1a1a; margin-bottom: 10px; }");
        out.println(".details { background: #f8f9fa; padding: 15px; border-radius: 8px; margin: 20px 0; text-align: left; border-left: 4px solid #28a745; }");
        out.println("a { color: #1e3c72; text-decoration: none; font-weight: bold; font-size: 14px; margin: 0 10px; }");
        out.println("</style></head><body>");

        out.println("<div class='success-card'>");
        out.println("<div class='icon'>&#10004;</div>"); // Success Checkmark
        out.println("<h1>Registration Complete</h1>");
        out.println("<p>The vehicle has been successfully added to the system.</p>");

        out.println("<div class='details'>");
        out.println("<strong>Model:</strong> " + model.toUpperCase() + "<br>");
        out.println("<strong>Engine:</strong> " + engine);
        out.println("</div>");

        out.println("<a href='inventory'>Add Another</a> | <a href='home'>Dashboard</a>");
        out.println("</div></body></html>");
    }
}