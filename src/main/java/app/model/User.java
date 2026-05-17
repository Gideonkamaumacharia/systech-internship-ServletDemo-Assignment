package app.model;

import app.framework.ShowroomForm;
import app.framework.ShowroomFormField;
import app.framework.ShowroomTable;
import app.framework.ShowroomTableCol;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@ShowroomForm(label = "Register New User", actionUrl = "./user")
@ShowroomTable(label = "Showroom Users", tableUrl = "./user_list", registerUrl = "./user",listJsp = "userList.jsp")
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ShowroomFormField(label = "UserName", placeholder = "Enter userName")
    @ShowroomTableCol(label = "User Name")
    @Column(name = "user_name",nullable = false)
    private String username;

    @ShowroomFormField(label = "Password", placeholder = "Enter password")
    @ShowroomTableCol(label = "Password")
    @Column(nullable = false)
    private String password;

    @ShowroomFormField(label = "Role", placeholder = "Role")
    @ShowroomTableCol(label = "Role")
    @Column
    private String role;

    //many logs to one user
    @OneToMany(mappedBy = "user")
    private List<AuditLog> logs;


    //one showroom many users
    @ManyToOne
    @JoinColumn(name = "showroom_id")
    private Showroom showroom;

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

    public Showroom getShowroom() {
        return showroom;
    }

    public void setShowroom(Showroom showroom) {
        this.showroom = showroom;
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
