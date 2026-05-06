package app.framework;


import app.dao.GenericDao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ShowroomFramework {

    @Inject
    GenericDao dao;

    public void htmlForm(PrintWriter writer, Class<?> clazz){

        if (!clazz.isAnnotationPresent(ShowroomForm.class))
            return;

        ShowroomForm formAnnot = clazz.getAnnotation(ShowroomForm.class);

        writer.println("<h2>" + formAnnot.label() + "</h2>");
        writer.println("<form method='" + formAnnot.method() + "' action='" + formAnnot.actionUrl() + "'>");

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ShowroomFormField.class))
                continue;

            ShowroomFormField fieldInfo = field.getAnnotation(ShowroomFormField.class);
            String fieldName = fieldInfo.name().isEmpty() ? field.getName() :  fieldInfo.name();
            writer.println("<label>" + fieldInfo.label() + ":</label>");

            if("select".equalsIgnoreCase(fieldInfo.type()) && fieldInfo.source() != Object.class){
                //writer.println("<select name='" + fieldName + "'>");
                List<?> options = dao.selectAll(fieldInfo.source());

                writer.println("<select name='" + (fieldInfo.name().isEmpty() ? field.getName() : fieldInfo.name()) + "'>");
                writer.println("<option value=''>-- Select --</option>");
                if (options != null){
                    for (Object opt: options){
                        Object id = getFieldValue(opt, "id");
                        Object label = getDisplayLabel(opt);
                        writer.println("<option value='" + id + "'>" + label + "</option>");
                    }
                }
                writer.println("</select>");
            } else {
                writer.println("<input type='text' name='"
                        + (fieldInfo.name().isEmpty()? field.getName() : fieldInfo.name()) + "' placeholder='Enter " +
                        fieldInfo.placeholder() + "' required />");
            }

        }

        writer.println("<button type='submit'>Register</button>");
        writer.println("</form>");
    }

    // Helper to find a "name" or "username" field to show in the dropdown
    private Object getDisplayLabel(Object obj) {
        try {
            // Try common naming fields
            for (String name : new String[]{"username", "locationName", "carModel"}) {
                try {
                    Field f = obj.getClass().getDeclaredField(name);
                    f.setAccessible(true);
                    return f.get(obj);
                } catch (NoSuchFieldException e) { /* continue */ }
            }
        } catch (Exception e) { return obj.toString(); }
        return obj.toString();
    }

    // Helper to get any field value by name (e.g., "id")
    public Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            return "N/A"; // Fallback if field doesn't exist
        }
    }


    public void htmlTable(PrintWriter writer, Class<?> clazz,
                                 List<?> tableData) {

        if (!clazz.isAnnotationPresent(ShowroomTable.class))
            return;
        ShowroomTable cohort12Table = clazz.getAnnotation(ShowroomTable.class);

        writer.println("<section>");
        writer.println("<h2>" + cohort12Table.label() + " Registered</h2>");
        writer.println("<p>");

        writer.println("<table style='border-collapse: collapse; width: 50%; font-family: Arial, sans-serif;'>");

        List<String> fieldNames = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ShowroomTableCol.class))
                continue;

            fieldNames.add(field.getName());
        }

        writer.println("<tr>");
        for (String fieldName : fieldNames) {

            // Header row
            writer.println("<th style='border: 1px solid #000; padding: 8px; background-color: #f2f2f2;'>" + fieldName + "</th>");
        }
        writer.println("</tr>");

        for (Object data : tableData) {
            writer.println("<tr>");
            for (String fieldName : fieldNames) {
                try {
                    Field field = data.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    writer.println("<td style='border: 1px solid #000; padding: 8px;'>"
                            + field.get(data) + "</td>");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
            writer.println("</tr>");
        }

        writer.println("</table>");
        writer.println("</p>");
        writer.println("</section>");

// Navigation
        writer.println("<section>");
        writer.println("<a href=\"" + cohort12Table.registerUrl() + "\">&larr; Register " + cohort12Table.label() + " </a>");
        writer.println("</section>");

    }


}

