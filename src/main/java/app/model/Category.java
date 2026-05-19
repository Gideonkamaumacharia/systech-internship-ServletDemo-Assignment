package app.model;

import app.framework.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@ShowroomForm(label = "Add Category", actionUrl = "./category")
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

    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER)
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
