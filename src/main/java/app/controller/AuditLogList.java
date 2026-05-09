package app.controller;

import app.action.BaseAction;
import app.bean.AuditLogBean;
import app.framework.ShowroomTable;
import app.model.AuditLog;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/applogs")
public class AuditLogList extends BaseAction<AuditLog> {

    @Inject
    AuditLogBean auditLogBean;

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<AuditLog> dataList = auditLogBean.list();

        req.setAttribute("dataList", dataList);

        String jspName = getType().isAnnotationPresent(ShowroomTable.class) ?
                getType().getAnnotation(ShowroomTable.class).listJsp() : "list.jsp";

        req.getRequestDispatcher(jspName).forward(req, resp);
    }
}