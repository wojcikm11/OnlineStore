package pl.edu.pw.onlinestore.app.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.onlinestore.app.api.dto.AddCategory;
import pl.edu.pw.onlinestore.app.api.dto.CategoryInfo;
import pl.edu.pw.onlinestore.app.api.dto.EditCategory;
import pl.edu.pw.onlinestore.app.api.dto.ProductCategory;
import pl.edu.pw.onlinestore.app.domain.Category;
import pl.edu.pw.onlinestore.app.domain.Product;
import pl.edu.pw.onlinestore.app.repository.CategoryRepository;
import pl.edu.pw.onlinestore.app.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void addCategory(AddCategory addCategory) {
        categoryRepository.save(mapToCategoryInfo(addCategory));
    }

    @Override
    public List<ProductCategory> getCategories() {
        return categoryRepository.findAll().stream().map(this::mapToProductCategory).toList();
    }

    @Override
    public CategoryInfo getCategoryByName(String name) {
        Optional<Category> category = categoryRepository.findByTitle(name);
        CategoryInfo noCategoryChosen = new CategoryInfo((long) -1, Category.DEFAULT_TITLE, Category.DEFAULT_DESCRIPTION);
        return category.map(this::mapToCategoryInfo).orElse(noCategoryChosen);
    }

    @Override
    public void updateCategory(EditCategory editCategory) {
        Category category = categoryRepository.findById(editCategory.getId()).orElseThrow();
        category.setTitle(editCategory.getTitle());
        category.setDescription(editCategory.getDescription());
    }

    @Override
    public void deleteCategory(String name) {
        List<Product> products = productRepository.findAllByCategoryTitle(name);
        for (Product product : products) {
            product.removeCategory();
        }
        categoryRepository.deleteByTitle(name);
    }

    private CategoryInfo mapToCategoryInfo(Category category) {
        return new CategoryInfo(category.getId(), category.getTitle(), category.getDescription());
    }

    private ProductCategory mapToProductCategory(Category category) {
        return new ProductCategory(category.getId(), category.getTitle());
    }

    private Category mapToCategoryInfo(AddCategory addCategory) {
        return new Category(
            addCategory.getTitle(),
            addCategory.getDescription()
        );
    }
}
