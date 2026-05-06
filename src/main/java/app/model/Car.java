package app.model;

import app.framework.*;

import java.io.Serializable;

@ShowroomForm(label = "Register New Vehicle", actionUrl = "./car")
@ShowroomTable(label = "Showroom Inventory", tableUrl = "./list", registerUrl = "./car")
public class Car implements Serializable {

    private Long id;

    @ShowroomFormField(label = "Car Model",name = "carModel", placeholder = "Enter car name")
    @ShowroomTableCol(label = "Model")
    private String carModel;

    @ShowroomFormField(label = "Engine Specification",name = "engineType", placeholder = "Enter engine type")
    @ShowroomTableCol(label = "Engine Type")
    private String engineType;

    @ShowroomFormField(label = "Year",name = "year", placeholder = "Enter engine type")
    @ShowroomTableCol(label = "Year")
    private Integer year;

    @ShowroomFormField(label = "Price",name = "price", placeholder = "Enter price")
    @ShowroomTableCol(label = "Price")
    private Double price;

    //ManyToOne
    @ShowroomRelationship(mappedBy = "Id")
    @ShowroomTableCol(label = "Brand")
    private Brand brand;

    @ShowroomTableCol(label = "Category")
    @ShowroomRelationship(mappedBy = "Id")
    private Category category;

    @ShowroomTableCol(label = "Showroom")
    @ShowroomRelationship(mappedBy = "Id")
    private Showroom showroom;

    //Foreign keys
    @ShowroomFormField(label = "Brand",name = "brandId",type = "select",source = Brand.class)
    private Long brandId;

    @ShowroomFormField(label = "Category",name = "categoryId",type = "select",source = Category.class)
    private Long categoryId;

    @ShowroomFormField(label = "Showroom",name = "showroomId",type = "select",source = Showroom.class)
    private Long showroomId;

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

    public Long getShowroomId() {
        return showroomId;
    }

    public void setShowroomId(Long showroomId) {
        this.showroomId = showroomId;
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