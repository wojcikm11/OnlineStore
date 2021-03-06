package pl.edu.pw.onlinestore.app.api.service;

import pl.edu.pw.onlinestore.app.api.dto.AddProduct;
import pl.edu.pw.onlinestore.app.api.dto.EditProduct;
import pl.edu.pw.onlinestore.app.api.dto.ProductInfo;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    void addProduct(AddProduct addProduct) throws IOException;
    List<ProductInfo> getProductsByCategory(String categoryName);
    List<ProductInfo> getAll();
    ProductInfo getProductById(Long id);
    void updateProduct(EditProduct editProduct);
    void removeProduct(Long id);
    void addToWishList(Long productId);
    void removeFromWishList(Long productId);
    List<ProductInfo> getAllFromWishList(String username);
}
