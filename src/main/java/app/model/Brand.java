package app.model;

import app.framework.ShowroomForm;
import app.framework.ShowroomFormField;
import app.framework.ShowroomTable;
import app.framework.ShowroomTableCol;

import java.io.Serializable;

@ShowroomForm(label = "Brand", actionUrl = "./car_brand")
@ShowroomTable(label = "Car Brands", tableUrl = "./brand_list", registerUrl = "./car_brand")
public class Brand implements Serializable {

    @ShowroomFormField(label = "Brand name", placeholder = "Enter car brand")
    @ShowroomTableCol(label = "Brand")
    private String name;

    @ShowroomFormField(label = "Country Of Origin", placeholder = "Enter country of origin")
    @ShowroomTableCol(label = "Country Of Origin")
    private String countryOfOrigin;

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
}
