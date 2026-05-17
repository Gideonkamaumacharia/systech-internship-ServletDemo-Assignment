package app.model;

import app.framework.*;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "cars")
@ShowroomForm(label = "Register New Vehicle", actionUrl = "./car")
@ShowroomTable(label = "Showroom Inventory", tableUrl = "./list", registerUrl = "./car")
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

    @ShowroomTableCol(label = "Brand")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ShowroomTableCol(label = "Category")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ShowroomTableCol(label = "Showroom")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showroom_id")
    private Showroom showroom;

//    //Foreign keys
//    @ShowroomFormField(label = "Brand",name = "brandId",type = "select",source = Brand.class)
//    private Long brandId;
//
//    @ShowroomFormField(label = "Category",name = "categoryId",type = "select",source = Category.class)
//    private Long categoryId;
//
//    @ShowroomFormField(label = "Showroom",name = "showroomId",type = "select",source = Showroom.class)
//    private Long showroomId;

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

    public String getPerformanceLabel() {
        if (engineType != null && engineType.contains("V8")) {
            return "High Performance";
        }
        return "Standard";
    }
}