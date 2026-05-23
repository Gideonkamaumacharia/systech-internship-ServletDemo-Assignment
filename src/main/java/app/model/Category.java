package app.model;

import app.framework.*;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;

import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@ShowroomForm(label = "Add Category")
@ShowroomTable(label = "Vehicle Categories", tableUrl = "./category_list", registerUrl = "./category",listJsp = "categoryList.jsp",editUrl = "editCategory",
        deleteUrl  = "deleteCategory")
@Entity
@Table(name = "categories")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ShowroomFormField(label = "Category Name", placeholder = "e.g. SUV")
    @ShowroomTableCol(label = "Category")
    @Column
    private String name;

    @ShowroomFormField(label = "Description ", placeholder = "Description")
    @ShowroomTableCol(label = "Description")
    @Column
    private String description;

    //@JsonbTransient
    @XmlTransient
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
