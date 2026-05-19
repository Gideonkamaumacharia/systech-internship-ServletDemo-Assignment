package app.action.baseDeleteAction;

import app.bean.AuditLogBean;
import app.model.AuditLog;
import app.model.Brand;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/deleteLog")
public class DeleteAuditLogAction extends BaseDeleteAction<AuditLog>{

    @EJB
    private AuditLogBean auditLogBean;

    @Override
    protected void removeEntity(Long id) throws Exception {
        auditLogBean.remove(id);
    }
}
