package app.model;

import app.framework.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "audit_logs")
@ShowroomTable(label = "Log", tableUrl = "./applogs",listJsp = "audit_logs.jsp")
public class AuditLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ShowroomFormField(label = "Action", placeholder = "What action")
    @ShowroomTableCol(label = "Action")
    @Column(name = "action_performed", updatable = false)
    private String actionPerformed;

    @ShowroomFormField(label = "Time", placeholder = "Time")
    @ShowroomTableCol(label = "Time")
    @Column(name = "time_stamp", nullable = false, updatable = false)
    private LocalDateTime timeStamp;

    @PrePersist
    protected void onPrePersist() {
        this.timeStamp = LocalDateTime.now();  // system clock, not caller
    }

    @ShowroomFormField(label = "Details", placeholder = "Enter the details")
    @ShowroomTableCol(label = "Details")
    @Column( updatable = false)
    private String details;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @ShowroomTableCol(label = "User")
    private User user;


    public AuditLog(){}

    public AuditLog(String actionPerformed,LocalDateTime timeStamp,String details){
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


    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
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
