package app.action.baseListAction;

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
public class AuditLogList extends BaseListAction<AuditLog> {

    @Inject
    private AuditLogBean auditLogBean;


    @Override
    protected List<AuditLog> fetchList(HttpServletRequest req) throws Exception {
        return auditLogBean.list();
    }
}