package app.model;

import app.framework.ShowroomForm;
import app.framework.ShowroomFormField;
import app.framework.ShowroomTable;
import app.framework.ShowroomTableCol;

import java.io.Serializable;

@ShowroomForm(label = "Register Showroom", actionUrl = "./register_showroom")
@ShowroomTable(label = "Branch Locations", tableUrl = "./showroom_lists", registerUrl = "./register_showroom",listJsp = "showroomList.jsp")
public class Showroom implements Serializable {
    @ShowroomFormField(label = "Location", placeholder = "e.g. Nairobi CBD")
    @ShowroomTableCol(label = "Location")
    private String locationName;

    @ShowroomFormField(label = "Manager", placeholder = "Enter manager name")
    @ShowroomTableCol(label = "Manager")
    private String managerName;

    @ShowroomFormField(label = "Capacity", placeholder = "Enter Capacity")
    @ShowroomTableCol(label = "Capacity")
    private int capacity;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
