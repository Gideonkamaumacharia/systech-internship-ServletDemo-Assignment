package app.action;

import app.model.Category;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/category_list")
public class CategoryList extends BaseActionList<Category>{
}
