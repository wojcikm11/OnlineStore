package pl.edu.pw.onlinestore.app.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pw.onlinestore.app.api.dto.*;
import pl.edu.pw.onlinestore.app.api.service.CategoryService;

import java.util.List;

@Controller
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category-edit")
    public String editCategory(@RequestParam("name") String name, Model model) {
        CategoryInfo category = categoryService.getCategoryByName(name);
        EditCategory editCategory = new EditCategory(category.getId(), category.getTitle(), category.getDescription());
        model.addAttribute("category", editCategory);
        return "forms/edit-category";
    }

    @PostMapping("/category-edit")
    public String getCategoryEditPage(@ModelAttribute("category") EditCategory editCategory) {
        categoryService.updateCategory(editCategory);
        return "redirect:/";
    }

    @GetMapping("/category-add")
    public String getCategoryAddPage(Model model) {
        AddCategory addCategory = new AddCategory();
        model.addAttribute("category", addCategory);
        return "/forms/add-category";
    }

    @PostMapping("/category-add")
    public String submitCategory(@ModelAttribute("category") AddCategory addCategory, Model model) {
        categoryService.addCategory(addCategory);
        return "redirect:/";
    }

    @PostMapping("/category-delete")
    public String deleteCategory(@RequestParam("name") String name) {
        categoryService.deleteCategory(name);
        return "redirect:/";
    }
}
