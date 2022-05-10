package pl.edu.pw.onlinestore.app.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pw.onlinestore.app.api.dto.AddProduct;
import pl.edu.pw.onlinestore.app.api.dto.ProductCategory;
import pl.edu.pw.onlinestore.app.api.dto.EditProduct;
import pl.edu.pw.onlinestore.app.api.dto.ProductInfo;
import pl.edu.pw.onlinestore.app.api.service.CategoryService;
import pl.edu.pw.onlinestore.app.api.service.ProductService;

import java.util.List;

@Controller
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/sell")
    public String getAddProductPage(Model model) {
        List<ProductCategory> categories = categoryService.getCategories();
        AddProduct product = new AddProduct();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "forms/add-product";
    }

    @PostMapping("/sell")
    public String submitProduct(@ModelAttribute("product") AddProduct addProduct, Model model) {
        System.out.println(addProduct.getCategoryId());
        productService.addProduct(addProduct);
        return "redirect:/";
    }

    @GetMapping("/product-edit")
    public String getEditProductPage(@RequestParam("id") Long id, Model model) {
        ProductInfo product = productService.getProductById(id);
        EditProduct editProduct = new EditProduct(product.getId(), product.getCategoryId(), product.getTitle(), product.getPrice());
        model.addAttribute("product", editProduct);
        List<ProductCategory> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        return "forms/edit-product";
    }

    @PostMapping("/product-edit")
    public String submitEditedProduct(@ModelAttribute("product") EditProduct editProduct, Model model) {
        productService.updateProduct(editProduct);
        return "redirect:/";
    }

    @PostMapping("/product-delete")
    public String deleteProduct(@RequestParam("id") Long id) {
        productService.removeProduct(id);
        return "redirect:/";
    }
}
