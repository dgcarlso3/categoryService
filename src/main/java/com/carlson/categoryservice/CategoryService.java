package com.carlson.categoryservice;

import com.carlson.categoryservice.webservices.WebServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryProductRepository categoryProductRepository;
    private WebServiceHelper webServiceHelper;

    public CategoryService() {}

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryProductRepository categoryProductRepository, WebServiceHelper webServiceHelper) {
        this.categoryRepository = categoryRepository;
        this.categoryProductRepository = categoryProductRepository;
        this.webServiceHelper = webServiceHelper;
    }

    public String addNewCategory (String name, String parentName) {
        Category parent = categoryRepository.findByName(parentName);
        Category category = new Category(name);
        category.setParent(parent);
        categoryRepository.save(category);
        return "Saved";
    }

    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            category = new Category();
            category.setCategoryProducts(Collections.emptyList());
        }
        return category;
    }

    public String addProductToCategory(Integer categoryId, Integer productId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException();
        }
        if (!webServiceHelper.getProductExists(productId)) {
            throw new ProductNotFoundException();
        }
        CategoryProduct categoryProduct = new CategoryProduct();
        categoryProduct.setCategoryId(categoryId);
        categoryProduct.setProductId(productId);
        categoryProductRepository.save(categoryProduct);
        return "Added";
    }

}
