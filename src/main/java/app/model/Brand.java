package app.model;

import app.framework.*;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;

import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "brands")
@ShowroomForm(label = "Brand")
@ShowroomTable(label = "Car Brands", tableUrl = "./brand_list", registerUrl = "/brand/form",listJsp = "brand.jsp")
public class Brand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ShowroomFormField(label = "Brand name", placeholder = "Enter car brand")
    @ShowroomTableCol(label = "Brand")
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, unique = true)
    private String name;

    @ShowroomFormField(label = "Country Of Origin", placeholder = "Enter country of origin")
    @ShowroomTableCol(label = "Country Of Origin")
    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    //@JsonbTransient
    @XmlTransient
    @OneToMany(mappedBy = "brand")
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
