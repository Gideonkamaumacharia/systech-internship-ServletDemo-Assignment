package app.action;

import app.bean.AuditLogBean;
import app.framework.*;
import app.model.AuditLog;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@ApplicationScoped
@ActionController
public class AuditLogAction {

    @Inject
    private AuditLogBean auditLogBean;

    @Inject
    private ShowroomFramework showroomFramework;

    @ActionMapping(path = "/auditlog/list", method = "GET")
    public ActionResponse list(HttpServletRequest req) throws Exception {
        List<AuditLog> logs = auditLogBean.list();
        return ActionResponse.ofList(AuditLog.class, logs);
    }
}