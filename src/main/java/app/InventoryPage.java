package app;

import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Way 3: Extending HttpServlet.
 * Demonstrates HTTP Method Handling (GET vs POST).
 */
public class InventoryPage extends HttpServlet {

    // 1. Handles the initial page load (Viewing the form)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><head><title>Car Inventory</title>");
        out.println("<style>");
        out.println("body { font-family: Arial; padding: 40px; background-color: #f4f4f4; }");
        out.println(".form-container { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); max-width: 500px; }");
        out.println("input { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ccc; border-radius: 4px; }");
        out.println("button { background: #28a745; color: white; border: none; padding: 10px 20px; cursor: pointer; border-radius: 4px; }");
        out.println("</style></head><body>");

        out.println("<div class='form-container'>");
        out.println("<h1>Add New Car to Inventory</h1>");
        out.println("<p>Demonstrating: <b>HttpServlet.doGet()</b></p>");

        // THE FORM: Note the method="POST"
        out.println("<form action='inventory' method='POST'>");
        out.println("Car Model: <input type='text' name='carModel' placeholder='e.g. Range Rover' required>");
        out.println("Engine Type: <input type='text' name='engineType' placeholder='e.g. V8' required>");
        out.println("<button type='submit'>Register Car</button>");
        out.println("</form>");

        out.println("<br><a href='home'>Back to Welcome Page</a>");
        out.println("</div></body></html>");
    }

    // 2. Handles the Form Submission (Processing data)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Retrieve data from the request parameters
        String model = req.getParameter("carModel");
        String engine = req.getParameter("engineType");

        System.out.println("DEBUG: Successfully received POST request for: " + model);

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body style='font-family: Arial; text-align: center; padding-top: 50px;'>");
        out.println("<div style='display: inline-block; padding: 30px; border: 2px solid #28a745; border-radius: 10px;'>");
        out.println("<h1 style='color: #28a745;'>✔ Car Registered Successfully!</h1>");
        out.println("<p>Demonstrating: <b>HttpServlet.doPost()</b></p>");
        out.println("<p><b>Model:</b> " + model + "</p>");
        out.println("<p><b>Engine:</b> " + engine + "</p>");
        out.println("<hr>");
        out.println("<a href='inventory'>Add Another Car</a> | <a href='home'>Home</a>");
        out.println("</div></body></html>");
    }
}