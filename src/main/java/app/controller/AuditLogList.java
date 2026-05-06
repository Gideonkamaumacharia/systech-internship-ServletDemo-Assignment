package app.controller;

import app.action.BaseActionList;
import app.model.AuditLog;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/applogs")
public class AuditLogList extends BaseActionList<AuditLog> {
    // That's it! BaseActionList will call returnData(),
    // which calls populateRelationships() to "stitch" the Users.
}