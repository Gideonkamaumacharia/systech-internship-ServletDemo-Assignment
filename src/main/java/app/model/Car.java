package app.model;

import app.framework.*;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "cars")
@ShowroomForm(label = "Register New Vehicle", actionUrl = "./car")
@ShowroomTable(label = "Showroom Inventory", tableUrl = "./list", registerUrl = "./car",editUrl = "editCar",
        deleteUrl  = "deleteCar")
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ShowroomFormField(label = "Car Model",name = "carModel", placeholder = "Enter car name")
    @ShowroomTableCol(label = "Model")
    @Column(name = "car_model",nullable = false)
    private String carModel;

    @ShowroomFormField(label = "Engine Specification",name = "engineType", placeholder = "Enter engine type")
    @ShowroomTableCol(label = "Engine Type")
    @Column(name = "engine_type",nullable = false)
    private String engineType;

    @ShowroomFormField(label = "Year",name = "year", placeholder = "Enter engine type")
    @ShowroomTableCol(label = "Year")
    @Column
    private Integer year;

    @ShowroomFormField(label = "Price",name = "price", placeholder = "Enter price")
    @ShowroomTableCol(label = "Price")
    @Column
    private Double price;

    @ShowroomFormField(label = "Brand", type = "select", source = Brand.class)
    @Transient
    private Long brandId;

    @ShowroomTableCol(label = "Brand")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ShowroomFormField(label = "Category", type = "select", source = Category.class)
    @Transient
    private Long categoryId;

    @ShowroomTableCol(label = "Category")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @ShowroomFormField(label = "Showroom", type = "select", source = Showroom.class)
    @Transient
    private Long showroomId;

    @ShowroomTableCol(label = "Showroom")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonbTransient
    @JoinColumn(name = "showroom_id")
    private Showroom showroom;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Showroom getShowroom() {
        return showroom;
    }

    public void setShowroom(Showroom showroom) {
        this.showroom = showroom;
    }


    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getShowroomId() {
        return showroomId;
    }

    public void setShowroomId(Long showroomId) {
        this.showroomId = showroomId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getPerformanceLabel() {
        if (engineType != null && engineType.contains("V8")) {
            return "High Performance";
        }
        return "Standard";
    }
}