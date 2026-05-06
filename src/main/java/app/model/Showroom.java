package app.model;

import app.framework.*;

import java.io.Serializable;
import java.util.List;

@ShowroomForm(label = "Register Showroom", actionUrl = "./showroom")
@ShowroomTable(label = "Branch Locations", tableUrl = "./showroom_list", registerUrl = "./showroom",listJsp = "showroomList.jsp")
public class Showroom implements Serializable {


    private Long id;

    @ShowroomFormField(label = "Location", placeholder = "e.g. Nairobi CBD")
    @ShowroomTableCol(label = "Location")
    private String locationName;

    @ShowroomFormField(label = "Manager", type = "select",source = User.class)
    @ShowroomTableCol(label = "Manager")
    private Long managerId;

    @ShowroomFormField(label = "Capacity", placeholder = "Enter Capacity")
    @ShowroomTableCol(label = "Capacity")
    private int capacity;

    //A showroom can have many cars or users
    @ShowroomRelationship(mappedBy = "showroomId")
    private List<Car> cars;

    // For the single Manager object
    @ShowroomRelationship(mappedBy = "showroomId")
    private List<User> users;

    // For the single Manager object
    // Add this to hold the actual User data after a JOIN or secondary fetch
    @ShowroomRelationship(mappedBy = "id") // Link via managerId to User's ID
    private User manager;

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

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return this.locationName;
    }
}
