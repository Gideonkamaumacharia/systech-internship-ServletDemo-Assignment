package app.model;

import app.framework.ShowroomForm;
import app.framework.ShowroomFormField;
import app.framework.ShowroomTable;
import app.framework.ShowroomTableCol;

import java.io.Serializable;

@ShowroomForm(label = "Add Category", actionUrl = "./register_category")
@ShowroomTable(label = "Vehicle Categories", tableUrl = "./category_lists", registerUrl = "./register_category")
public class Category implements Serializable {
    @ShowroomFormField(label = "Category Name", placeholder = "e.g. SUV")
    @ShowroomTableCol(label = "Category")
    private String name;

    @ShowroomFormField(label = "Description ", placeholder = "Description")
    @ShowroomTableCol(label = "Description")
    private String description;


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
}
