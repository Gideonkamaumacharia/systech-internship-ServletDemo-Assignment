package app.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "WelcomePage",
urlPatterns = {"/home"})
public class WelcomePage implements Servlet {

    private ServletConfig config;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.config = servletConfig;
        System.out.println("Servlet Started and initialized");
    }

    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpSession session = httpReq.getSession(false);



        out.println("<div class='container'>");
        if( session != null && session.getAttribute("lastViewed") != null) {
            String favCar = (String) session.getAttribute("lastViewed");
            out.println("<p style='color: #2a5298;'>Welcome back! You were recently eyeing the <strong>" + favCar + "</strong>.</p>");
            out.println();
            session.setMaxInactiveInterval(60);// Instead of killing the session, just set the "Life Span" to 60 seconds

        }



        ServletContext context = getServletConfig().getServletContext();
        Integer total = (Integer) context.getAttribute("totalCars");
        String latest = (String) context.getAttribute("latestCar");

        if(total != null){
            out.println("<div style='background: #e9ecef; padding: 15px; border-radius: 8px; margin-top: 20px;'>");
            out.println("<strong>Live Showroom Update:</strong> " + total + " Vehicles in Fleet.");
            out.println("<br><small>Latest Arrival: <span style='color: #1e3c72; font-weight: bold;'>" + latest + "</span></small>");
            out.println("</div>");
        }

        Integer users = (Integer) context.getAttribute("activeUsers");
        if(users != null){
            out.println("<div style='background: #e9ecef; padding: 15px; border-radius: 8px; margin-top: 20px;'>");
            out.println("<strong>Active Users:</strong> " + users + " Users in the website.");
        }

        out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<title>Elite Showroom | Home</title>");
        out.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;700&display=swap' rel='stylesheet'>");
        out.println("<style>");
        out.println("body { font-family: 'Inter', sans-serif; margin: 0; background-color: #f0f2f5; color: #333; }");

        out.println(".hero { background: linear-gradient(rgba(30, 60, 114, 0.8), rgba(42, 82, 152, 0.8)), " +
                "url('https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?auto=format&fit=crop&w=1600&q=80'); " +
                "background-size: cover; background-position: center; height: 400px; color: white; " +
                "display: flex; flex-direction: column; align-items: center; justify-content: center; text-align: center; }");

        out.println(".hero h1 { font-size: 3.5rem; margin: 0; letter-spacing: -1px; }");
        out.println(".hero p { font-size: 1.2rem; opacity: 0.9; margin-top: 10px; }");

        out.println(".container { max-width: 900px; margin: -50px auto 50px; padding: 40px; background: white; " +
                "border-radius: 12px; box-shadow: 0 10px 30px rgba(0,0,0,0.1); text-align: center; }");

        out.println("h2 { color: #1e3c72; margin-bottom: 30px; }");

        out.println(".nav-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; margin-top: 30px; }");
        out.println(".nav-item { padding: 25px; border: 1px solid #eee; border-radius: 10px; text-decoration: none; " +
                "color: #333; transition: all 0.3s ease; background: #fafafa; }");
        out.println(".nav-item:hover { transform: translateY(-5px); box-shadow: 0 5px 15px rgba(0,0,0,0.05); border-color: #1e3c72; }");
        out.println(".nav-item strong { display: block; font-size: 1.1rem; color: #1e3c72; margin-bottom: 5px; }");
        out.println(".nav-item span { font-size: 0.85rem; color: #777; }");

        out.println("</style></head><body>");

        out.println("<div class='hero'>");
        out.println("<h1>ELITE CAR SHOWROOM</h1>");
        out.println("<p>Where Art Meets Automotive</p>");
        out.println("</div>");

        out.println("<div class='container'>");
        out.println("<h2>Dashboard</h2>");
        out.println("<p>Precision engineering meets digital control. From the <strong>initial structural blueprints</strong> to <strong>performance configurations</strong> and <strong>showroom delivery</strong>, this portal governs the entire lifecycle of our elite vehicle lineup.</p>");
        out.println("<div class='nav-grid'>");

        out.println("<a href='home' class='nav-item'>");
        out.println("<strong>Home Page</strong>");
        out.println("</a>");

        out.println("<a href='specs' class='nav-item'>");
        out.println("<strong>Technical Specs</strong>");
        out.println("</a>");

        out.println("<a href='car' class='nav-item'>");
        out.println("<strong>Car Inventory</strong>");
        out.println("</a>");

        out.println("<a href='user' class='nav-item'>");
        out.println("<strong>Showroom users</strong>");
        out.println("</a>");

        out.println("</div>");
        out.println("</div>");

        out.println("<footer style='text-align:center; padding-bottom:40px; color:#aaa; font-size:0.8rem;'>");
        out.println("Built by Gideon &bull; ");
        out.println("</footer>");

        out.println("</body></html>");
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.config;
    }

    @Override
    public String getServletInfo() {
        return "Showroom Welcome Page";
    }

    @Override
    public void destroy() {
        System.out.println("Servlet shutting down!!");
    }
}