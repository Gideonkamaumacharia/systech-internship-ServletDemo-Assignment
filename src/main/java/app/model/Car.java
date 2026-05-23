package app.model;

import app.framework.*;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;

import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "cars")
@ShowroomForm(label = "Register New Vehicle")
@ShowroomTable(label = "Showroom Car", tableUrl = "./list", registerUrl = "/car/form", editUrl = "editCar",
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


    @Transient
    private Long brandId;

    @ShowroomFormField(label = "Brand", type = "select", source = Brand.class)
    @ShowroomTableCol(label = "Brand")
    @ManyToOne(fetch = FetchType.EAGER)
    @XmlTransient
    @JoinColumn(name = "brand_id")
    private Brand brand;


    @Transient
    private Long categoryId;

    @ShowroomFormField(label = "Category", type = "select", source = Category.class)
    @ShowroomTableCol(label = "Category")
    @ManyToOne(fetch = FetchType.EAGER)
    @XmlTransient
    @JoinColumn(name = "category_id")
    private Category category;


    @Transient
    private Long showroomId;

    @ShowroomFormField(label = "Showroom", type = "select", source = Showroom.class)
    @ShowroomTableCol(label = "Showroom")
    @ManyToOne(fetch = FetchType.EAGER)
    @XmlTransient
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