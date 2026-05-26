package app.framework;


import app.dao.GenericDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.BigDecimalConverter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ApplicationScoped
public class ShowroomFramework {

    @Inject
    GenericDao dao;


public String htmlForm(Class<?> clazz, String contextPath) {

    if (!clazz.isAnnotationPresent(ShowroomForm.class))
        return "";

    ShowroomForm formAnnot = clazz.getAnnotation(ShowroomForm.class);

    StringWriter stringWriter = new StringWriter();
    PrintWriter writer = new PrintWriter(stringWriter);

    String entityName = clazz.getSimpleName().toLowerCase();

    String actionUrl = contextPath + "/app/" + entityName + "/create";

    writer.println("""

        <style>
            .enterprise-form-container {
                max-width: 700px;
                margin: 40px auto;
                background: rgba(15, 23, 42, 0.88);
                backdrop-filter: blur(12px);
                border-radius: 22px;
                padding: 35px;
                border: 1px solid rgba(255,255,255,0.08);
                box-shadow: 0 20px 50px rgba(0,0,0,0.45);
                font-family: 'Inter', sans-serif;
            }
            .enterprise-form-title {
                color: #ffffff;
                font-size: 1.8rem;
                margin-bottom: 25px;
                text-align: center;
            }
            .form-group {margin-bottom: 20px;}
            .form-label {
                display: block;
                margin-bottom: 8px;
                color: #cbd5e1;
                font-size: 0.92rem;
                font-weight: 600;
            }
            .enterprise-input,
            .enterprise-select {
                width: 100%;
                padding: 11px 14px;
                border-radius: 12px;
                border: 1px solid rgba(255,255,255,0.08);
                background: #1e293b;
                color: #ffffff;
                outline: none;
                transition: 0.3s ease;
            }
            .enterprise-input:focus,
            .enterprise-select:focus {
                border-color: #38bdf8;
                box-shadow:0 0 0 4px rgba(56,189,248,0.15);
            }
            .enterprise-input::placeholder {   color: #94a3b8;}
            .enterprise-btn {
                width: 100%;
                padding: 13px;
                border: none;
                border-radius: 12px;
                margin-top: 10px;
                background:linear-gradient( to right,#38bdf8, #6366f1);
                color: white;
                font-weight: 600;
                cursor: pointer;
                transition: 0.3s ease;
            }

            .enterprise-btn:hover {
                transform: translateY(-2px);
                box-shadow:
                          0 10px 25px
                          rgba(56,189,248,0.25);
            }

        </style>

    """);

    writer.println("<div class='enterprise-form-container'>");

    renderTopBar(writer, contextPath);

    writer.println("<h2 class='enterprise-form-title'>" + formAnnot.label() + "</h2>");

    writer.println("<form method='POST' action='" + actionUrl + "'>");

    for (Field field : clazz.getDeclaredFields()) {

        if (!field.isAnnotationPresent(ShowroomFormField.class))
            continue;

        ShowroomFormField fieldInfo =
                field.getAnnotation(ShowroomFormField.class);

        String fieldName = fieldInfo.name().isEmpty() ? field.getName() : fieldInfo.name();

        writer.println("<div class='form-group'>");

        writer.println("<label class='form-label'>" + fieldInfo.label() + "</label>");

        // SELECT FIELD
        if ("select".equalsIgnoreCase(fieldInfo.type()) && fieldInfo.source() != Object.class) {

            List<?> options = dao.selectAll(fieldInfo.source());

            if (field.getType() == Long.class || field.getType() == long.class) {
                // It is already an id field — use fieldName directly
                writer.println("<select class='enterprise-select' name='" + fieldName + "'>");
            } else {
                // It is a relationship object field — need the .id suffix
                writer.println("<select class='enterprise-select' name='" + fieldName + ".id'>");
            }

            writer.println("<option value=''>-- Select --</option>");

            if (options != null) {

                for (Object opt : options) {

                    Object id = getFieldValue(opt, "id");

                    Object label = getDisplayLabel(opt);

                    writer.println("<option value='" + id + "'>" + label + "</option>");
                }
            }

            writer.println("</select>");

        } else {

            writer.println(
                    "<input class='enterprise-input' " + "type='text' " +
                            "name='" + fieldName + "' " + "placeholder='Enter " + fieldInfo.placeholder() +
                            "' required />"
            );
        }

        writer.println("</div>");
    }

    writer.println("<button class='enterprise-btn' type='submit'>" + "Register" + "</button>");

    writer.println("<div style='margin-top:15px; display:flex; gap:10px;'>");

    writer.println("<a href='" + contextPath + "/app/" + entityName + "/list' "
            + "style='"
            + "flex:1; text-align:center; padding:11px; border-radius:12px;"
            + "background:rgba(255,255,255,0.05); border:1px solid rgba(255,255,255,0.08);"
            + "color:#cbd5e1; text-decoration:none; font-size:0.9rem;"
            + "transition:0.3s ease;"
            + "'>"
            + "&larr; View " + formAnnot.label() + " List"
            + "</a>");

    writer.println("</div>");

    writer.println("</form>");

    writer.println("</div>");

    return stringWriter.toString();
}

    private void renderRelationshipSelect(
            PrintWriter writer,
            ShowroomFormField fieldInfo,
            String fieldName) {

        List<?> options =
                dao.selectAll(fieldInfo.source());

        writer.println("<select name='" + fieldName + "'>");
        writer.println("<option value=''>-- Select --</option>");

        if (options != null) {

            for (Object opt : options) {

                Object id =
                        getFieldValue(opt, "id");

                Object label = getDisplayLabel(opt);

                writer.println("<option value='" + id + "'>" + label + "</option>");}
        }
        writer.println("</select>");
    }

    public String htmlEditForm(Class<?> clazz,Object entity,String contextPath){

        if(!clazz.isAnnotationPresent(ShowroomForm.class)) return "";

        ShowroomForm formAnnot=clazz.getAnnotation(ShowroomForm.class);

        StringWriter sw=new StringWriter();
        PrintWriter writer=new PrintWriter(sw);

        String actionUrl=contextPath+"/app/"+clazz.getSimpleName().toLowerCase()+"/update";

        writer.println("""
    <style>

    .enterprise-form{
        max-width:700px;
        margin:40px auto;
        padding:35px;
        background:rgba(15,23,42,.92);
        border-radius:22px;
        border:1px solid rgba(255,255,255,.08);
        box-shadow:0 20px 50px rgba(0,0,0,.45);
        font-family:'Inter',sans-serif;
    }

    .enterprise-form h2{
        color:#fff;
        margin-bottom:25px;
        font-size:1.8rem;
        text-align:center;
    }

    .form-group{margin-bottom:18px;}

    .enterprise-form label{
        display:block;
        margin-bottom:8px;
        color:#cbd5e1;
        font-size:.92rem;
        font-weight:600;
    }

    .enterprise-form input,
    .enterprise-form select{
        width:100%;
        padding:11px 14px;
        border-radius:12px;
        border:1px solid rgba(255,255,255,.08);
        background:#1e293b;
        color:#fff;
        outline:none;
        transition:.3s ease;
        box-sizing:border-box;
    }

    .enterprise-form input:focus,
    .enterprise-form select:focus{
        border-color:#38bdf8;
        box-shadow:0 0 0 4px rgba(56,189,248,.15);
    }

    .enterprise-form input::placeholder{color:#94a3b8;}

    .enterprise-btn{
        width:100%;
        padding:13px;
        margin-top:10px;
        border:none;
        border-radius:12px;
        background:linear-gradient(to right,#38bdf8,#6366f1);
        color:#fff;
        font-weight:600;
        cursor:pointer;
        transition:.3s ease;
    }

    .enterprise-btn:hover{
        transform:translateY(-2px);
        box-shadow:0 10px 25px rgba(56,189,248,.25);
    }

    </style>
    """);

        writer.println("<div class='enterprise-form'>");

        renderTopBar(writer, contextPath);

        writer.println("<h2>Edit "+formAnnot.label()+"</h2>");
        writer.println("<form method='POST' action='"+actionUrl+"'>");

        Object idValue=getFieldValue(entity,"id");

        writer.println("<input type='hidden' name='id' value='"+idValue+"'/>");

        for(Field field:clazz.getDeclaredFields()){

            if(!field.isAnnotationPresent(ShowroomFormField.class)) continue;

            ShowroomFormField fieldInfo = field.getAnnotation(ShowroomFormField.class);

            if (fieldInfo.editIgnore()) continue;

            String fieldName=fieldInfo.name().isEmpty()?field.getName():fieldInfo.name();

            writer.println("<div class='form-group'>");
            writer.println("<label>"+fieldInfo.label()+"</label>");

            if("select".equalsIgnoreCase(fieldInfo.type()) && fieldInfo.source()!=Object.class){

                List<?> options=dao.selectAll(fieldInfo.source());

                Object currentVal=getFieldValue(entity,field.getName());

                writer.println("<select name='"+fieldName+".id'>");
                writer.println("<option value=''>-- Select --</option>");

                if(options!=null){

                    for(Object opt:options){

                        Object id=getFieldValue(opt,"id");
                        Object label=getDisplayLabel(opt);

                        String selected=(id!=null && id.equals(currentVal))?"selected":"";

                        writer.println("<option value='"+id+"' "+selected+">"+label+"</option>");
                    }
                }

                writer.println("</select>");

            }else{

                Object currentVal=getFieldValue(entity,field.getName());

                String value=currentVal!=null?currentVal.toString():"";

                writer.println("<input type='text' name='"+fieldName+"' value='"+value+
                        "' placeholder='Enter "+fieldInfo.placeholder()+"' required/>");
            }

            writer.println("</div>");
        }

        writer.println("<button class='enterprise-btn' type='submit'>Update</button>");
        writer.println("</form>");
        writer.println("</div>");

        return sw.toString();
    }
    // Helper to find a "name" or "username" field to show in the dropdown
    private Object getDisplayLabel(Object obj) {
        try {
            for (String name : new String[]{"username", "locationName", "carModel"}) {
                try {
                    Field f = obj.getClass().getDeclaredField(name);
                    f.setAccessible(true);
                    return f.get(obj);
                } catch (NoSuchFieldException e) {
                }
            }
        } catch (Exception e) { return obj.toString(); }
        return obj.toString();
    }

    public <T> T serializeForm(Map<String, String[]> requestMap, Class<T> clazz) {

        System.out.println("Form Serialization....");

        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            T clazzInstance = constructor.newInstance();

            BeanUtilsBean beanUtilsBean = new BeanUtilsBean(new ConvertUtilsBean() {
                @Override
                public Object convert(String value, Class clazz) {
                    if (clazz.isEnum()) {
                        return convertEnumValue(clazz, value);
                    } else if (clazz == Date.class) {
                        SimpleDateFormat dateFormat =
                                new SimpleDateFormat("yyyy-MM-dd");
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

            // Filtered map — relationship keys removed before BeanUtils runs
            Map<String, String[]> filteredMap = new HashMap<>(requestMap);

            for (Field field : clazz.getDeclaredFields()) {

                if (!field.isAnnotationPresent(ManyToOne.class)
                        && !field.isAnnotationPresent(OneToOne.class))
                    continue;

                field.setAccessible(true);

                String idKey       = field.getName() + ".id";
                String[] submitted = requestMap.get(idKey);

                boolean hasValue = submitted != null
                        && submitted.length > 0
                        && !submitted[0].isEmpty();

                if (!hasValue) continue;

                Long id = Long.parseLong(submitted[0]);

                // Proxy — tells Hibernate this is an existing record
                Object reference = dao.getReference(field.getType(), id);
                field.set(clazzInstance, reference);

                // Remove so BeanUtils never touches the proxy
                filteredMap.remove(idKey);
            }

            // BeanUtils only sees plain fields now
            beanUtilsBean.populate(clazzInstance, filteredMap);

            return clazzInstance;

        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Class must have a no-argument constructor", e);
        } catch (IllegalAccessException | InvocationTargetException
                 | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private Object convertEnumValue(Class<?> enumClass,String value) {

        try {
            return Enum.valueOf((Class<Enum>) enumClass, value.toUpperCase());
        } catch (IllegalArgumentException e) {

            throw new RuntimeException("Invalid value '" + value + "' for enum "
                            + enumClass.getSimpleName());
        }
    }

    // Helper to get any field value by name (e.g., "id")
    public Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            return "N/A";
        }
    }


    public void htmlTable(PrintWriter writer,Class<?> clazz, List<?> tableData, String contextPath) {

        if (!clazz.isAnnotationPresent(ShowroomTable.class))
            return;

        ShowroomTable showroomTable = clazz.getAnnotation(ShowroomTable.class);

        String registerUrl = contextPath + "/app" + showroomTable.registerUrl();

        writer.println("""

        <style>
            .enterprise-table-container {
                max-width: 1200px;
                margin: 40px auto;
                background: rgba(15, 23, 42, 0.88);
                backdrop-filter: blur(12px);
                border-radius: 22px;
                padding: 30px;
                border: 1px solid rgba(255,255,255,0.08);
                box-shadow:  0 20px 50px rgba(0,0,0,0.45);
                font-family: 'Inter', sans-serif;
            }
            .enterprise-table-title {
                color: #ffffff;
                margin-bottom: 25px;
                font-size: 1.8rem;
            }
            .enterprise-table {
                width: 100%;
                border-collapse: collapse;
                overflow: hidden;
                border-radius: 16px;
            }
            .enterprise-table thead {
                background:
                        linear-gradient(
                                to right,
                                #1e293b,
                                #0f172a
                        );
            }
            .enterprise-table th {
                padding: 16px;
                text-align: left;
                color: #ffffff;
                font-size: 0.92rem;
                font-weight: 600;
                border-bottom: 1px solid rgba(255,255,255,0.08);
            }
            .enterprise-table td {
                padding: 15px;
                color: #cbd5e1;
                border-bottom:1px solid rgba(255,255,255,0.05);
            }
            .enterprise-table tbody tr {
                transition: 0.25s ease;
            }
            .enterprise-table tbody tr:hover {
                background:rgba(56,189,248,0.08);
            }
            .enterprise-action-link {
                display: inline-block;
                padding: 8px 14px;
                border-radius: 10px;
                text-decoration: none;
                font-size: 0.85rem;
                font-weight: 600;
                background:
                        linear-gradient(
                                to right,
                                #38bdf8,
                                #6366f1
                        );
                color: white;
                transition: 0.3s ease;
            }
            .enterprise-action-link:hover {
                transform: translateY(-2px);
                box-shadow:
                        0 10px 20px
                        rgba(56,189,248,0.25);
            }
            .enterprise-register-link {
                display: inline-block;
                margin-top: 25px;
                color: #94a3b8;
                text-decoration: none;
                font-weight: 500;
                transition: 0.3s ease;
            }
            .enterprise-register-link:hover {
                color: #ffffff;
            }
            .empty-state {
                padding: 30px;
                text-align: center;
                color: #94a3b8;
            }
        </style>
    """);

        writer.println("<section class='enterprise-table-container'>");

        renderTopBar(writer, contextPath);

        writer.println("<h2 class='enterprise-table-title'>" + showroomTable.label() + " Registered</h2>");

        if (tableData == null || tableData.isEmpty()) {
            writer.println("<div class='empty-state'>" + "No records available." + "</div>");

        } else {
            writer.println("<table class='enterprise-table'>");
            List<String> fieldNames = new ArrayList<>();
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.isAnnotationPresent(ShowroomTableCol.class))
                    continue;
                fieldNames.add(field.getName());
            }

            writer.println("<thead>");
            writer.println("<tr>");

            for (String fieldName : fieldNames) {
                writer.println("<th>" + fieldName + "</th>");
            }

            writer.println("<th>Actions</th>");
            writer.println("</tr>");
            writer.println("</thead>");

            writer.println("<tbody>");

            for (Object data : tableData) {
                writer.println("<tr>");

                for (String fieldName : fieldNames) {
                    try {
                        Field field = data.getClass().getDeclaredField(fieldName);
                        field.setAccessible(true);

                        Object value = field.get(data);

                        writer.println("<td>" + (value != null ? value : "-") + "</td>");

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                try {
                    Object id = getFieldValue(data, "id");

                    String entityName = clazz.getSimpleName().toLowerCase();
                    writer.println("<td>");

                    writer.println("<a class='enterprise-action-link' href='"
                            + contextPath + "/app/" + entityName + "/edit/" + id + "'>"
                            + "Edit"
                            + "</a>");

                    writer.println("<form method='POST' action='"
                            + contextPath + "/app/" + entityName + "/delete/" + id + "'"
                            + " style='display:inline; margin-left:8px;'"
                            + " onsubmit='return confirm(\"Delete this record?\")'>");

                    writer.println("<button type='submit' style='"
                            + "padding:8px 14px;"
                            + "border-radius:10px;"
                            + "border:none;"
                            + "font-size:0.85rem;"
                            + "font-weight:600;"
                            + "background:linear-gradient(to right,#ef4444,#dc2626);"
                            + "color:white;"
                            + "cursor:pointer;"
                            + "'>Delete</button>");
                    writer.println("</form>");

                    writer.println("</td>");

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                writer.println("</tr>");
            }
            writer.println("</tbody>");
            writer.println("</table>");
        }

        //writer.println("<a class='enterprise-register-link' href='" + registerUrl + "'>" + "&larr; Register " + showroomTable.label() + "</a>");

        writer.println("<div style='margin-top:25px; display:flex; gap:15px; align-items:center;'>");

        writer.println("<a class='enterprise-register-link' href='" + registerUrl + "'>"
                + "&larr; Register " + showroomTable.label()
                + "</a>");

//        writer.println("<a href='" + contextPath + "/home' "
//                + "style='"
//                + "padding:10px 18px; border-radius:12px;"
//                + "background:rgba(255,255,255,0.05); border:1px solid rgba(255,255,255,0.08);"
//                + "color:#94a3b8; text-decoration:none; font-size:0.9rem;"
//                + "transition:0.3s ease;"
//                + "'>"
//                + "&#8962; Dashboard"
//                + "</a>");

        writer.println("</div>");

        writer.println("</section>");
    }
    public String htmlTable(Class<?> clazz,List<?> tableData, String contextPath) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        htmlTable(printWriter, clazz, tableData, contextPath);
        return stringWriter.toString();
    }


    public void renderTopBar(PrintWriter writer, String contextPath) {

        writer.println(
                "<div style='"
                        + "display:flex;"
                        + "justify-content:space-between;"
                        + "align-items:center;"
                        + "margin-bottom:25px;"
                        + "'>"
        );

        writer.println(
                "<a href='" + contextPath + "/home' "
                        + "style='"
                        + "padding:10px 18px;"
                        + "border-radius:12px;"
                        + "background:rgba(255,255,255,0.05);"
                        + "border:1px solid rgba(255,255,255,0.08);"
                        + "color:#cbd5e1;"
                        + "text-decoration:none;"
                        + "'>"
                        + "&#8962; Dashboard"
                        + "</a>"
        );

        writer.println(
                "<a href='" + contextPath + "/app/logout' "
                        + "style='"
                        + "padding:10px 18px;"
                        + "border-radius:12px;"
                        + "background:linear-gradient(to right,#ef4444,#dc2626);"
                        + "color:white;"
                        + "text-decoration:none;"
                        + "font-weight:600;"
                        + "'>"
                        + "Logout"
                        + "</a>"
        );

        writer.println("</div>");
    }

}

