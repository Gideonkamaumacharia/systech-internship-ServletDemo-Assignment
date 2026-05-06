package app.model;

import app.framework.*;

import java.io.Serializable;
import java.util.List;

@ShowroomForm(label = "Brand", actionUrl = "./brand")
@ShowroomTable(label = "Car Brands", tableUrl = "./brand_list", registerUrl = "./brand",listJsp = "brand.jsp")
public class Brand implements Serializable {

    private Long id;

    @ShowroomFormField(label = "Brand name", placeholder = "Enter car brand")
    @ShowroomTableCol(label = "Brand")
    private String name;

    @ShowroomFormField(label = "Country Of Origin", placeholder = "Enter country of origin")
    @ShowroomTableCol(label = "Country Of Origin")
    private String countryOfOrigin;

    //OneToMany

    @ShowroomRelationship(mappedBy = "brandId") // Look for cars where brandId = this.id
    private List<Car> cars;

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @Override
    public String toString() {
        return this.name;
    }
}
