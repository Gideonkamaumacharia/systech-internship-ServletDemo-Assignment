package app.model;

import app.framework.*;
import app.model.enums.UserRole;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "showrooms")
@ShowroomSecured(
        readRoles  = {UserRole.ADMIN, UserRole.MANAGER},
        writeRoles = {UserRole.ADMIN}
)
@ShowroomForm(label = "Register Showroom")
@ShowroomTable(label = "Branch Locations", tableUrl = "./showroom_list", registerUrl = "/showroom/form",listJsp = "showroomList.jsp")
public class Showroom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ShowroomFormField(label = "Location", placeholder = "e.g. Nairobi CBD")
    @ShowroomTableCol(label = "Location")
    @Column(name = "location_name",nullable = false,unique = true)
    private String locationName;


    @ShowroomFormField(label = "Manager", type = "select", source = User.class, name = "managerId")
    @Transient
    private Long managerId;

    @ShowroomTableCol(label = "Manager")
    @OneToOne
    @JoinColumn(name = "manager_id",nullable = false,unique = true)
    private User manager;

    @ShowroomFormField(label = "Capacity", placeholder = "Enter Capacity")
    @ShowroomTableCol(label = "Capacity")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @Column(name = "capacity",nullable = false)
    private int capacity;

    //@JsonbTransient
    @XmlTransient
    @OneToMany(mappedBy = "showroom")
    private List<Car> cars;


    @OneToMany(mappedBy = "showroom",fetch = FetchType.EAGER)
    private List<User> users;



    public User getManager() { return manager; }
    public void setManager(User manager) { this.manager = manager; }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return this.locationName;
    }
}
