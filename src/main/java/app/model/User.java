package app.model;

import app.framework.ShowroomForm;
import app.framework.ShowroomFormField;
import app.framework.ShowroomTable;
import app.framework.ShowroomTableCol;
import app.model.enums.UserRole;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@ShowroomForm(label = "Register New User")
@ShowroomTable(label = "Showroom Users", tableUrl = "./user_list", registerUrl = "/user/form",listJsp = "userList.jsp")
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ShowroomFormField(label = "UserName", placeholder = "Enter userName")
    @ShowroomTableCol(label = "User Name")
    @Column(name = "user_name",nullable = false,unique = true, length = 50)
    private String username;

    @ShowroomFormField(label = "Password", placeholder = "Enter password",editIgnore=true)
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @ShowroomFormField(label = "Role", placeholder = "Role")
    @ShowroomTableCol(label = "Role")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    //many logs to one user
    @OneToMany(mappedBy = "user")
    private List<AuditLog> logs;


    //one showroom many users
    @ManyToOne
    @JoinColumn(name = "showroom_id",nullable = true)
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return username;
    }
}
