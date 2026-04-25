package app.model;

import app.framework.ShowroomForm;
import app.framework.ShowroomFormField;
import app.framework.ShowroomTable;
import app.framework.ShowroomTableCol;

import java.io.Serializable;
import java.util.Date;


@ShowroomForm(label = "Log", actionUrl = "./logs")
@ShowroomTable(label = "Log", tableUrl = "./app-logs", registerUrl = "./logs")
public class AuditLog implements Serializable {
    @ShowroomFormField(label = "Action", placeholder = "What action")
    @ShowroomTableCol(label = "Action")
    private String actionPerformed;

    @ShowroomFormField(label = "User", placeholder = "Enter your user name")
    @ShowroomTableCol(label = "User")
    private String username;

    @ShowroomFormField(label = "Time", placeholder = "Time")
    @ShowroomTableCol(label = "Time")
    private String timeStamp;

    @ShowroomFormField(label = "Details", placeholder = "Enter the details")
    @ShowroomTableCol(label = "Details")
    private String details;


    public String getActionPerformed() {
        return actionPerformed;
    }

    public void setActionPerformed(String actionPerformed) {
        this.actionPerformed = actionPerformed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
