package pl.edu.pw.onlinestore.app.api.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
import pl.edu.pw.onlinestore.app.repository.UserRepository;
import pl.edu.pw.onlinestore.security.SecurityUtils;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(UserRepository userRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addProduct(AddProduct addProduct) throws IOException {
        User loggedUser = SecurityUtils.getLoggedUser();
        Category category = categoryRepository.findById(addProduct.getCategoryId()).orElseThrow();
        productRepository.save(map(addProduct, loggedUser, category));
    }

    @Override
    public List<ProductInfo> getProductsByCategory(String categoryName) {
        return productRepository.findAllByCategoryTitle(categoryName).stream().
                map(product -> map(product, isInWishList(product.getId()))).toList();
    }

    @Override
    public List<ProductInfo> getAll() {
        return productRepository.findAll().stream().
                map(product -> map(product, isInWishList(product.getId()))).toList();
    }

    @Override
    public ProductInfo getProductById(Long id) {
        return map(productRepository.findById(id).orElseThrow(), isInWishList(id));
    }

    private boolean isInWishList(Long productId) {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            return false;
        }
        Product product = productRepository.findById(productId).orElseThrow();
        User user = userRepository.findByUsername(SecurityUtils.getLoggedUser().getUsername()).orElseThrow();
        return user.inWishList(product);
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

    @Override
    public void addToWishList(Long productId) {
        User user = userRepository.findByUsername(SecurityUtils.getLoggedUser().getUsername()).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        user.addToWishList(product);
    }

    @Override
    public void removeFromWishList(Long productId) {
        User user = userRepository.findByUsername(SecurityUtils.getLoggedUser().getUsername()).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();
        user.removeFromWishList(product);
    }

    @Override
    public List<ProductInfo> getAllFromWishList(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getWishList().stream().map(product -> map(product, isInWishList(product.getId()))).toList();
    }

    private Product map(AddProduct addProduct, User loggedUser, Category category) throws IOException {
        byte[] photo = addProduct.getFile() != null ? addProduct.getFile().getBytes() : null;
        return new Product(
            loggedUser,
            category,
            addProduct.getTitle(),
            addProduct.getPrice(),
            photo
        );
    }

    private ProductInfo map(Product product, boolean inWishList) {
        Long categoryId = (long) -1;
        String categoryTitle = "Brak";
        String photoBase64 = "";

        if (product.getCategory() != null) {
            categoryId = product.getCategory().getId();
            categoryTitle = product.getCategory().getTitle();
        }
        if (product.getPhoto() != null) {
            photoBase64 = Base64.getEncoder().encodeToString(product.getPhoto());
        }
        return new ProductInfo(
                product.getId(),
                photoBase64,
                categoryId,
                product.getTitle(),
                product.getUser().getUsername(),
                categoryTitle,
                product.getPrice(),
                inWishList
        );
    }


}
