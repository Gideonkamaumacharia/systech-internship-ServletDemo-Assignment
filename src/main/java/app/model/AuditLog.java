package app.model;

import app.framework.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "audit_logs")
@ShowroomForm(label = "Log", actionUrl = "./logs")
@ShowroomTable(label = "Log", tableUrl = "./applogs", registerUrl = "./logs",listJsp = "audit_logs.jsp")
public class AuditLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ShowroomFormField(label = "Action", placeholder = "What action")
    @ShowroomTableCol(label = "Action")
    @Column(name = "action_performed")
    private String actionPerformed;

    @ShowroomFormField(label = "Time", placeholder = "Time")
    @ShowroomTableCol(label = "Time")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_stamp")
    private Date timeStamp;

    @ShowroomFormField(label = "Details", placeholder = "Enter the details")
    @ShowroomTableCol(label = "Details")
    @Column
    private String details;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ShowroomTableCol(label = "User")
    private User user;


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
