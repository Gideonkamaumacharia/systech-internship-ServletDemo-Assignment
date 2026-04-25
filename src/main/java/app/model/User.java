package app.model;

import app.framework.ShowroomForm;
import app.framework.ShowroomFormField;
import app.framework.ShowroomTable;
import app.framework.ShowroomTableCol;

import java.io.Serializable;

@ShowroomForm(label = "Register New User", actionUrl = "./users")
@ShowroomTable(label = "Showroom Users", tableUrl = "./user_list", registerUrl = "./users",listJsp = "userList.jsp")
public class User implements Serializable {

    @ShowroomFormField(label = "ID", placeholder = "Enter user ID")
    @ShowroomTableCol(label = "ID")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
