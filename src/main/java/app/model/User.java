package app.model;

import app.framework.ShowroomForm;
import app.framework.ShowroomFormField;
import app.framework.ShowroomTable;
import app.framework.ShowroomTableCol;

import java.io.Serializable;
import java.util.List;

@ShowroomForm(label = "Register New User", actionUrl = "./user")
@ShowroomTable(label = "Showroom Users", tableUrl = "./user_list", registerUrl = "./user",listJsp = "userList.jsp")
public class User implements Serializable {

    private Long id;

    @ShowroomFormField(label = "UserName", placeholder = "Enter userName")
    @ShowroomTableCol(label = "User Name")
    private String username;

    @ShowroomFormField(label = "Password", placeholder = "Enter password")
    @ShowroomTableCol(label = "Password")
    private String password; // Hash this in real apps!

    @ShowroomFormField(label = "Role", placeholder = "Role")
    @ShowroomTableCol(label = "Role")
    private String role; // e.g., "ADMIN", "SALES"

    //many logs to one user
    private List<AuditLog> logs;

    public List<AuditLog> getLogs() {
        return logs;
    }

    public void setLogs(List<AuditLog> logs) {
        this.logs = logs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //one showroom many users
    private Showroom showroom;
    //Foreign key
    private Long showroomId;

    public Showroom getShowroom() {
        return showroom;
    }

    public void setShowroom(Showroom showroom) {
        this.showroom = showroom;
    }

    public Long getShowroomId() {
        return showroomId;
    }

    public void setShowroomId(Long showroomId) {
        this.showroomId = showroomId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
