package app.model;

import app.framework.ShowroomForm;
import app.framework.ShowroomFormField;
import app.framework.ShowroomTable;
import app.framework.ShowroomTableCol;

import java.io.Serializable;

@ShowroomForm(label = "Register New Vehicle", actionUrl = "./car")
@ShowroomTable(label = "Showroom Inventory", tableUrl = "./list", registerUrl = "./car")
public class Car implements Serializable {

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPerformanceLabel() {
        if (this.engineType.contains("V8")) {
            return "High Performance";
        }
        return "Standard";
    }
}