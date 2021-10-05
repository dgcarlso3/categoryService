package com.carlson.categoryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryProductRepository categoryProductRepository;

    public CategoryService() {}

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryProductRepository categoryProductRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryProductRepository = categoryProductRepository;
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
        return categoryRepository.findByName(name);
    }

    public String addProductToCategory(Integer categoryId, Integer productId) {
        CategoryProduct categoryProduct = new CategoryProduct();
        categoryProduct.setCategoryId(categoryId);
        categoryProduct.setProductId(productId);
        categoryProductRepository.save(categoryProduct);
        return "Added";
    }

}
