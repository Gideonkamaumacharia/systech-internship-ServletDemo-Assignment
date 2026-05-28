package app.model;

import app.framework.*;
import app.model.enums.UserRole;
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
@Table(name = "categories")
@ShowroomForm(label = "Add Category")
@ShowroomTable(label = "Vehicle Categories", tableUrl = "./category_list", registerUrl = "/category/form",listJsp = "categoryList.jsp")
// Category — only ADMIN writes, everyone can read
@ShowroomSecured(
        readRoles  = {UserRole.ADMIN, UserRole.MANAGER, UserRole.SALES_REP, UserRole.VIEWER},
        writeRoles = {UserRole.ADMIN}
)
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ShowroomFormField(label = "Category Name", placeholder = "e.g. SUV")
    @ShowroomTableCol(label = "Category")
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, unique = true)
    private String name;

    @ShowroomFormField(label = "Description ", placeholder = "Description")
    @ShowroomTableCol(label = "Description")
    @Column
    private String description;

    //@JsonbTransient
    @XmlTransient
    @JsonbTransient
    @OneToMany(mappedBy = "category")
    private List<Car> cars;

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.name; // Or whatever field holds the name (e.g., brandName)
    }
}
