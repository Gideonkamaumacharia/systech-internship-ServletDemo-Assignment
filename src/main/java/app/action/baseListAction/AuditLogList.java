//package app.action.baseListAction;
//
//
//import app.bean.AuditLogBean;
//import app.model.AuditLog;
//import jakarta.inject.Inject;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import java.util.List;
//
//@WebServlet("/applogs")
//public class AuditLogList extends BaseListAction<AuditLog> {
//
//    @Inject
//    private AuditLogBean auditLogBean;
//
//
//    @Override
//    protected List<AuditLog> fetchList(HttpServletRequest req) throws Exception {
//        return auditLogBean.list();
//    }
//}