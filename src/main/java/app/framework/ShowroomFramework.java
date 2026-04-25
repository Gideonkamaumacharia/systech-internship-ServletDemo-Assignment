package app.framework;


import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ShowroomFramework {

    public static void htmlForm(PrintWriter writer, Class<?> clazz){

        if (!clazz.isAnnotationPresent(ShowroomForm.class))
            return;

        ShowroomForm formAnnot = clazz.getAnnotation(ShowroomForm.class);

        writer.println("<h2>" + formAnnot.label() + "</h2>");
        writer.println("<form method='" + formAnnot.method() + "' action='" + formAnnot.actionUrl() + "'>");

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(ShowroomFormField.class))
                continue;

            ShowroomFormField fieldInfo = field.getAnnotation(ShowroomFormField.class);
            writer.println("<label>" + fieldInfo.label() + ":</label>");
            writer.println("<input type='text' name='"
                    + (fieldInfo.name().isEmpty()? field.getName() : fieldInfo.name()) + "' placeholder='Enter " + fieldInfo.placeholder() + "' required />");
        }

        writer.println("<button type='submit'>Register</button>");
        writer.println("</form>");
    }

    public static void htmlTable(PrintWriter writer, Class<?> clazz,
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

