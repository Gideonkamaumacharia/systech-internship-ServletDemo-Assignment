package app;

import jakarta.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

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

        out.println("<a href='inventory' class='nav-item'>");
        out.println("<strong>Car Inventory</strong>");
        out.println("</a>");

        out.println("</div>");
        out.println("</div>");

        out.println("<footer style='text-align:center; padding-bottom:40px; color:#aaa; font-size:0.8rem;'>");
        out.println("Built for Cohort 12 learning  Portal &bull; WildFly 39.0.1");
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