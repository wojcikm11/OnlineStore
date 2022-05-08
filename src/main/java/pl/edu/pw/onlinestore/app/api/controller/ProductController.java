package pl.edu.pw.onlinestore.app.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/sell")
    public String getAddProductPage() {
        return "forms/add-product";
    }

    @GetMapping("/product-edit")
    public String getEditProductPage() {
        return "forms/edit-product";
    }
}
