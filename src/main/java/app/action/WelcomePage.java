package app.action;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        name = "WelcomePage",
        urlPatterns = {"/home"}
)
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

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<title>Elite Showroom | Home</title>");

        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");

        out.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");

        out.println("<style>");

        out.println("* {");
        out.println("margin: 0;");
        out.println("padding: 0;");
        out.println("box-sizing: border-box;");
        out.println("}");

        out.println("body {");
        out.println("font-family: 'Inter', sans-serif;");
        out.println("background: #0f172a;");
        out.println("color: #e2e8f0;");
        out.println("line-height: 1.6;");
        out.println("min-height: 100vh;");
        out.println("}");

        /* HERO SECTION */
        out.println(".hero {");
        out.println("height: 500px;");
        out.println("background:");
        out.println("linear-gradient(rgba(15,23,42,0.85), rgba(15,23,42,0.92)),");
        out.println("url('https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?auto=format&fit=crop&w=1600&q=80');");
        out.println("background-size: cover;");
        out.println("background-position: center;");
        out.println("display: flex;");
        out.println("flex-direction: column;");
        out.println("justify-content: center;");
        out.println("align-items: center;");
        out.println("text-align: center;");
        out.println("padding: 20px;");
        out.println("border-bottom: 1px solid rgba(255,255,255,0.08);");
        out.println("}");

        out.println(".hero h1 {");
        out.println("font-size: 4rem;");
        out.println("font-weight: 700;");
        out.println("letter-spacing: 2px;");
        out.println("color: #ffffff;");
        out.println("margin-bottom: 15px;");
        out.println("text-shadow: 0 4px 20px rgba(0,0,0,0.5);");
        out.println("}");

        out.println(".hero p {");
        out.println("font-size: 1.2rem;");
        out.println("color: #cbd5e1;");
        out.println("max-width: 700px;");
        out.println("}");

        /* MAIN CONTAINER */
        out.println(".container {");
        out.println("max-width: 1200px;");
        out.println("margin: -80px auto 60px;");
        out.println("padding: 50px;");
        out.println("background: rgba(15, 23, 42, 0.82);");
        out.println("backdrop-filter: blur(14px);");
        out.println("border: 1px solid rgba(255,255,255,0.08);");
        out.println("border-radius: 24px;");
        out.println("box-shadow: 0 20px 50px rgba(0,0,0,0.45);");
        out.println("}");

        /* WELCOME MESSAGE */
        out.println(".welcome-banner {");
        out.println("background: linear-gradient(135deg, #1e293b, #111827);");
        out.println("padding: 18px 22px;");
        out.println("border-radius: 14px;");
        out.println("margin-bottom: 35px;");
        out.println("border-left: 4px solid #38bdf8;");
        out.println("color: #cbd5e1;");
        out.println("font-size: 0.95rem;");
        out.println("}");

        /* HEADINGS */
        out.println("h2 {");
        out.println("font-size: 2rem;");
        out.println("margin-bottom: 20px;");
        out.println("color: #ffffff;");
        out.println("text-align: center;");
        out.println("}");

        out.println(".description {");
        out.println("text-align: center;");
        out.println("max-width: 850px;");
        out.println("margin: 0 auto 45px auto;");
        out.println("font-size: 1rem;");
        out.println("color: #94a3b8;");
        out.println("}");

        /* GRID */
        out.println(".nav-grid {");
        out.println("display: grid;");
        out.println("grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));");
        out.println("gap: 24px;");
        out.println("margin-top: 20px;");
        out.println("}");

        /* CARDS */
        out.println(".nav-item {");
        out.println("background: linear-gradient(145deg, #1e293b, #111827);");
        out.println("padding: 30px 25px;");
        out.println("border-radius: 18px;");
        out.println("text-decoration: none;");
        out.println("color: #e2e8f0;");
        out.println("transition: all 0.3s ease;");
        out.println("border: 1px solid rgba(255,255,255,0.06);");
        out.println("position: relative;");
        out.println("overflow: hidden;");
        out.println("}");

        out.println(".nav-item::before {");
        out.println("content: '';");
        out.println("position: absolute;");
        out.println("top: 0;");
        out.println("left: 0;");
        out.println("width: 100%;");
        out.println("height: 4px;");
        out.println("background: linear-gradient(to right, #38bdf8, #6366f1);");
        out.println("}");

        out.println(".nav-item:hover {");
        out.println("transform: translateY(-8px);");
        out.println("box-shadow: 0 18px 40px rgba(0,0,0,0.4);");
        out.println("border-color: rgba(56,189,248,0.4);");
        out.println("}");

        out.println(".nav-item strong {");
        out.println("display: block;");
        out.println("font-size: 1.1rem;");
        out.println("margin-bottom: 10px;");
        out.println("color: #ffffff;");
        out.println("}");

        out.println(".nav-item span {");
        out.println("font-size: 0.9rem;");
        out.println("color: #94a3b8;");
        out.println("}");

        /* FOOTER */
        out.println("footer {");
        out.println("text-align: center;");
        out.println("padding: 30px;");
        out.println("color: #64748b;");
        out.println("font-size: 0.9rem;");
        out.println("}");

        /* RESPONSIVE */
        out.println("@media(max-width: 768px) {");

        out.println(".hero h1 {");
        out.println("font-size: 2.5rem;");
        out.println("}");

        out.println(".container {");
        out.println("margin: -50px 20px 40px;");
        out.println("padding: 30px 25px;");
        out.println("}");

        out.println(".hero {");
        out.println("height: 420px;");
        out.println("}");

        out.println("}");

        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        /* HERO */
        out.println("<div class='hero'>");
        out.println("<h1>ELITE CAR SHOWROOM</h1>");
        out.println("<p>Luxury Engineering • Premium Inventory • Intelligent Management</p>");
        out.println("</div>");

        /* MAIN CONTAINER */
        out.println("<div class='container'>");

        if (session != null && session.getAttribute("lastViewed") != null) {

            String favCar = (String) session.getAttribute("lastViewed");

            out.println("<div class='welcome-banner'>");
            out.println("Welcome back! You were recently eyeing the <strong>" + favCar + "</strong>.");
            out.println("</div>");

            // Session lifespan = 60 seconds
            session.setMaxInactiveInterval(60);
        }

        ServletContext context = getServletConfig().getServletContext();

        /*
        Integer total = (Integer) context.getAttribute("totalCars");
        String latest = (String) context.getAttribute("latestCar");

        if(total != null){
            out.println("<div class='welcome-banner'>");
            out.println("<strong>Fleet Update:</strong> " + total + " vehicles available.");
            out.println("<br>");
            out.println("Latest Arrival: <strong>" + latest + "</strong>");
            out.println("</div>");
        }
        */

        out.println("<h2>Administrative Dashboard</h2>");

        out.println("<p class='description'>");
        out.println("Manage showroom operations with a premium administrative experience. ");
        out.println("Monitor vehicle inventory, oversee brands and categories, manage users, ");
        out.println("and review system audit logs from one centralized dashboard.");
        out.println("</p>");

        out.println("<div class='nav-grid'>");

        out.println("<a href='" + httpReq.getContextPath() + "/home' class='nav-item'>");
        out.println("<strong>Home Page</strong>");
        out.println("<span>Return to the main dashboard overview.</span>");
        out.println("</a>");

        out.println("<a href='" + httpReq.getContextPath() + "/app/car/list' class='nav-item'>");
        out.println("<strong>Car Inventory</strong>");
        out.println("<span>Manage showroom vehicles and listings.</span>");
        out.println("</a>");

//        // Form — register a new car
//        out.println("<a href='app/car/form' class='nav-item'>");
//        out.println("<strong>Register New Vehicle</strong>");
//        out.println("<span>Add a new car to the showroom.</span>");
//        out.println("</a>");

        out.println("<a href='user' class='nav-item'>");
        out.println("<strong>Showroom Users</strong>");
        out.println("<span>View and manage system users.</span>");
        out.println("</a>");

        out.println("<a href='brand' class='nav-item'>");
        out.println("<strong>Vehicle Brands</strong>");
        out.println("<span>Manage luxury automotive brands.</span>");
        out.println("</a>");

        out.println("<a href='category' class='nav-item'>");
        out.println("<strong>Vehicle Categories</strong>");
        out.println("<span>Organize inventory classifications.</span>");
        out.println("</a>");

        out.println("<a href='showroom' class='nav-item'>");
        out.println("<strong>Showrooms</strong>");
        out.println("<span>Manage physical showroom locations.</span>");
        out.println("</a>");

        out.println("<a href='applogs' class='nav-item'>");
        out.println("<strong>System Audit Logs</strong>");
        out.println("<span>Track activities and monitor operations.</span>");
        out.println("</a>");

        out.println("</div>");
        out.println("</div>");

        /* FOOTER */
        out.println("<footer>");
        out.println("Built by Gideon • Enterprise Showroom Management System");
        out.println("</footer>");

        out.println("</body>");
        out.println("</html>");
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