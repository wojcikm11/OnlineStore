package pl.edu.pw.onlinestore.app.api.service;

import pl.edu.pw.onlinestore.app.api.dto.AddCategory;
import pl.edu.pw.onlinestore.app.api.dto.CategoryInfo;
import pl.edu.pw.onlinestore.app.api.dto.EditCategory;
import pl.edu.pw.onlinestore.app.api.dto.ProductCategory;

import java.util.List;

public interface CategoryService {
    void addCategory(AddCategory addCategory);
    List<ProductCategory> getCategories();
    CategoryInfo getCategoryByName(String name);
    void updateCategory(EditCategory editCategory);
}
