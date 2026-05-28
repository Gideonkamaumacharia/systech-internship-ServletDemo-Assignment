package app.framework;


import app.dao.GenericDao;
import app.model.Brand;
import app.model.Category;
import app.model.Showroom;
import app.model.User;
import app.model.enums.UserRole;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.servlet.http.HttpServletRequest;
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
    FrameworkDataProvider dao;


    public String htmlForm(Class<?> clazz, String contextPath) {

        if (!clazz.isAnnotationPresent(ShowroomForm.class))
            return "";

        ShowroomForm formAnnot = clazz.getAnnotation(ShowroomForm.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        String entityName = clazz.getSimpleName().toLowerCase();
        String actionUrl  = contextPath + "/app/" + entityName + "/create";

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
            .form-group { margin-bottom: 20px; }
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
                box-shadow: 0 0 0 4px rgba(56,189,248,0.15);
            }
            .enterprise-input::placeholder { color: #94a3b8; }
            .enterprise-btn {
                width: 100%;
                padding: 13px;
                border: none;
                border-radius: 12px;
                margin-top: 10px;
                background: linear-gradient(to right, #38bdf8, #6366f1);
                color: white;
                font-weight: 600;
                cursor: pointer;
                transition: 0.3s ease;
            }
            .enterprise-btn:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 25px rgba(56,189,248,0.25);
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

            ShowroomFormField fieldInfo = field.getAnnotation(ShowroomFormField.class);
            String fieldName = fieldInfo.name().isEmpty() ? field.getName() : fieldInfo.name();

            writer.println("<div class='form-group'>");
            writer.println("<label class='form-label'>" + fieldInfo.label() + "</label>");

            boolean isSelectType = "select".equalsIgnoreCase(fieldInfo.type());

            // ── Determine which branch to use ──────────────────────────────
            boolean isEnumSelect   = isSelectType
                    && fieldInfo.enumSource() != null
                    && fieldInfo.enumSource() != ShowroomFormField.NullEnum.class
                    && fieldInfo.enumSource().isEnum();

            boolean isEntitySelect = isSelectType
                    && !isEnumSelect                        // enum takes priority
                    && fieldInfo.source() != null
                    && fieldInfo.source() != Object.class;

            // ── ENUM SELECT ────────────────────────────────────────────────
            if (isEnumSelect) {

                writer.println("<select class='enterprise-select' name='" + fieldName + "'>");
                writer.println("<option value=''>-- Select " + fieldInfo.label() + " --</option>");

                for (Object constant : fieldInfo.enumSource().getEnumConstants()) {
                    writer.println("<option value='" + constant + "'>" + constant + "</option>");
                }

                writer.println("</select>");

                // ── ENTITY SELECT ──────────────────────────────────────────────
            } else if (isEntitySelect) {

                List<?> options = dao.selectAll(fieldInfo.source());

                // Relationship field → submit as fieldName.id so serializeForm resolves the proxy
                String selectName = (field.getType() == Long.class || field.getType() == long.class)
                        ? fieldName
                        : fieldName + ".id";

                writer.println("<select class='enterprise-select' name='" + selectName + "'>");
                writer.println("<option value=''>-- Select " + fieldInfo.label() + " --</option>");

                if (options != null) {
                    for (Object opt : options) {
                        Object id    = getFieldValue(opt, "id");
                        Object label = getDisplayLabel(opt);
                        writer.println("<option value='" + id + "'>" + label + "</option>");
                    }
                }

                writer.println("</select>");

                // ── PLAIN TEXT INPUT ───────────────────────────────────────────
            } else {

                writer.println(
                        "<input class='enterprise-input' type='text' " +
                                "name='" + fieldName + "' " +
                                "placeholder='Enter " + fieldInfo.placeholder() + "' required />"
                );
            }

            writer.println("</div>");
        }

        writer.println("<button class='enterprise-btn' type='submit'>Register</button>");

        writer.println("<div style='margin-top:15px; display:flex; gap:10px;'>");
        writer.println(
                "<a href='" + contextPath + "/app/" + entityName + "/list' " +
                        "style='flex:1; text-align:center; padding:11px; border-radius:12px;" +
                        "background:rgba(255,255,255,0.05); border:1px solid rgba(255,255,255,0.08);" +
                        "color:#cbd5e1; text-decoration:none; font-size:0.9rem; transition:0.3s ease;'>" +
                        "&larr; View " + formAnnot.label() + " List" +
                        "</a>"
        );
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

    public String htmlEditForm(Class<?> clazz, Object entity, String contextPath) {

        if (!clazz.isAnnotationPresent(ShowroomForm.class)) return "";

        ShowroomForm formAnnot = clazz.getAnnotation(ShowroomForm.class);

        StringWriter sw     = new StringWriter();
        PrintWriter  writer = new PrintWriter(sw);

        String actionUrl = contextPath + "/app/" + clazz.getSimpleName().toLowerCase() + "/update";

        writer.println("""
        <style>
        .enterprise-form {
            max-width: 700px;
            margin: 40px auto;
            padding: 35px;
            background: rgba(15,23,42,.92);
            border-radius: 22px;
            border: 1px solid rgba(255,255,255,.08);
            box-shadow: 0 20px 50px rgba(0,0,0,.45);
            font-family: 'Inter', sans-serif;
        }
        .enterprise-form h2 {
            color: #fff;
            margin-bottom: 25px;
            font-size: 1.8rem;
            text-align: center;
        }
        .form-group { margin-bottom: 18px; }
        .enterprise-form label {
            display: block;
            margin-bottom: 8px;
            color: #cbd5e1;
            font-size: .92rem;
            font-weight: 600;
        }
        .enterprise-form input,
        .enterprise-form select {
            width: 100%;
            padding: 11px 14px;
            border-radius: 12px;
            border: 1px solid rgba(255,255,255,.08);
            background: #1e293b;
            color: #fff;
            outline: none;
            transition: .3s ease;
            box-sizing: border-box;
        }
        .enterprise-form input:focus,
        .enterprise-form select:focus {
            border-color: #38bdf8;
            box-shadow: 0 0 0 4px rgba(56,189,248,.15);
        }
        .enterprise-form input::placeholder { color: #94a3b8; }
        .enterprise-btn {
            width: 100%;
            padding: 13px;
            margin-top: 10px;
            border: none;
            border-radius: 12px;
            background: linear-gradient(to right, #38bdf8, #6366f1);
            color: #fff;
            font-weight: 600;
            cursor: pointer;
            transition: .3s ease;
        }
        .enterprise-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(56,189,248,.25);
        }
        </style>
    """);

        writer.println("<div class='enterprise-form'>");

        renderTopBar(writer, contextPath);

        writer.println("<h2>Edit " + formAnnot.label() + "</h2>");
        writer.println("<form method='POST' action='" + actionUrl + "'>");

        Object idValue = getFieldValue(entity, "id");
        writer.println("<input type='hidden' name='id' value='" + idValue + "'/>");

        for (Field field : clazz.getDeclaredFields()) {

            if (!field.isAnnotationPresent(ShowroomFormField.class)) continue;

            ShowroomFormField fieldInfo = field.getAnnotation(ShowroomFormField.class);

            if (fieldInfo.editIgnore()) continue;

            String fieldName = fieldInfo.name().isEmpty() ? field.getName() : fieldInfo.name();

            writer.println("<div class='form-group'>");
            writer.println("<label>" + fieldInfo.label() + "</label>");

            // ── ENTITY SELECT ──────────────────────────────────────────────
            if ("select".equalsIgnoreCase(fieldInfo.type())
                    && fieldInfo.source() != Object.class) {

                List<?> options   = dao.selectAll(fieldInfo.source());
                Object currentVal = getFieldValue(entity, field.getName());

                writer.println("<select name='" + fieldName + ".id'>");
                writer.println("<option value=''>-- Select --</option>");

                if (options != null) {
                    for (Object opt : options) {
                        Object id    = getFieldValue(opt, "id");
                        Object label = getDisplayLabel(opt);
                        String selected = (id != null && id.equals(currentVal))
                                ? "selected" : "";
                        writer.println("<option value='" + id + "' "
                                + selected + ">" + label + "</option>");
                    }
                }

                writer.println("</select>");

                // ── ENUM SELECT ────────────────────────────────────────────────
            } else if ("select".equalsIgnoreCase(fieldInfo.type())
                    && fieldInfo.enumSource() != null
                    && fieldInfo.enumSource() != ShowroomFormField.NullEnum.class
                    && fieldInfo.enumSource().isEnum()) {

                Object currentVal = getFieldValue(entity, field.getName());

                writer.println("<select name='" + fieldName + "'>");
                writer.println("<option value=''>-- Select " + fieldInfo.label() + " --</option>");

                for (Object constant : fieldInfo.enumSource().getEnumConstants()) {
                    String selected = (currentVal != null
                            && currentVal.toString().equals(constant.toString()))
                            ? "selected" : "";
                    writer.println("<option value='" + constant + "' "
                            + selected + ">" + constant + "</option>");
                }

                writer.println("</select>");

                // ── PLAIN TEXT INPUT ───────────────────────────────────────────
            } else {

                Object currentVal = getFieldValue(entity, field.getName());
                String value      = currentVal != null ? currentVal.toString() : "";

                writer.println("<input type='text' name='" + fieldName
                        + "' value='" + value
                        + "' placeholder='Enter " + fieldInfo.placeholder()
                        + "' required/>");
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


    public void htmlTable(PrintWriter writer, Class<?> clazz, List<?> tableData,
                          String contextPath, User caller) {

        if (!clazz.isAnnotationPresent(ShowroomTable.class))
            return;

        ShowroomTable showroomTable = clazz.getAnnotation(ShowroomTable.class);
        String registerUrl = contextPath + "/app" + showroomTable.registerUrl();

        boolean canWrite = caller != null
                && (caller.getRole() == UserRole.ADMIN
                || caller.getRole() == UserRole.MANAGER);

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
                box-shadow: 0 20px 50px rgba(0,0,0,0.45);
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
                background: linear-gradient(to right, #1e293b, #0f172a);
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
                border-bottom: 1px solid rgba(255,255,255,0.05);
            }
            .enterprise-table tbody tr {
                transition: 0.25s ease;
            }
            .enterprise-table tbody tr:hover {
                background: rgba(56,189,248,0.08);
            }
            .enterprise-action-link {
                display: inline-block;
                padding: 8px 14px;
                border-radius: 10px;
                text-decoration: none;
                font-size: 0.85rem;
                font-weight: 600;
                background: linear-gradient(to right, #38bdf8, #6366f1);
                color: white;
                transition: 0.3s ease;
            }
            .enterprise-action-link:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 20px rgba(56,189,248,0.25);
            }
            .enterprise-register-link {
                display: inline-block;
                padding: 10px 20px;
                border-radius: 12px;
                background: linear-gradient(to right, #38bdf8, #6366f1);
                color: white;
                text-decoration: none;
                font-weight: 600;
                font-size: 0.9rem;
                transition: 0.3s ease;
            }
            .enterprise-register-link:hover {
                transform: translateY(-2px);
                box-shadow: 0 10px 20px rgba(56,189,248,0.25);
            }
            .empty-state {
                padding: 30px;
                text-align: center;
                color: #94a3b8;
            }
            .role-badge {
                display: inline-block;
                padding: 3px 10px;
                border-radius: 20px;
                font-size: 0.78rem;
                font-weight: 600;
                background: rgba(56,189,248,0.15);
                color: #38bdf8;
                border: 1px solid rgba(56,189,248,0.3);
            }
        </style>
    """);

        writer.println("<section class='enterprise-table-container'>");
        renderTopBar(writer, contextPath);
        writer.println("<h2 class='enterprise-table-title'>"
                + showroomTable.label() + " Registered</h2>");

        if (tableData == null || tableData.isEmpty()) {
            writer.println("<div class='empty-state'>No records available.</div>");

        } else {

            // ── Collect annotated fields ───────────────────────────────────
            List<Field> annotatedFields = new ArrayList<>();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(ShowroomTableCol.class)) {
                    annotatedFields.add(field);
                }
            }

            writer.println("<table class='enterprise-table'>");

            // ── Header ─────────────────────────────────────────────────────
            writer.println("<thead><tr>");
            for (Field field : annotatedFields) {
                ShowroomTableCol col = field.getAnnotation(ShowroomTableCol.class);
                writer.println("<th>" + col.label() + "</th>");
            }
            // Actions column header — only render if canWrite
            if (canWrite) {
                writer.println("<th>Actions</th>");
            }
            writer.println("</tr></thead>");

            // ── Body ───────────────────────────────────────────────────────
            writer.println("<tbody>");

            for (Object data : tableData) {
                writer.println("<tr>");

                for (Field field : annotatedFields) {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(data);
                        writer.println("<td>" + (value != null ? value : "-") + "</td>");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                // ── Actions cell — only for ADMIN and MANAGER ──────────────
                if (canWrite) {
                    try {
                        Object id = getFieldValue(data, "id");
                        String entityName = clazz.getSimpleName().toLowerCase();

                        writer.println("<td>");

                        // Edit button
                        writer.println(
                                "<a class='enterprise-action-link' " +
                                        "href='" + contextPath + "/app/" + entityName + "/edit/" + id + "'>" +
                                        "Edit</a>"
                        );

                        // Delete button
                        writer.println(
                                "<form method='POST' " +
                                        "action='" + contextPath + "/app/" + entityName + "/delete/" + id + "' " +
                                        "style='display:inline; margin-left:8px;' " +
                                        "onsubmit='return confirm(\"Delete this record?\")'>" +
                                        "<button type='submit' style='" +
                                        "padding:8px 14px; border-radius:10px; border:none;" +
                                        "font-size:0.85rem; font-weight:600;" +
                                        "background:linear-gradient(to right,#ef4444,#dc2626);" +
                                        "color:white; cursor:pointer;'>" +
                                        "Delete</button></form>"
                        );

                        writer.println("</td>");

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                // SALES_REP / VIEWER — no actions cell rendered at all

                writer.println("</tr>");
            }

            writer.println("</tbody>");
            writer.println("</table>");
        }

        // ── Bottom bar — Register link only for ADMIN and MANAGER ─────────
        writer.println("<div style='margin-top:25px; display:flex; gap:15px; align-items:center;'>");

        if (canWrite) {
            writer.println(
                    "<a class='enterprise-register-link' href='" + registerUrl + "'>" +
                            "+ Register " + showroomTable.label() + "</a>"
            );
        } else {
            // SALES_REP / VIEWER sees a read-only label instead
            writer.println(
                    "<span style='color:#94a3b8; font-size:0.9rem;'>" +
                            "Viewing " + showroomTable.label() + " records (read-only)</span>"
            );
        }

        writer.println("</div>");
        writer.println("</section>");
    }

    public String htmlTable(Class<?> clazz,List<?> tableData, String contextPath) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        User caller = (User) CDI.current()
                .select(HttpServletRequest.class)
                .get()
                .getSession(false)
                .getAttribute("activeUser");

        htmlTable(printWriter, clazz, tableData, contextPath,caller);
        return stringWriter.toString();
    }

    public String htmlFilterForm(String contextPath,
                                 String actionPath,
                                 User caller) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter  writer       = new PrintWriter(stringWriter);

        List<Showroom>  showrooms  = dao.selectAll(Showroom.class);
        List<Brand>     brands     = dao.selectAll(Brand.class);
        List<Category>  categories = dao.selectAll(Category.class);

        writer.println("<div class='enterprise-filter-container'>");
        writer.println("<form method='GET' action='" + contextPath + actionPath + "' class='filter-form'>");
        writer.println("<h3 class='filter-title'>Filter Vehicles</h3>");
        writer.println("<div class='filter-row'>");

        // Showroom filter — ADMIN only
        if (caller.getRole() == UserRole.ADMIN) {
            writer.println("<div class='filter-group'>");
            writer.println("<label class='filter-label'>Showroom</label>");
            writer.println("<select name='showroomId' class='enterprise-select'>");
            writer.println("<option value=''>All Showrooms</option>");
            for (Showroom s : showrooms) {
                writer.println("<option value='" + s.getId() + "'>"
                        + s.getLocationName() + "</option>");
            }
            writer.println("</select>");
            writer.println("</div>");
        }

        // Brand filter — Everyone ie ADMIN , MANAGER.SALES_REP and VIEWER
        writer.println("<div class='filter-group'>");
        writer.println("<label class='filter-label'>Brand</label>");
        writer.println("<select name='brandId' class='enterprise-select'>");
        writer.println("<option value=''>All Brands</option>");
        for (Brand b : brands) {
            writer.println("<option value='" + b.getId() + "'>"
                    + b.getName() + "</option>");
        }
        writer.println("</select>");
        writer.println("</div>");

        // Category filter —Everyone ie ADMIN , MANAGER.SALES_REP and VIEWER
        writer.println("<div class='filter-group'>");
        writer.println("<label class='filter-label'>Category</label>");
        writer.println("<select name='categoryId' class='enterprise-select'>");
        writer.println("<option value=''>All Categories</option>");
        for (Category c : categories) {
            writer.println("<option value='" + c.getId() + "'>"
                    + c.getName() + "</option>");
        }
        writer.println("</select>");
        writer.println("</div>");

        writer.println("</div>"); // filter-row

        writer.println("<div class='filter-actions'>");
        writer.println("<button type='submit' class='enterprise-btn' "
                + "style='width:auto; padding: 10px 24px;'>Apply Filter</button>");
        writer.println("<a href='" + contextPath + actionPath + "' "
                + "class='enterprise-btn' "
                + "style='width:auto; padding:10px 24px; "
                + "background: rgba(255,255,255,0.08); "
                + "text-decoration:none; margin-left:10px;'>Clear</a>");
        writer.println("</div>");

        writer.println("</form>");
        writer.println("</div>");

        // CSS
        writer.println("""
        <style>
            .enterprise-filter-container {
                max-width: 1200px;
                margin: 30px auto 0;
                background: rgba(15,23,42,0.88);
                border-radius: 18px;
                padding: 25px 30px;
                border: 1px solid rgba(255,255,255,0.08);
                font-family: 'Inter', sans-serif;
            }
            .filter-title {
                color: #ffffff;
                font-size: 1rem;
                margin-bottom: 16px;
                font-weight: 600;
            }
            .filter-row {
                display: flex;
                gap: 16px;
                flex-wrap: wrap;
                align-items: flex-end;
            }
            .filter-group {
                display: flex;
                flex-direction: column;
                gap: 6px;
                min-width: 180px;
                flex: 1;
            }
            .filter-label {
                color: #94a3b8;
                font-size: 0.85rem;
                font-weight: 500;
            }
            .filter-actions {
                margin-top: 16px;
                display: flex;
                align-items: center;
            }
        </style>
    """);

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

