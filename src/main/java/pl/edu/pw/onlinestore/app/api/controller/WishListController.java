package pl.edu.pw.onlinestore.app.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.pw.onlinestore.app.api.service.ProductService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WishListController {

    private ProductService productService;

    public WishListController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/wishlist-add")
    public String addToFavorites(@RequestParam("id") Long productId, HttpServletRequest request) {
        productService.addToWishList(productId);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("/wishlist-remove")
    public String removeFromFavorites(@RequestParam("id") Long productId, HttpServletRequest request) {
        productService.removeFromWishList(productId);
        return "redirect:" + request.getHeader("referer");
    }
}
