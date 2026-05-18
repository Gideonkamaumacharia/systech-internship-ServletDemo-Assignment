package app.action;

import app.framework.ShowroomFramework;
import app.framework.ShowroomTable;
import app.dao.GenericDao;
import app.model.Car;
import app.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;


import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

//Defines flow
public class BaseAction<T> extends HttpServlet {

    @ApplicationScoped
    @Inject
    ShowroomFramework showroomFramework;

    @SuppressWarnings("unchecked")
    public T serializeForm(Map<String, String[]> requestMap) {

        System.out.println("Form Serialization....");

        try {
            Class<T> clazz = this.getType();
            Constructor<T> constructor = clazz.getDeclaredConstructor();

            T clazzInstance = constructor.newInstance();

            BeanUtilsBean beanUtilsBean = new BeanUtilsBean(new ConvertUtilsBean() {
                @Override
                public Object convert(String value, Class clazz) {
                    if (clazz.isEnum()) {
                        return Enum.valueOf(clazz, value);
                    } else if (clazz == Date.class) {
                        // web forms return the date in the form
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            return dateFormat.parse(value);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        return super.convert(value, clazz);
                    }
                }
            });

            ConvertUtils.register(new BigDecimalConverter(), BigDecimal.class);

            beanUtilsBean.populate(clazzInstance, requestMap);

            return clazzInstance;

        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Class must have a no-argument constructor", e);
        }
        catch (IllegalAccessException | InvocationTargetException | InstantiationException e ) {
            throw new RuntimeException(e);
        }

    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            T entity = this.serializeForm(req.getParameterMap());
            handleCreate(entity,req,resp);
            System.out.println("handleCreate() called for : " + entity.getClass().getSimpleName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (this.getType().isAnnotationPresent(ShowroomTable.class)) {
            resp.sendRedirect(this.getType()
                    .getAnnotation(ShowroomTable.class).tableUrl());

        } else {
            resp.sendRedirect("./home");
        }
    }


    protected void handleCreate(T entity,
                                HttpServletRequest req,
                                HttpServletResponse resp) throws ServletException, IOException {


    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //if session exist, use it, otherwise create a new one
        HttpSession session = req.getSession();

        //For the browser cache not the server
        resp.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma","no-cache");
        resp.setDateHeader("Expires",0);

        if (session == null || session.getAttribute("userAuthenticated") == null) {

            // Not logged in! Send them back to the gate
            HttpServletResponse httpResp = resp;

            String path = req.getServletPath(); //use the request to get the path
            String dest = path.substring(1);//strip the /

            resp.sendRedirect("login?dest=" + dest);
            return;
        }

        ServletConfig config = getServletConfig();

        Class<T> clazz = getType();

        ShowroomTable table = clazz.getAnnotation(ShowroomTable.class);

        PrintWriter writer = resp.getWriter();

        writer.println("<!DOCTYPE html>");
        writer.println("<html>");

        writer.println("<head>");

        writer.println("<meta charset='UTF-8'>");
        writer.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");

        writer.println("<title>");
        writer.println(config.getInitParameter("pageName"));
        writer.println("</title>");

        writer.println("<link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");

        writer.println("<style>");

        writer.println("* {");
        writer.println("margin: 0;");
        writer.println("padding: 0;");
        writer.println("box-sizing: border-box;");
        writer.println("}");

        writer.println("body {");
        writer.println("font-family: 'Inter', sans-serif;");
        writer.println("background: #0f172a;");
        writer.println("color: #e2e8f0;");
        writer.println("min-height: 100vh;");
        writer.println("padding: 40px 20px;");
        writer.println("}");

        /* PAGE WRAPPER */
        writer.println(".page-wrapper {");
        writer.println("max-width: 1200px;");
        writer.println("margin: auto;");
        writer.println("}");

        /* HEADER */
        writer.println("header {");
        writer.println("background: linear-gradient(145deg, #1e293b, #111827);");
        writer.println("padding: 30px;");
        writer.println("border-radius: 24px;");
        writer.println("margin-bottom: 35px;");
        writer.println("border: 1px solid rgba(255,255,255,0.08);");
        writer.println("box-shadow: 0 15px 40px rgba(0,0,0,0.35);");
        writer.println("}");

        writer.println(".header-content {");
        writer.println("display: flex;");
        writer.println("justify-content: space-between;");
        writer.println("align-items: center;");
        writer.println("flex-wrap: wrap;");
        writer.println("gap: 20px;");
        writer.println("}");

        writer.println(".header-title h1 {");
        writer.println("font-size: 2rem;");
        writer.println("color: #ffffff;");
        writer.println("margin-bottom: 8px;");
        writer.println("}");

        writer.println(".header-title p {");
        writer.println("color: #94a3b8;");
        writer.println("font-size: 0.95rem;");
        writer.println("}");

        writer.println(".user-badge {");
        writer.println("background: rgba(56, 189, 248, 0.12);");
        writer.println("border: 1px solid rgba(56, 189, 248, 0.25);");
        writer.println("padding: 12px 18px;");
        writer.println("border-radius: 14px;");
        writer.println("color: #cbd5e1;");
        writer.println("font-size: 0.95rem;");
        writer.println("}");

        writer.println(".user-badge strong {");
        writer.println("color: #ffffff;");
        writer.println("}");

        /* FORM CONTAINER */
        writer.println("section {");
        writer.println("background: rgba(15, 23, 42, 0.85);");
        writer.println("backdrop-filter: blur(12px);");
        writer.println("padding: 40px;");
        writer.println("border-radius: 24px;");
        writer.println("border: 1px solid rgba(255,255,255,0.08);");
        writer.println("box-shadow: 0 20px 50px rgba(0,0,0,0.4);");
        writer.println("margin-bottom: 30px;");
        writer.println("}");

        /* FORM ELEMENTS */
        writer.println("form {");
        writer.println("width: 100%;");
        writer.println("}");

        writer.println("label {");
        writer.println("display: block;");
        writer.println("margin-bottom: 6px;");
        writer.println("font-size: 0.85rem;");
        writer.println("font-weight: 500;");
        writer.println("color: #cbd5e1;");
        writer.println("}");

        writer.println("input, select, textarea {");
        writer.println("width: 100%;");
        writer.println("padding: 10px 12px;");
        writer.println("margin-bottom: 16px;");
        writer.println("border-radius: 10px;");
        writer.println("border: 1px solid rgba(255,255,255,0.08);");
        writer.println("background: #1e293b;");
        writer.println("color: #ffffff;");
        writer.println("font-size: 0.88rem;");
        writer.println("min-height: 42px;");
        writer.println("outline: none;");
        writer.println("transition: all 0.3s ease;");
        writer.println("}");

        writer.println("input:focus, select:focus, textarea:focus {");
        writer.println("border-color: #38bdf8;");
        writer.println("box-shadow: 0 0 0 4px rgba(56,189,248,0.15);");
        writer.println("}");

        writer.println("button, input[type='submit'] {");
        writer.println("background: linear-gradient(to right, #38bdf8, #6366f1);");
        writer.println("color: white;");
        writer.println("border: none;");
        writer.println("padding: 14px 24px;");
        writer.println("border-radius: 14px;");
        writer.println("font-size: 0.95rem;");
        writer.println("font-weight: 600;");
        writer.println("cursor: pointer;");
        writer.println("transition: all 0.3s ease;");
        writer.println("}");

        writer.println("button:hover, input[type='submit']:hover {");
        writer.println("transform: translateY(-2px);");
        writer.println("box-shadow: 0 10px 25px rgba(99,102,241,0.35);");
        writer.println("}");

        /* NAVIGATION LINKS */
        writer.println(".nav-link {");
        writer.println("display: inline-block;");
        writer.println("margin-top: 15px;");
        writer.println("padding: 14px 22px;");
        writer.println("background: linear-gradient(145deg, #1e293b, #111827);");
        writer.println("color: #ffffff;");
        writer.println("text-decoration: none;");
        writer.println("border-radius: 14px;");
        writer.println("border: 1px solid rgba(255,255,255,0.08);");
        writer.println("transition: all 0.3s ease;");
        writer.println("}");

        writer.println(".nav-link:hover {");
        writer.println("transform: translateY(-3px);");
        writer.println("border-color: rgba(56,189,248,0.35);");
        writer.println("box-shadow: 0 12px 25px rgba(0,0,0,0.3);");
        writer.println("}");

        /* FOOTER NAV */
        writer.println(".footer-nav {");
        writer.println("margin-top: 35px;");
        writer.println("display: flex;");
        writer.println("justify-content: space-between;");
        writer.println("align-items: center;");
        writer.println("flex-wrap: wrap;");
        writer.println("gap: 15px;");
        writer.println("}");

        writer.println(".back-btn {");
        writer.println("display: inline-block;");
        writer.println("padding: 14px 22px;");
        writer.println("background: rgba(255,255,255,0.05);");
        writer.println("border: 1px solid rgba(255,255,255,0.08);");
        writer.println("border-radius: 14px;");
        writer.println("text-decoration: none;");
        writer.println("color: #e2e8f0;");
        writer.println("transition: all 0.3s ease;");
        writer.println("}");

        writer.println(".back-btn:hover {");
        writer.println("background: rgba(255,255,255,0.08);");
        writer.println("transform: translateY(-2px);");
        writer.println("}");

        /* RESPONSIVE */
        writer.println("@media(max-width: 768px) {");

        writer.println("header {");
        writer.println("padding: 24px;");
        writer.println("}");

        writer.println(".header-title h1 {");
        writer.println("font-size: 1.5rem;");
        writer.println("}");

        writer.println("section {");
        writer.println("padding: 25px;");
        writer.println("}");

        writer.println(".footer-nav {");
        writer.println("flex-direction: column;");
        writer.println("align-items: flex-start;");
        writer.println("}");

        writer.println("}");

        writer.println("</style>");

        writer.println("</head>");

        writer.println("<body>");

        writer.println("<div class='page-wrapper'>");

        // HEADER
        writer.println("<header>");

        writer.println("<div class='header-content'>");

        writer.println("<div class='header-title'>");
        writer.println("<h1>");
        writer.println("</h1>");
        writer.println("<p>Enterprise Showroom Management Portal</p>");
        writer.println("</div>");

        writer.println("<div class='user-badge'>");
        writer.println("Logged in as <strong>");
        writer.println(session.getAttribute("UserActualName"));
        writer.println("</strong>");
        writer.println("</div>");

        writer.println("</div>");

        writer.println("</header>");

        // FORM SECTION
        writer.println("<section>");

        showroomFramework.htmlForm(writer, this.getType());

        writer.println("</section>");

        // VIEW REGISTERED LINK
        writer.println("<a href=\"" + table.tableUrl() + "\" class=\"nav-link\">");
        writer.println("View Registered " + table.label());
        writer.println("</a>");

        // FOOTER NAV
        writer.println("<div class='footer-nav'>");

        writer.println("<a href='home' class='back-btn'>");
        writer.println("&larr; Return to Dashboard");
        writer.println("</a>");

        writer.println("<span style='font-size: 12px; color: #64748b;'>");
        writer.println("v1.0.4-STABLE");
        writer.println("</span>");

        writer.println("</div>");

        writer.println("</div>");

        writer.println("</body>");
        writer.println("</html>");
    }
    @SuppressWarnings("unchecked")
    public Class<T> getType() {
        ParameterizedType superClass =
                (ParameterizedType) getClass().getGenericSuperclass();

        return (Class<T>) superClass.getActualTypeArguments()[0];
    }




}