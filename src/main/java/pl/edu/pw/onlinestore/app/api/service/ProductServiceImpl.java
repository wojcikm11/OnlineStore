package pl.edu.pw.onlinestore.app.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.onlinestore.app.api.dto.AddProduct;
import pl.edu.pw.onlinestore.app.api.dto.EditProduct;
import pl.edu.pw.onlinestore.app.api.dto.ProductInfo;
import pl.edu.pw.onlinestore.app.domain.Category;
import pl.edu.pw.onlinestore.app.domain.Product;
import pl.edu.pw.onlinestore.app.domain.User;
import pl.edu.pw.onlinestore.app.repository.CategoryRepository;
import pl.edu.pw.onlinestore.app.repository.ProductRepository;
import pl.edu.pw.onlinestore.security.SecurityUtils;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addProduct(AddProduct addProduct) {
        User loggedUser = SecurityUtils.getLoggedUser();
        Category category = categoryRepository.findById(addProduct.getCategoryId()).orElseThrow();
        productRepository.save(map(addProduct, loggedUser, category));
    }

    @Override
    public List<ProductInfo> getProductsByCategory(String categoryName) {
        return productRepository.findAllByCategoryTitle(categoryName).stream().map(this::map).toList();
    }

    @Override
    public List<ProductInfo> getAll() {
        return productRepository.findAll().stream().map(this::map).toList();
    }

    @Override
    public ProductInfo getProductById(Long id) {
        return map(productRepository.findById(id).orElseThrow());
    }

    @Override
    public void updateProduct(EditProduct editProduct) {
        Product product = productRepository.findById(editProduct.getId()).orElseThrow();
        Category category = categoryRepository.findById(editProduct.getCategoryId()).orElseThrow();
        product.setCategory(category);
        product.setTitle(editProduct.getTitle());
        product.setPrice(editProduct.getPrice());
    }

    @Override
    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

    private Product map(AddProduct addProduct, User loggedUser, Category category) {
        return new Product(
            loggedUser,
            category,
            addProduct.getTitle(),
            addProduct.getPrice()
        );
    }

    private ProductInfo map(Product product) {
        return new ProductInfo(
                product.getId(),
                product.getCategory().getId(),
                product.getTitle(),
                product.getUser().getUsername(),
                product.getCategory().getTitle(),
                product.getPrice()
        );
    }


}
