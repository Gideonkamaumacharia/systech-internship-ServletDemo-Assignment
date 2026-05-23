package app.framework;


import app.dao.GenericDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ShowroomFramework {

    @Inject
    GenericDao dao;

//    public void htmlForm(PrintWriter writer, Class<?> clazz){
//
//        if (!clazz.isAnnotationPresent(ShowroomForm.class))
//            return;
//
//        ShowroomForm formAnnot = clazz.getAnnotation(ShowroomForm.class);
//
//        writer.println("<h2>" + formAnnot.label() + "</h2>");
//        writer.println("<form method='" + formAnnot.method() + "' action='" + formAnnot.actionUrl() + "'>");
//
//        for (Field field : clazz.getDeclaredFields()) {
//            if (!field.isAnnotationPresent(ShowroomFormField.class))
//                continue;
//
//            ShowroomFormField fieldInfo = field.getAnnotation(ShowroomFormField.class);
//            String fieldName = fieldInfo.name().isEmpty() ? field.getName() :  fieldInfo.name();
//            writer.println("<label>" + fieldInfo.label() + ":</label>");
//
//            if("select".equalsIgnoreCase(fieldInfo.type()) && fieldInfo.source() != Object.class){
//                //writer.println("<select name='" + fieldName + "'>");
//                List<?> options = dao.selectAll(fieldInfo.source());
//
//                writer.println("<select name='" + (fieldInfo.name().isEmpty() ? field.getName() : fieldInfo.name()) + "'>");
//                writer.println("<option value=''>-- Select --</option>");
//                if (options != null){
//                    for (Object opt: options){
//                        Object id = getFieldValue(opt, "id");
//                        Object label = getDisplayLabel(opt);
//                        writer.println("<option value='" + id + "'>" + label + "</option>");
//                    }
//                }
//                writer.println("</select>");
//            } else {
//                writer.println("<input type='text' name='"
//                        + (fieldInfo.name().isEmpty()? field.getName() : fieldInfo.name()) + "' placeholder='Enter " +
//                        fieldInfo.placeholder() + "' required />");
//            }
//
//        }
//
//        writer.println("<button type='submit'>Register</button>");
//        writer.println("</form>");
//    }
//
////    public String htmlForm(Class<?> clazz) {
////
////        StringWriter stringWriter = new StringWriter();
////        PrintWriter printWriter   = new PrintWriter(stringWriter);
////
////        htmlForm(printWriter, clazz);  // reuses your existing logic untouched
////
////        return stringWriter.toString();
////    }
//
//    // Original — now just delegates to the contextPath-aware version
//    public void htmlForm(PrintWriter writer, Class<?> clazz, String contextPath) {
//        String html = htmlForm(clazz, contextPath);
//        writer.println(html);
//    }

    // Add this overload to ShowroomFramework that accepts the context path
    public String htmlForm(Class<?> clazz, String contextPath) {

        if (!clazz.isAnnotationPresent(ShowroomForm.class))
            return "";

        ShowroomForm formAnnot = clazz.getAnnotation(ShowroomForm.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter  writer       = new PrintWriter(stringWriter);

        String entityName = clazz.getSimpleName().toLowerCase(); // "car"

        // Build action URL dynamically — no hardcoding
        String actionUrl = contextPath + "/app/" + entityName + "/create";

        writer.println("<h2>" + formAnnot.label() + "</h2>");
        writer.println("<form method='POST' action='" + actionUrl + "'>");

        for (Field field : clazz.getDeclaredFields()) {

            if (!field.isAnnotationPresent(ShowroomFormField.class))
                continue;

            ShowroomFormField fieldInfo = field.getAnnotation(ShowroomFormField.class);
            String fieldName = fieldInfo.name().isEmpty()
                    ? field.getName()
                    : fieldInfo.name();

            writer.println("<label>" + fieldInfo.label() + ":</label>");

            if ("select".equalsIgnoreCase(fieldInfo.type())
                    && fieldInfo.source() != Object.class) {

                List<?> options = dao.selectAll(fieldInfo.source());

                writer.println("<select name='" + fieldName + ".id'>");
                writer.println("<option value=''>-- Select --</option>");

                if (options != null) {
                    for (Object opt : options) {
                        Object id    = getFieldValue(opt, "id");
                        Object label = getDisplayLabel(opt);
                        writer.println("<option value='" + id + "'>"
                                + label + "</option>");
                    }
                }
                writer.println("</select>");

            } else {
                writer.println("<input type='text' name='" + fieldName
                        + "' placeholder='Enter " + fieldInfo.placeholder()
                        + "' required />");
            }
        }

        writer.println("<button type='submit'>Register</button>");
        writer.println("</form>");

        return stringWriter.toString();
    }


    public String htmlEditForm(Class<?> clazz,
                               Object entity,
                               String contextPath) {

        if (!clazz.isAnnotationPresent(ShowroomForm.class))
            return "";

        ShowroomForm formAnnot =
                clazz.getAnnotation(ShowroomForm.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer        = new PrintWriter(stringWriter);

        writer.println("<h2>Edit " + formAnnot.label() + "</h2>");

        // Deployment-safe update URL
        String actionUrl =
                contextPath
                        + "/app/"
                        + clazz.getSimpleName().toLowerCase()
                        + "/update";

        writer.println(
                "<form method='POST' action='"
                        + actionUrl
                        + "'>"
        );

        // Hidden id field
        Object idValue = getFieldValue(entity, "id");

        writer.println(
                "<input type='hidden' "
                        + "name='id' "
                        + "value='" + idValue + "' />"
        );

        for (Field field : clazz.getDeclaredFields()) {

            if (!field.isAnnotationPresent(ShowroomFormField.class))
                continue;

            ShowroomFormField fieldInfo =
                    field.getAnnotation(ShowroomFormField.class);

            String fieldName =
                    fieldInfo.name().isEmpty()
                            ? field.getName()
                            : fieldInfo.name();

            writer.println(
                    "<label>"
                            + fieldInfo.label()
                            + ":</label>"
            );

            // SELECT FIELD
            if ("select".equalsIgnoreCase(fieldInfo.type())
                    && fieldInfo.source() != Object.class) {

                List<?> options =
                        dao.selectAll(fieldInfo.source());

                Object currentVal =
                        getFieldValue(entity, field.getName());

                writer.println("<select name='" + fieldName + ".id'>");

                writer.println("<option value=''>-- Select --</option>");

                if (options != null) {

                    for (Object opt : options) {

                        Object id =
                                getFieldValue(opt, "id");

                        Object label =
                                getDisplayLabel(opt);

                        String selected =
                                (id != null && id.equals(currentVal))
                                        ? "selected"
                                        : "";

                        writer.println(
                                "<option value='"
                                        + id
                                        + "' "
                                        + selected
                                        + ">"
                                        + label
                                        + "</option>"
                        );
                    }
                }

                writer.println("</select>");

            } else {

                // NORMAL INPUT FIELD
                Object currentVal =
                        getFieldValue(entity, field.getName());

                String value =
                        currentVal != null
                                ? currentVal.toString()
                                : "";

                writer.println(
                        "<input type='text' "
                                + "name='" + fieldName + "' "
                                + "value='" + value + "' "
                                + "placeholder='Enter "
                                + fieldInfo.placeholder()
                                + "' required />"
                );
            }
        }

        writer.println("<button type='submit'>Update</button>");
        writer.println("</form>");

        return stringWriter.toString();
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

    public<T> T serializeForm(Map<String, String[]> requestMap,Class<T> clazz ) {

        System.out.println("Form Serialization....");

        try {

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


    public void htmlTable(PrintWriter writer, Class<?> clazz, List<?> tableData, String contextPath) {

        if (!clazz.isAnnotationPresent(ShowroomTable.class))
            return;

        ShowroomTable showroomTable = clazz.getAnnotation(ShowroomTable.class);

        // Build deployment-safe URL
        String registerUrl = contextPath + "/app" + showroomTable.registerUrl();

        writer.println("<section>");
        writer.println("<h2>"
                + showroomTable.label()
                + " Registered</h2>");

        writer.println("<p>");

        writer.println(
                "<table style='border-collapse: collapse; width: 50%; "
                        + "font-family: Arial, sans-serif;'>"
        );

        List<String> fieldNames = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {

            if (!field.isAnnotationPresent(ShowroomTableCol.class))
                continue;
            fieldNames.add(field.getName());
        }

        // Header row
        writer.println("<tr>");
        for (String fieldName : fieldNames) {

            writer.println("<th style='border: 1px solid #000; " + "padding: 8px; background-color: #f2f2f2;'>" + fieldName + "</th>");

        }

        writer.println(
                "<th style='border: 1px solid #000; "
                        + "padding: 8px; background-color: #f2f2f2;'>"
                        + "Actions"
                        + "</th>"
        );

        writer.println("</tr>");

        // Data rows
        for (Object data : tableData) {
            writer.println("<tr>");
            for (String fieldName : fieldNames) {

                try {
                    Field field = data.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);

                    writer.println(
                            "<td style='border: 1px solid #000; padding: 8px;'>"
                                    + field.get(data)
                                    + "</td>"
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            try {

                Object id = getFieldValue(data, "id");

                writer.println(
                        "<td style='border: 1px solid #000; padding: 8px;'>"
                                + "<a href='"
                                + contextPath
                                + "/app/car/edit/"
                                + id
                                + "'>Edit</a>"
                                + "</td>"
                );

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            writer.println("</tr>");
        }

        writer.println("</table>");
        writer.println("</p>");
        writer.println("</section>");
        // Navigation
        writer.println("<section>");
        // USE THE COMPUTED URL HERE
        writer.println(
                "<a href=\"" + registerUrl + "\">"
                        + "&larr; Register "
                        + showroomTable.label()
                        + "</a>"
        );

        writer.println("</section>");
    }

    public String htmlTable(Class<?> clazz,
                            List<?> tableData,
                            String contextPath) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        htmlTable(printWriter, clazz, tableData, contextPath);
        return stringWriter.toString();
    }


}

