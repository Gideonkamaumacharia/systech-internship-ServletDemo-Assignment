package app.model;

import app.framework.*;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.Serializable;
import java.util.Date;


@ShowroomForm(label = "Log", actionUrl = "./logs")
@ShowroomTable(label = "Log", tableUrl = "./applogs", registerUrl = "./logs",listJsp = "audit_logs.jsp")
@ApplicationScoped
public class AuditLog implements Serializable {

    private Long id;

    @ShowroomFormField(label = "Action", placeholder = "What action")
    @ShowroomTableCol(label = "Action")
    private String actionPerformed;

    @ShowroomFormField(label = "Time", placeholder = "Time")
    @ShowroomTableCol(label = "Time")
    private Date timeStamp;

    @ShowroomFormField(label = "Details", placeholder = "Enter the details")
    @ShowroomTableCol(label = "Details")
    private String details;


    //ManyToOne -> one user performs many actions that are logged
    // The Object for the JSP (e.g., ${log.user.username})
    @ShowroomRelationship(mappedBy = "id")
    private User user;

    //Foreign key
    private Long userId;

    public AuditLog(){}

    public AuditLog(String actionPerformed,Date timeStamp,String details){
        this.actionPerformed = actionPerformed;
        this.timeStamp = timeStamp;
        this.details = details;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActionPerformed() {
        return actionPerformed;
    }

    public void setActionPerformed(String actionPerformed) {
        this.actionPerformed = actionPerformed;
    }


    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
