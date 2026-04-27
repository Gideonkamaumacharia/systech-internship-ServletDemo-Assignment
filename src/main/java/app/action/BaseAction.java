package app.action;

import app.framework.ShowroomFramework;
import app.framework.ShowroomTable;
import app.utility.db.GenericDao;
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

public class BaseAction<T> extends HttpServlet {

    @ApplicationScoped
    @Inject
    GenericDao dao;

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
        //if session exist, use it, otherwise create a new one

        try {
            T entity = this.serializeForm(req.getParameterMap());
            dao.insert(this.getType(), entity);
            System.out.println("DAO insert called for: " + entity.getClass().getSimpleName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (this.getType().isAnnotationPresent(ShowroomTable.class)) {
            resp.sendRedirect(this.getType()
                    .getAnnotation(ShowroomTable.class).tableUrl());//redirect to tableUrl
            //for car-> list (which is CarList)

        } else {
            resp.sendRedirect("./home");
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException{

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
        writer.println("<title>");
        writer.println(config.getInitParameter("pageName"));
        writer.println("</title>");
        writer.println("<style>");
        writer.println("body { font-family: Arial; margin: 40px; background-color: #f4f6f8; }");
        writer.println("header { background-color: #2c3e50; color: white; padding: 15px; }");
        writer.println("section { margin-top: 20px; padding: 20px; background: white; border-radius: 5px; max-width: 400px; }");
        writer.println("input { width: 100%; padding: 8px; margin: 10px 0; }");
        writer.println("button { padding: 10px; background-color: #3498db; color: white; border: none; width: 100%; }");
        writer.println("a { display: inline-block; margin-top: 10px; color: #3498db; }");
        writer.println("</style>");
        writer.println("</head>");

        writer.println("<body>");

// Header
        writer.println("<header>");
        writer.println("<h1>");
        writer.println(config.getInitParameter("pageHeader"));
        writer.print("Logged In User: ");
        writer.println(session.getAttribute("UserActualName"));
        writer.println("</h1>");
        writer.println("</header>");

// Form
        writer.println("<section>");
        showroomFramework.htmlForm(writer, this.getType());
        writer.println("</section>");
        writer.println("<a href=\"" + table.tableUrl() + "\" class=\"nav-link\">View Registered " + table.label() + "</a>");

        writer.println("<div class='footer-nav'>");
        writer.println("<a href='home' class='back-btn'>&larr; Return to Dashboard</a>");
        writer.println("<span style='font-size: 12px; color: #ccc;'>v1.0.4-STABLE</span>");
        writer.println("</div>");
        writer.println("</body>");
        writer.println("</html>");

    }

    @SuppressWarnings("unchecked")
    public Class<T> getType() { //Returns the actual entity class eg Person.class
        ParameterizedType superClass =
                (ParameterizedType) getClass().getGenericSuperclass();

        return (Class<T>) superClass.getActualTypeArguments()[0];
    }

    public String dbName(){
        return this.getType().getSimpleName() + "_DB";
    }

    @SuppressWarnings("unchecked")
    public List<T> returnData(HttpSession session) throws SQLException {

        System.out.println("DB NAME: " + this.dbName());

        List<T> data = dao.selectAll(this.getType());
        return (List<T>) data;
    }

}