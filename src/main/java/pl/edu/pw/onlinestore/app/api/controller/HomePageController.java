package pl.edu.pw.onlinestore.app.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pw.onlinestore.app.api.dto.CategoryInfo;
import pl.edu.pw.onlinestore.app.api.dto.ProductCategory;
import pl.edu.pw.onlinestore.app.api.dto.ProductInfo;
import pl.edu.pw.onlinestore.app.api.service.CategoryService;
import pl.edu.pw.onlinestore.app.api.service.ProductService;
import pl.edu.pw.onlinestore.app.domain.Product;

import java.util.List;
import java.util.Optional;

@Controller
public class HomePageController {

    private CategoryService categoryService;
    private ProductService productService;

    public HomePageController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/")
    public String getHomePage(Model model, @RequestParam("category") Optional<String> categoryParam) {
        String categoryName;
        List<ProductInfo> products;
        if (categoryParam.isEmpty()) {
            categoryName = "all";
            products = productService.getAll();
        } else {
            categoryName = categoryParam.get();
            products = productService.getProductsByCategory(categoryName);
        }
        model.addAttribute("products", products);
        CategoryInfo categoryInfo = categoryService.getCategoryByName(categoryName);
        model.addAttribute("categoryInfo", categoryInfo);
        List<ProductCategory> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        return "/index";
    }
}
